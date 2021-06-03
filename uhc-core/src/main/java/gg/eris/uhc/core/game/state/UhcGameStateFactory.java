package gg.eris.uhc.core.game.state;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class UhcGameStateFactory<S extends UhcPlayer, T extends UhcGame<S>> {

  protected final T game;

  public abstract AbstractCountdownGameState<S, T> newCountdownGameState();

  public abstract AbstractDeathmatchGameState<S, T> newDeathmatchGameState();

  public abstract AbstractEndedGameState<S, T> newEndedGameState();

  public abstract AbstractGracePeriodGameState<S, T> newGracePeriodGameState();

  public abstract AbstractPvpGameState<S, T> newPvpGameState();

  public abstract AbstractStartingGameState<S, T> newStartingGameState();

  public abstract AbstractWaitingGameState<S, T> newWaitingGameState();

  public abstract Supplier<AbstractGameState<S, T>> initialState();

  public abstract Supplier<AbstractGameState<S, T>> endState();
}
