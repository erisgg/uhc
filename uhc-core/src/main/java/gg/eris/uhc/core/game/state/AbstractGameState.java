package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public abstract class AbstractGameState<S extends UhcPlayer, T extends UhcGame<S>> implements
    GameState<S, T> {

  protected final T game;
  protected int ticks;

  public AbstractGameState(T game) {
    this.game = game;
    this.ticks = 0;
  }

  public final void start() {
    Bukkit.getPluginManager().registerEvents(this, this.game.getPlugin());
    this.ticks = 0;
    this.onStart();
  }

  @Override
  public final void tick() {
    this.ticks++;
    this.onTick(this.ticks);
  }

  public final void end() {
    HandlerList.unregisterAll(this);
    this.onEnd();
  }

  public abstract void onStart();
  public abstract void onEnd();
  public abstract void onTick(int tick);

}
