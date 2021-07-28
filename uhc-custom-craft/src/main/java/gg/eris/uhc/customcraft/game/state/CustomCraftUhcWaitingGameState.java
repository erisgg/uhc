package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.state.AbstractWaitingGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class CustomCraftUhcWaitingGameState extends AbstractWaitingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final Identifier SCOREBOARD_IDENTIFIER = Identifier.of("scoreboard", "waiting");

  private final CommonsScoreboard waitingScoreboard;

  public CustomCraftUhcWaitingGameState(CustomCraftUhcGame game) {
    super(game);

    this.waitingScoreboard =
        game.getPlugin().getCommons().getScoreboardController().newScoreboard(SCOREBOARD_IDENTIFIER);
    this.waitingScoreboard.setTitle((player, ticks) -> CC.GOLD.bold() + "Eris " + CC.YELLOW.bold() + "UHC");
    this.waitingScoreboard.addLine("");
    this.waitingScoreboard.addLine(CC.GRAY + "Waiting for players");
    this.waitingScoreboard.addLine("");
    this.waitingScoreboard.addLine((player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlugin().getCommons().getErisPlayerManager().getPlayers().size() + "/70", 5);
    this.waitingScoreboard.addLine("");
    this.waitingScoreboard.addLine(CC.GRAY + "Border: " + CC.YELLOW + game.getSettings().getBorderSize());
    this.waitingScoreboard.addLine("");
    this.waitingScoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.waitingScoreboard.addPlayer(event.getPlayer());
  }

  @Override
  public void onTick(int tick) {

  }

  @Override
  public void onStart() {

  }

  @Override
  public void onEnd() {
    this.waitingScoreboard.removeAllPlayers();
  }

}
