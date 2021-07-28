package gg.eris.uhc.core.game.state.listener;

import com.google.common.collect.Sets;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.state.GameState;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public final class MultiStateListenerManager {

  private final UhcPlugin plugin;
  private final Set<MultiStateListener> listening;
  private final Set<MultiStateListener> listeners;

  public MultiStateListenerManager(UhcPlugin plugin) {
    this.listening = Sets.newHashSet();
    this.listeners = Sets.newHashSet();
    this.plugin = plugin;
  }

  public void onStateStart(GameState<?, ?> state) {
    for (MultiStateListener listener : this.listeners) {
      boolean applicable = listener.isApplicable(state.getType());
      if (applicable && !this.listening.contains(listener)) {
        this.listening.add(listener);
        listener.enable(state);
        Bukkit.getPluginManager().registerEvents(listener, this.plugin);
      } else if (!applicable && this.listening.contains(listener)) {
        this.listening.remove(listener);
        listener.disable(state);
        HandlerList.unregisterAll(listener);
      }
    }
  }

  public void addListener(MultiStateListener listener) {
    this.listeners.add(listener);
  }

}
