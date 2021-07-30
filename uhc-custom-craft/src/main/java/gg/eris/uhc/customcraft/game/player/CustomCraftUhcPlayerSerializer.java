package gg.eris.uhc.customcraft.game.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import org.bukkit.entity.Player;

public final class CustomCraftUhcPlayerSerializer extends
    ErisPlayerSerializer<CustomCraftUhcPlayer> {

  public CustomCraftUhcPlayerSerializer() {
    super(CustomCraftUhcPlayer.class);
  }

  @Override
  public CustomCraftUhcPlayer newPlayer(Player player) {
    return new CustomCraftUhcPlayer(DefaultData.newData(player), 0);
  }

  @Override
  public CustomCraftUhcPlayer deserializePlayer(JsonNode node) {
    DefaultData data = DefaultData.fromNode(node);

    int coins = 0;
    if (node.has("games")) {
      JsonNode games = node.get("games");
      if (games.has(CustomCraftUhcIdentifiers.JSON_KEY)) {
        coins = games.get(CustomCraftUhcIdentifiers.JSON_KEY).get("coins").asInt(0);
      }
    }

    return new CustomCraftUhcPlayer(
        data,
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

    return node;
  }
}
