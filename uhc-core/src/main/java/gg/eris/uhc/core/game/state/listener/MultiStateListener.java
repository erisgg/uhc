package gg.eris.uhc.core.game.state.listener;

import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import java.util.Set;
import org.bukkit.event.Listener;

public abstract class MultiStateListener implements Listener {

  protected abstract Set<Type> getApplicableTypes();

  protected final boolean isApplicable(Type type) {
    return getApplicableTypes().contains(type);
  }

  protected abstract void onEnable(GameState<?, ?> state);
  protected abstract void onDisable(GameState<?, ?> state);

}
