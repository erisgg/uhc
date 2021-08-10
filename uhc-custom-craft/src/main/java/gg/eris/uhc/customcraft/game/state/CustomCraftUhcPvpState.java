package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractPvpGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.WorldBorder;

public final class CustomCraftUhcPvpState extends
    AbstractPvpGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private int pvpStateTime;
  private int deathmatchCountdown;

  private static final Identifier SCOREBOARD_IDENTIFIER
      = CustomCraftUhcIdentifiers.SCOREBOARD_ID.id("pvp");

  private boolean borderFinished;
  private final CommonsScoreboard scoreboard;


  public CustomCraftUhcPvpState(CustomCraftUhcGame game) {
    super(game);
    this.borderFinished = false;
    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.GOLD.bold() + "Eris " + CC.YELLOW.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "Deathmatch in:");
    this.scoreboard.addLine((player, ticks) -> {
      int time;
      if (this.deathmatchCountdown == -1) {
        time = (this.game.getSettings().getPvpPeriodDuration() - this.pvpStateTime) + this.game
            .getSettings().getPreDeathmatchCountdownDuration();
      } else {
        time = this.deathmatchCountdown;
      }
      return CC.YELLOW + Time.toShortDisplayTime(time, TimeUnit.SECONDS);
    }, 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlayers().size() + "", 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Kills: " + CC.YELLOW + ((CustomCraftUhcPlayer) player)
            .getGameKills(), 1);
    this.scoreboard.addLine("");
    this.scoreboard
        .addLine((player, tick) -> CC.GRAY + "Border: " + CC.YELLOW + Math
            .round(game.getWorld().getWorldBorder().getSize() / 2), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onStart() {
    this.pvpStateTime = 0;
    this.deathmatchCountdown = -1;
    this.scoreboard.addAllPlayers();
  }

  @Override
  public void onEnd() {
    this.scoreboard.removeAllPlayers();
    this.game.getPlugin().getCommons().getScoreboardController().removeScoreboard(this.scoreboard);
  }

  @Override
  public void onTick(int tick) {
    if (tick == 1 && this.game.getSettings().getBorderShrinkDelay() == 0) {
      startBorderShrink();
    }

    int secondsTick = tick % 20;
    if (secondsTick % 20 != 0) {
      return;
    }

    // Check deathmatch. Variable represents whether a broadcast would be a repeat
    boolean canBroadcast = checkDeathmatch();
    if (this.deathmatchCountdown != -1) { // Handling deathmatch counting down
      boolean broadcast = false;
      if (this.deathmatchCountdown > 0 && this.deathmatchCountdown % 60 == 0) {
        broadcast = true;
      } else {
        switch (this.deathmatchCountdown) {
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
                "Deathmatch is <h>starting</h>."
            );
            this.game.setGameState(TypeRegistry.DEATHMATCH);
            return;
        }
      }
      if (broadcast && canBroadcast) {
        TextController.broadcastToServer(
            TextType.INFORMATION,
            "Deathmatch is <h>starting</h> in <h>{0}</h>.",
            Time.toLongDisplayTime(this.deathmatchCountdown, TimeUnit.SECONDS)
        );
      }

      this.deathmatchCountdown--;
    }

    if (tick / 20 == this.game.getSettings().getBorderShrinkDelay()) {
      startBorderShrink();
    } else if (!this.borderFinished
        && this.game.getWorld().getWorldBorder().getSize() / 2 == this.game
        .getSettings().getBorderShrunkRadius()) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The border has <h>finished</h> shrinking."
      );
      this.borderFinished = true;
    }

    this.pvpStateTime++;
  }

  private void startBorderShrink() {
    WorldBorder border = this.game.getWorld().getWorldBorder();
    border.setCenter(0, 0);
    border.setSize(this.game.getSettings().getBorderShrunkRadius() * 2,
        this.game.getSettings().getBorderShrinkDuration());
    border = this.game.getNether().getWorldBorder();
    border.setCenter(0, 0);
    border.setSize(this.game.getSettings().getBorderShrunkRadius() * 2,
        this.game.getSettings().getBorderShrinkDuration());
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "The border has <h>begun</h> shrinking."
    );
  }

  private boolean checkDeathmatch() {
    if (this.deathmatchCountdown == -1) {
      if (this.game.getPlayers().size() <= this.game.getSettings().getDeathmatchPlayerThreshold()) {
        this.deathmatchCountdown = this.game.getSettings().getPreDeathmatchCountdownDuration();
        TextController.broadcastToServer(
            TextType.INFORMATION,
            "Deathmatch will start in <h>{0}</h> as there are only <h>{1}</h> players remaining.",
            Time.toLongDisplayTime(this.deathmatchCountdown, TimeUnit.SECONDS),
            this.game.getPlayers().size()
        );
        return false;
      } else if (this.pvpStateTime >= this.game.getSettings().getPvpPeriodDuration()) {
        this.deathmatchCountdown = this.game.getSettings().getPreDeathmatchCountdownDuration();
      }
    }

    return true;
  }
}
