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
    onStart();
  }

  @Override
  public final void tick() {
    onTick(++this.ticks); // start from tick 1, so second checks can just use %20
    // means that the first second is always 19/20ths of a second, but cope and seethe.
    // i actually am seething this really bugs me but it's 3am 31/7/21 and i cba.
  }

  public final void end() {
    HandlerList.unregisterAll(this);
    onEnd();
  }

  public abstract void onStart();
  public abstract void onEnd();
  public abstract void onTick(int tick);

}
