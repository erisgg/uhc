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

public class CustomCraftUhcCountdownGameState extends
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
        TextController.broadcast(TextController.builder(
            "The game is $$starting$$!",
            TextType.INFORMATION
        ));
        this.game.setGameState(TypeRegistry.STARTING);
        break;
    }

    if (broadcast) {
      TextController.broadcast(TextController.builder(
          "The game will begin in $${0}$$",
          TextType.INFORMATION,
          Time.toDisplayTime(this.countdown, TimeUnit.SECONDS, false)
      ));
    }
  }

  @Override
  public void onStart() {
    this.countdown = COUNTDOWN_TIME;
    TextController.broadcast(TextController.builder(
        "The countdown has been $$started$$. The game will start in $${0}$$",
        TextType.INFORMATION,
        Time.toDisplayTime(this.countdown, TimeUnit.SECONDS, false)
    ));
  }

  @Override
  public void onEnd() {
    TextController.broadcast(TextController.builder(
        "The countdown has been $$paused$$. It will resume at $$" + REQUIRED_PLAYERS
            + "/70$$ players",
        TextType.INFORMATION
    ));
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.setJoinMessage(null);
    LobbyUtil.broadcastJoin(event.getPlayer(), this.erisPlayerManager.getPlayers().size());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    event.setQuitMessage(null);
    if (this.erisPlayerManager.getPlayers().size() < REQUIRED_PLAYERS) {
      this.game.setGameState(TypeRegistry.WAITING);
    }
  }

}
