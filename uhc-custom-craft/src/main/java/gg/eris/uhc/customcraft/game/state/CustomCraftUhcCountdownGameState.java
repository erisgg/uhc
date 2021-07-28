package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractCountdownGameState;
import gg.eris.uhc.core.util.LobbyUtil;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class CustomCraftUhcCountdownGameState extends
    AbstractCountdownGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  public static final int REQUIRED_PLAYERS = 10;
  private static final int COUNTDOWN_TIME = 300;

  private final ErisPlayerManager erisPlayerManager;
  private int countdown;

  public CustomCraftUhcCountdownGameState(CustomCraftUhcGame game) {
    super(game);
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();
  }

  @Override
  public void onTick(int tick) {
    if (this.ticks % 20 == 0) {
      this.countdown--;
    }

    boolean broadcast = false;

    switch (countdown) {
      case 180:
      case 120:
      case 60:
      case 30:
      case 10:
      case 5:
      case 4:
      case 3:
      case 2:
      case 1:
        broadcast = true;
        break;
      case 0:
        TextController.broadcastToServer(
            TextType.INFORMATION,
            "The game is <h>starting</h>"
        );
        this.game.setGameState(TypeRegistry.STARTING);
        break;
    }

    if (broadcast) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The game will begin in <h>{0}</h>",
          Time.toLongDisplayTime(this.countdown, TimeUnit.SECONDS)
      );
    }
  }

  @Override
  public void onStart() {
    this.countdown = COUNTDOWN_TIME;
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "The countdown has <h>started</h>. The game will begin in <h>{0}</h>.",
        Time.toLongDisplayTime(this.countdown, TimeUnit.SECONDS)
    );
  }

  @Override
  public void onEnd() {
    if (this.erisPlayerManager.getPlayers().size() < REQUIRED_PLAYERS) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The countdown has been <h>paused</h>. It will resume at <h>{0}/70</h> players.",
          REQUIRED_PLAYERS
      );
    }
  }

}
