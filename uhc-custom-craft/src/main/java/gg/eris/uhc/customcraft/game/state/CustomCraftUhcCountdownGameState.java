package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Text;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractCountdownGameState;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.CustomCraftUhcModule;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class CustomCraftUhcCountdownGameState extends
    AbstractCountdownGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final Identifier SCOREBOARD_IDENTIFIER
      = CustomCraftUhcIdentifiers.SCOREBOARD_ID.id("countdown");

  private final ErisPlayerManager erisPlayerManager;
  private int countdown;
  private boolean shortened;

  private final CommonsScoreboard scoreboard;

  public CustomCraftUhcCountdownGameState(CustomCraftUhcGame game) {
    super(game);
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();
    this.countdown = 0;
    this.shortened = false;

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.YELLOW.bold() + "Eris " + CC.GOLD.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "Starting in:");
    this.scoreboard.addLine((player, ticks) -> CC.YELLOW + Time.toShortDisplayTime(this.countdown,
        TimeUnit.SECONDS), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Tier: " + CC.YELLOW + ((CustomCraftUhcPlayer) player)
            .getStar() + CustomCraftUhcIdentifiers.STAR);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Coins: " + CC.YELLOW
            + Text.formatInt(((CustomCraftUhcPlayer) player).getCoins()), 5);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlugin().getCommons()
            .getErisPlayerManager().getPlayers().size() + "/" + game.getSettings().getMaxPlayers(),
        1);
    this.scoreboard.addLine("");
    this.scoreboard
        .addLine(CC.GRAY + "Border: " + CC.YELLOW + game.getSettings().getBorderRadius());
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onTick(int tick) {
    if (this.erisPlayerManager.getPlayers().size() < this.game.getSettings().getRequiredPlayers()) {
      this.game.setGameState(TypeRegistry.WAITING);
    }

    if (this.erisPlayerManager.getPlayers().size() == this.game.getSettings().getMaxPlayers()) {
      Bukkit.getScheduler().runTaskAsynchronously(this.game.getPlugin(), () ->
          this.game.getPlugin().getCommons().getRedisWrapper().addToSet(
              CustomCraftUhcIdentifiers.LIVE_GAME_SET,
              "" + ((CustomCraftUhcModule) this.game.getModule()).getPort())
      );
    }

    if (!this.shortened && this.erisPlayerManager.getPlayers().size() >= this.game.getSettings().getShortenPlayers()) {
      this.shortened = true;
      if (this.countdown > this.game.getSettings().getShortenCountdownDuration()) {
        this.countdown = this.game.getSettings().getShortenCountdownDuration();
        TextController.broadcastToServer(
            TextType.INFORMATION,
            "The countdown has been shortened to <h>{0}</h>.",
            Time.toLongDisplayTime(this.countdown, TimeUnit.SECONDS)
        );
        return;
      }
    }

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
    this.countdown = this.game.getSettings().getPregameCountdownDuration();
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "The countdown has <h>started</h>. The game will begin in <h>{0}</h>.",
        Time.toLongDisplayTime(this.countdown, TimeUnit.SECONDS)
    );
    this.scoreboard.addAllPlayers();
  }

  @Override
  public void onEnd() {
    if (this.erisPlayerManager.getPlayers().size() < this.game.getSettings().getRequiredPlayers()) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          " The countdown has been <h>paused</h>. It will resume at <h>{0}/{1}</h> players.",
          this.game.getSettings().getRequiredPlayers(),
          this.game.getSettings().getMaxPlayers()
      );
      this.shortened = false;
    }

    this.scoreboard.removeAllPlayers();
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboard.addPlayer(event.getPlayer());
  }

}
