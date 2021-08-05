package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.state.AbstractWaitingGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class CustomCraftUhcWaitingGameState extends
    AbstractWaitingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final Identifier SCOREBOARD_IDENTIFIER =
      CustomCraftUhcIdentifiers.MENU_ID.id("waiting");

  private final CommonsScoreboard scoreboard;

  public CustomCraftUhcWaitingGameState(CustomCraftUhcGame game) {
    super(game);

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.GOLD.bold() + "Eris " + CC.YELLOW.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.GRAY + "Waiting for players");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlugin().getCommons()
            .getErisPlayerManager().getPlayers().size() + "/70", 5);
    this.scoreboard.addLine("");
    this.scoreboard
        .addLine(CC.GRAY + "Border: " + CC.YELLOW + game.getSettings().getBorderRadius());
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onStart() {
    this.scoreboard.addAllPlayers();
  }

  @Override
  public void onTick(int tick) {
    if (this.game.getPlugin().getCommons().getErisPlayerManager().getPlayers().size() >= this.game
        .getSettings().getRequiredPlayers()) {
      this.game.setGameState(TypeRegistry.COUNTDOWN);
    }
  }

  @Override
  public void onEnd() {
    this.scoreboard.removeAllPlayers();
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboard.addPlayer(event.getPlayer());
  }

}
