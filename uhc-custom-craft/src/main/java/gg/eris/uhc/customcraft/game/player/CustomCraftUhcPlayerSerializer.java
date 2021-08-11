package gg.eris.uhc.customcraft.game.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import org.bukkit.entity.Player;

public final class CustomCraftUhcPlayerSerializer extends
    ErisPlayerSerializer<CustomCraftUhcPlayer> {

  public CustomCraftUhcPlayerSerializer() {
    super(CustomCraftUhcPlayer.class);
  }

  @Override
  public CustomCraftUhcPlayer newPlayer(Player player) {
    return new CustomCraftUhcPlayer(
        DefaultData.newData(player),
        0,
        0,
        0,
        1000,
        Maps.newHashMap(),
        new Object2IntArrayMap<>()
    );
  }

  @Override
  public CustomCraftUhcPlayer deserializePlayer(JsonNode node) {
    DefaultData data = DefaultData.fromNode(node);

    int coins = 0;
    int kills = 0;
    int wins = 0;
    int gamesPlayed = 0;
    Map<Vocation, IntSet> treeData = Maps.newHashMap();
    Object2IntMap<Vocation> prestigeData = new Object2IntArrayMap<>();

    if (node.has("games")) {
      JsonNode games = node.get("games");
      if (games.has(CustomCraftUhcIdentifiers.JSON_KEY)) {
        JsonNode customCraft = games.get(CustomCraftUhcIdentifiers.JSON_KEY);

        if (customCraft.has("coins")) {
          coins = customCraft.get("coins").asInt();
        }

        if (customCraft.has("kills")) {
          kills = customCraft.get("kills").asInt();
        }

        if (customCraft.has("wins")) {
          wins = customCraft.get("wins").asInt();
        }

        if (customCraft.has("games_played")) {
          gamesPlayed = customCraft.get("games_played").asInt();
        }

        if (customCraft.has(CustomCraftUhcIdentifiers.JSON_UNLOCKS_KEY)) {
          JsonNode unlocks = customCraft.get(CustomCraftUhcIdentifiers.JSON_UNLOCKS_KEY);
          if (unlocks instanceof ObjectNode) {
            for (Vocation vocation : Vocation.values()) {
              if (unlocks.has(vocation.getStorageKey())) {
                ArrayNode vocations = (ArrayNode) unlocks.get(vocation.getStorageKey());
                IntSet set = new IntArraySet();
                for (JsonNode slot : vocations) {
                  set.add(slot.asInt());
                }
                treeData.put(vocation, set);
              }
            }

            if (unlocks.has(CustomCraftUhcIdentifiers.JSON_PRESTIGE_KEY)) {
              ObjectNode prestiges =
                  (ObjectNode) unlocks.get(CustomCraftUhcIdentifiers.JSON_PRESTIGE_KEY);
              for (Vocation vocation : Vocation.values()) {
                JsonNode prestigeNode = prestiges.get(vocation.getStorageKey());
                if (prestigeNode instanceof IntNode) {
                  prestigeData.put(vocation, prestigeNode.asInt());
                }
              }
            }
          }
        }
      }
    }

    return new CustomCraftUhcPlayer(
        data,
        wins,
        kills,
        gamesPlayed,
        coins,
        treeData,
        prestigeData
    );
  }

  @Override
  protected ObjectNode appendFields(CustomCraftUhcPlayer player, ObjectNode node) {
    ObjectNode games;
    if (!node.has("games")) {
      games = node.putObject("games");
    } else {
      games = (ObjectNode) node.get("games");
    }

    ObjectNode uhc;
    if (!games.has(CustomCraftUhcIdentifiers.JSON_KEY)) {
      uhc = games.putObject(CustomCraftUhcIdentifiers.JSON_KEY);
    } else {
      uhc = (ObjectNode) games.get(CustomCraftUhcIdentifiers.JSON_KEY);
    }

    uhc.put("coins", player.getCoins());
    uhc.put("wins", player.getWins());
    uhc.put("games_played", player.getGamesPlayed());
    uhc.put("kills", player.getKills());

    ObjectNode unlocks;
    if (!uhc.has(CustomCraftUhcIdentifiers.JSON_UNLOCKS_KEY)) {
      unlocks = uhc.putObject(CustomCraftUhcIdentifiers.JSON_UNLOCKS_KEY);
    } else {
      unlocks = (ObjectNode) games.get(CustomCraftUhcIdentifiers.JSON_UNLOCKS_KEY);
    }

    for (Map.Entry<Vocation, IntSet> entry : player.getTreeData().entrySet()) {
      if (entry.getValue() != null) {
        ArrayNode vocationNode;
        String key = entry.getKey().getStorageKey();
        if (!unlocks.has(key)) {
          vocationNode = unlocks.putArray(key);
        } else {
          vocationNode = (ArrayNode) unlocks.get(key);
        }

        for (int value : entry.getValue()) {
          vocationNode.add(value);
        }
      }
    }

    if (player.getPrestigeData().size() > 0) {
      ObjectNode prestigesNode;
      if (!unlocks.has(CustomCraftUhcIdentifiers.JSON_PRESTIGE_KEY)) {
        prestigesNode = unlocks.putObject(CustomCraftUhcIdentifiers.JSON_PRESTIGE_KEY);
      } else {
        prestigesNode = (ObjectNode) unlocks.get(CustomCraftUhcIdentifiers.JSON_PRESTIGE_KEY);
      }

      for (Object2IntMap.Entry<Vocation> entry : player.getPrestigeData().object2IntEntrySet()) {
        prestigesNode.put(entry.getKey().getStorageKey(), entry.getIntValue());
      }
    }

    return node;
  }
}
