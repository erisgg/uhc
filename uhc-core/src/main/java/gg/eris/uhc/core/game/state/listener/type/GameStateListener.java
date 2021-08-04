package gg.eris.uhc.core.game.state.listener.type;

import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import java.util.Set;

public abstract class GameStateListener extends MultiStateListener {

  @Override
  protected final Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.GRACE_PERIOD,
        TypeRegistry.PVP,
        TypeRegistry.DEATHMATCH
    );
  }
}
