package gg.eris.uhc.core.game.state.listener;

import com.google.common.collect.Sets;
import gg.eris.uhc.core.game.state.GameState;
import java.util.Set;

public final class MultiStateListenerManager {

  private final Set<MultiStateListener> listening;
  private final Set<MultiStateListener> listeners;

  public MultiStateListenerManager() {
    this.listening = Sets.newHashSet();
    this.listeners = Sets.newHashSet();
  }

  public void onStateStart(GameState<?, ?> state) {
    for (MultiStateListener listener : this.listeners) {
      boolean applicable = listener.isApplicable(state.getType());
      if (applicable && !this.listening.contains(listener)) {
        this.listening.add(listener);
        listener.onEnable(state);
      } else if (!applicable && this.listening.contains(listener)) {
        this.listening.remove(listener);
        listener.onDisable(state);
      }
    }
  }

  public void addListener(MultiStateListener listener) {
    this.listeners.add(listener);
  }

}
