package gg.eris.uhc.customcraft.game.player;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gg.eris.commons.bukkit.player.ErisPlayer.DefaultData;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import org.bukkit.entity.Player;

public class CustomCraftUhcPlayerSerializer extends ErisPlayerSerializer<CustomCraftUhcPlayer> {

  public CustomCraftUhcPlayerSerializer() {
    super(CustomCraftUhcPlayer.class);
  }

  @Override
  public CustomCraftUhcPlayer newPlayer(Player player) {
    return new CustomCraftUhcPlayer(DefaultData.newData(player));
  }

  @Override
  public CustomCraftUhcPlayer deserializePlayer(JsonNode node) {
    DefaultData data = DefaultData.fromNode(node);
    return new CustomCraftUhcPlayer(data);
  }

  @Override
  protected ObjectNode appendFields(CustomCraftUhcPlayer player, ObjectNode node) {
    return node;
  }
}
