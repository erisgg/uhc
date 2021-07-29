package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractCountdownGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class CustomCraftUhcCountdownGameState extends
    AbstractCountdownGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final Identifier SCOREBOARD_IDENTIFIER = Identifier.of("scoreboard", "waiting");

  //public static final int REQUIRED_PLAYERS = 10;
  //private static final int COUNTDOWN_TIME = 420;

  public static final int REQUIRED_PLAYERS = 2;
  private static final int COUNTDOWN_TIME = 30;

  private final ErisPlayerManager erisPlayerManager;
  private int countdown;

  private final CommonsScoreboard scoreboard;

  public CustomCraftUhcCountdownGameState(CustomCraftUhcGame game) {
    super(game);
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.GOLD.bold() + "Eris " + CC.YELLOW.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "Starting in:");
    this.scoreboard.addLine((player, ticks) -> CC.YELLOW + Time.toShortDisplayTime(this.countdown,
        TimeUnit.SECONDS), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlugin().getCommons()
            .getErisPlayerManager().getPlayers().size() + "/70", 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "Border: " + CC.YELLOW + game.getSettings().getBorderSize());
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onTick(int tick) {
    if (this.ticks % 20 != 0) {
      return;
    }

    this.countdown--;

    boolean broadcast = false;

    switch (this.countdown) {
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
            "The game is <h>starting</h>."
        );
        this.game.setGameState(TypeRegistry.STARTING);
        break;
    }

    if (broadcast) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The game will begin in <h>{0}</h>.",
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

    for (Player player : Bukkit.getOnlinePlayers()) {
      this.scoreboard.addPlayer(player);
    }
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

    this.scoreboard.removeAllPlayers();
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboard.addPlayer(event.getPlayer());
  }

}
