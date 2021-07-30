package gg.eris.uhc.customcraft.game.listener;

import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import java.util.Set;

public final class PvpListener extends MultiStateListener {

  @Override
  protected Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.PVP,
        TypeRegistry.DEATHMATCH
    );
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }
}
