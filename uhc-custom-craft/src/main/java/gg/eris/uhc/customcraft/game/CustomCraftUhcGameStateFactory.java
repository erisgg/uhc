package gg.eris.uhc.customcraft.game;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import gg.eris.uhc.core.game.state.AbstractCountdownGameState;
import gg.eris.uhc.core.game.state.AbstractDeathmatchGameState;
import gg.eris.uhc.core.game.state.AbstractEndedGameState;
import gg.eris.uhc.core.game.state.AbstractGameState;
import gg.eris.uhc.core.game.state.AbstractGracePeriodGameState;
import gg.eris.uhc.core.game.state.AbstractPvpGameState;
import gg.eris.uhc.core.game.state.AbstractStartingGameState;
import gg.eris.uhc.core.game.state.AbstractWaitingGameState;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import java.util.Map;
import java.util.function.Supplier;

public class CustomCraftUhcGameStateFactory extends
    UhcGameStateFactory<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private final Map<Type, Supplier<AbstractGameState<CustomCraftUhcPlayer, CustomCraftUhcGame>>> gameStateMap;

  public CustomCraftUhcGameStateFactory(CustomCraftUhcGame game) {
    super(game);
    this.gameStateMap = Maps.newHashMap(
        ImmutableMap.<Type, Supplier<AbstractGameState<CustomCraftUhcPlayer, CustomCraftUhcGame>>>builder()
            .put(TypeRegistry.WAITING, this::newWaitingGameState)
            .put(TypeRegistry.COUNTDOWN, this::newCountdownGameState)
            .put(TypeRegistry.STARTING, this::newStartingGameState)
            .put(TypeRegistry.GRACE_PERIOD, this::newGracePeriodGameState)
            .put(TypeRegistry.PVP, this::newPvpGameState)
            .put(TypeRegistry.DEATHMATCH, this::newDeathmatchGameState)
            .put(TypeRegistry.ENDED, this::newEndedGameState)
            .build());
  }

  public GameState<CustomCraftUhcPlayer, CustomCraftUhcGame> getNewState(Type type) {
    return this.gameStateMap.get(type).get();
  }

  @Override
  public AbstractCountdownGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newCountdownGameState() {
    return null;
  }

  @Override
  public AbstractDeathmatchGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newDeathmatchGameState() {
    return null;
  }

  @Override
  public AbstractEndedGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newEndedGameState() {
    return null;
  }

  @Override
  public AbstractGracePeriodGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newGracePeriodGameState() {
    return null;
  }

  @Override
  public AbstractPvpGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newPvpGameState() {
    return null;
  }

  @Override
  public AbstractStartingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newStartingGameState() {
    return null;
  }

  @Override
  public AbstractWaitingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newWaitingGameState() {
    return null;
  }

  @Override
  public Supplier<AbstractGameState<CustomCraftUhcPlayer, CustomCraftUhcGame>> initialState() {
    return this::newWaitingGameState;
  }

  @Override
  public Supplier<AbstractGameState<CustomCraftUhcPlayer, CustomCraftUhcGame>> endState() {
    return this::newEndedGameState;
  }
}
