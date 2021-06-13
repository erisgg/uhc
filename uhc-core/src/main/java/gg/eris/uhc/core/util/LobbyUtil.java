package gg.eris.uhc.core.util;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class LobbyUtil {

  public static void broadcastJoin(Player player, int count) {
    TextController.broadcast(TextController.builder(
        "{0} has $$joined$$ the game $$(" + count + "/70)$$",
        TextType.INFORMATION,
        player.getName()
    ));
  }

}
