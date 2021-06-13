package gg.eris.uhc.customcraft.game.state;

import gg.eris.uhc.core.game.state.AbstractGracePeriodGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcPlayer;

public final class CustomCraftUhcGracePeriodState extends AbstractGracePeriodGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  public CustomCraftUhcGracePeriodState(CustomCraftUhcGame game) {
    super(game);
  }

  @Override
  public void onStart() {
    this.game.getModule().disableLobby();
  }

  @Override
  public void onEnd() {

  }

  @Override
  public void onTick(int tick) {

  }
}
