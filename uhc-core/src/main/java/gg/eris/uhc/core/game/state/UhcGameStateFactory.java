package gg.eris.uhc.core.game.state;

import com.google.common.collect.Maps;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.Type;
import java.util.Map;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;


public abstract class UhcGameStateFactory<S extends UhcPlayer, T extends UhcGame<S>> {

  protected final T game;
  private final Map<Type, Supplier<GameState<S, T>>> gameStateMap;

  public UhcGameStateFactory(T game) {
    this.game = game;
    this.gameStateMap = Maps.newHashMap();
  }

  protected final void registerGameState(Type type, Supplier<GameState<S, T>> stateSupplier) {
    Validate.isTrue(!this.gameStateMap.containsKey(type));
    this.gameStateMap.put(type, stateSupplier);
  }

  public final GameState<S, T> getNewState(Type type) {
    return this.gameStateMap.get(type).get();
  }

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
