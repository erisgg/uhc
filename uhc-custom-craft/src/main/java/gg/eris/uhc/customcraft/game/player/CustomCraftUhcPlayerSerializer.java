package gg.eris.uhc.customcraft.game.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
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
        0
    );
  }

  @Override
  public CustomCraftUhcPlayer deserializePlayer(JsonNode node) {
    DefaultData data = DefaultData.fromNode(node);

    int coins = 0;
    int kills = 0;
    int wins = 0;
    int gamesPlayed = 0;
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
      }
    }

    return new CustomCraftUhcPlayer(
        data,
        wins,
        kills,
        gamesPlayed,
        coins
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

    return node;
  }
}
