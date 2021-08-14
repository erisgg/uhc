package gg.eris.uhc.core.util;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.core.UhcPlugin;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LobbyUtil {

  public static void broadcastJoin(ErisPlayer player, int count) {
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "{0} has <h>joined</h> the game (<h>{1}/{2}</h>).",
        player.getDisplayName(),
        UhcPlugin.getPlugin().getUhc().getGame().getSettings().getMaxPlayers()
        count
    );
  }

}
