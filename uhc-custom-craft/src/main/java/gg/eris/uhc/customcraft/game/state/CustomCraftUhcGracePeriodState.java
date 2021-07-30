package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractGracePeriodGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public final class CustomCraftUhcGracePeriodState extends
    AbstractGracePeriodGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final Identifier SCOREBOARD_IDENTIFIER
      = Identifier.of("scoreboard", "grace_period");

  private int countdown;
  private final CommonsScoreboard scoreboard;

  public CustomCraftUhcGracePeriodState(CustomCraftUhcGame game) {
    super(game);

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.GOLD.bold() + "Eris " + CC.YELLOW.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "PvP in:");
    this.scoreboard.addLine((player, ticks) -> CC.YELLOW + Time.toShortDisplayTime(this.countdown, TimeUnit.SECONDS), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlayers().size(), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "Border: " + CC.YELLOW + game.getSettings().getBorderRadius());
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onStart() {
    this.countdown = this.game.getSettings().getGracePeriodDuration();
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "The game has <h>started</h>!"
    );
    this.scoreboard.addAllPlayers();
  }

  @Override
  public void onEnd() {
    this.scoreboard.removeAllPlayers();
    this.game.getPlugin().getCommons().getScoreboardController().removeScoreboard(this.scoreboard);
  }

  @Override
  public void onTick(int tick) {
    if (tick % 20 != 0) {
      return;
    }

    boolean broadcast = false;
    switch (--this.countdown) {
      case 300:
      case 180:
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
            "The grace period has <h>ended</h>! PvP is now <h>enabled</h>."
        );
        this.game.setGameState(TypeRegistry.PVP);
        return;
    }

    if (broadcast) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The grace period will <h>end</h> in <h>{0}</h>.",
          Time.toLongDisplayTime(this.countdown, TimeUnit.SECONDS)
      );
    }
  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() == EntityType.PLAYER
        && event.getEntityType() == EntityType.PLAYER) {
      event.setCancelled(true);
      TextController.send(
          (Player) event.getDamager(),
          TextType.ERROR,
          "You cannot attack players until the grace period is <h>over</h> (<h>{0}</h>).",
          Time.toShortDisplayTime(this.countdown, TimeUnit.SECONDS)
      );
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboard.addPlayer(event.getPlayer());
  }
}
