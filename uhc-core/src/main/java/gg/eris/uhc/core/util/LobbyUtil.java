package gg.eris.uhc.core.util;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class LobbyUtil {

  public static void broadcastJoin(Player player, int count) {
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "{0} has <h>joined</h> the game (<h>{1}/70</h).",
        count,
        player.getName()
    );
  }

}
