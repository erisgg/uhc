package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.uhc.core.game.state.AbstractWaitingGameState;
import gg.eris.uhc.core.util.LobbyUtil;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class CustomCraftUhcWaitingGameState extends AbstractWaitingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private final ErisPlayerManager erisPlayerManager;

  public CustomCraftUhcWaitingGameState(CustomCraftUhcGame game) {
    super(game);
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();
  }

  @Override
  public void onTick(int tick) {

  }

  @Override
  public void onStart() {

  }

  @Override
  public void onEnd() {

  }

}
