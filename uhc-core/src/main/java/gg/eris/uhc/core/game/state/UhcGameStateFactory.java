package gg.eris.uhc.core.game.state;

import com.google.common.collect.Maps;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import java.util.Map;
import java.util.function.Supplier;


public abstract class UhcGameStateFactory<S extends UhcPlayer, T extends UhcGame<S>> {

  protected final T game;
  private final Map<Type, Supplier<GameState<S, T>>> gameStateMap;

  public UhcGameStateFactory(T game) {
    this.game = game;
    this.gameStateMap = Maps.newIdentityHashMap();
    registerGameState(TypeRegistry.WAITING, this::newWaitingGameState);
    registerGameState(TypeRegistry.COUNTDOWN, this::newCountdownGameState);
    registerGameState(TypeRegistry.STARTING, this::newStartingGameState);
    registerGameState(TypeRegistry.GRACE_PERIOD, this::newGracePeriodGameState);
    registerGameState(TypeRegistry.PVP, this::newPvpGameState);
    registerGameState(TypeRegistry.DEATHMATCH, this::newDeathmatchGameState);
    registerGameState(TypeRegistry.ENDED, this::newEndedGameState);
  }

  protected final void registerGameState(Type type, Supplier<GameState<S, T>> stateSupplier) {
    Validate.isTrue(!this.gameStateMap.containsKey(type), "Type type=" + type
        + " already registered");
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
