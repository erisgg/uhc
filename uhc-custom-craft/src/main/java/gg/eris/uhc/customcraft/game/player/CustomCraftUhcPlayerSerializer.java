package gg.eris.uhc.customcraft.game.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.kit.Kit;
import gg.eris.uhc.customcraft.craft.kit.KitRegistry;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import org.bukkit.entity.Player;

public final class CustomCraftUhcPlayerSerializer extends
    ErisPlayerSerializer<CustomCraftUhcPlayer> {

  private static final int DEFAULT_COINS = 0;

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
        0,
        DEFAULT_COINS,
        Maps.newHashMap(),
        new Object2IntArrayMap<>(),
        new Object2IntArrayMap<>(),
        null
    );
  }

  @Override
  public CustomCraftUhcPlayer deserializePlayer(JsonNode node) {
    DefaultData data = DefaultData.fromNode(node);

    int coins = DEFAULT_COINS;
    int gamesPlayed = 0;
    int wins = 0;
    int kills = 0;
    int deaths = 0;
    Map<Vocation, IntSet> treeData = Maps.newHashMap();
    Object2IntMap<Vocation> prestigeData = new Object2IntArrayMap<>();
    Object2IntMap<Identifier> kits = new Object2IntArrayMap<>();
    Identifier activeKit = null;

    if (node.has("games")) {
      JsonNode games = node.get("games");
      if (games.has(CustomCraftUhcIdentifiers.JSON_KEY)) {
        JsonNode customCraft = games.get(CustomCraftUhcIdentifiers.JSON_KEY);

        if (customCraft.has("coins")) {
          coins = customCraft.get("coins").asInt();
        }

        if (customCraft.has("games_played")) {
          gamesPlayed = customCraft.get("games_played").asInt();
        }

        if (customCraft.has("wins")) {
          wins = customCraft.get("wins").asInt();
        }

        if (customCraft.has("kills")) {
          kills = customCraft.get("kills").asInt();
        }

        if (customCraft.has("deaths")) {
          deaths = customCraft.get("deaths").asInt();
        }

        if (customCraft.has("active_kit")) {
          activeKit = CustomCraftUhcIdentifiers.KIT.id(customCraft.get("active_kit").asText());
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

            if (unlocks.has("kits")) {
              ObjectNode kitsNode = (ObjectNode) unlocks.get("kits");
              for (Kit value : KitRegistry.get().values()) {
                if (kitsNode.has(value.getStorageKey())) {
                  kits.put(value.getIdentifier(), kitsNode.get(value.getStorageKey()).asInt());
                }
              }
            }
          }
        }
      }
    }

    return new CustomCraftUhcPlayer(
        data,
        gamesPlayed,
        wins,
        kills,
        deaths,
        coins,
        treeData,
        prestigeData,
        kits,
        activeKit
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

    uhc.put("games_played", player.getGamesPlayed());
    uhc.put("wins", player.getWins());
    uhc.put("kills", player.getKills());
    uhc.put("deaths", player.getDeaths());
    uhc.put("coins", player.getCoins());

    if (player.getActiveKit() != null) {
      System.out.println(player.getActiveKit());
      uhc.put("active_kit", KitRegistry.get().get(player.getActiveKit()).getStorageKey());
    }

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

    ObjectNode kits;
    if (!unlocks.has("kits")) {
      kits = unlocks.putObject("kits");
    } else {
      kits = (ObjectNode) unlocks.get("kits");
    }

    for (Object2IntMap.Entry<Identifier> entry : player.getKits().object2IntEntrySet()) {
      kits.put(KitRegistry.get().get(entry.getKey()).getStorageKey(), entry.getIntValue());
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
