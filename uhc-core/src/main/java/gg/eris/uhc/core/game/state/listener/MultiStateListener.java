package gg.eris.uhc.core.game.state.listener;

import com.google.common.collect.Sets;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Set;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class MultiStateListener implements Listener {

  private final Set<Supplier<Integer>> taskCreators;

  private final IntSet taskIds;

  public MultiStateListener() {
    this.taskCreators = Sets.newHashSet();
    this.taskIds = new IntArraySet();
  }

  protected abstract Set<Type> getApplicableTypes();

  protected final boolean isApplicable(Type type) {
    return getApplicableTypes().contains(type);
  }

  protected final void addTask(Supplier<Integer> supplier) {
    this.taskCreators.add(supplier);
  }

  public final void enable(GameState<?, ?> state) {
    onEnable(state);

    for (Supplier<Integer> supplier : taskCreators) {
      this.taskIds.add(supplier.get());
    }
  }

  public final void disable(GameState<?, ?> state) {
    for (int taskId : this.taskIds) {
      Bukkit.getScheduler().cancelTask(taskId);
    }
    this.taskIds.clear();

    onDisable(state);
  }

  protected abstract void onEnable(GameState<?, ?> state);
  protected abstract void onDisable(GameState<?, ?> state);

}
