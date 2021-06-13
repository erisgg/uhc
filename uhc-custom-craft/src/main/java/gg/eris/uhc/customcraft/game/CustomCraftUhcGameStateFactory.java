package gg.eris.uhc.customcraft.game;

import gg.eris.uhc.core.game.state.AbstractCountdownGameState;
import gg.eris.uhc.core.game.state.AbstractDeathmatchGameState;
import gg.eris.uhc.core.game.state.AbstractEndedGameState;
import gg.eris.uhc.core.game.state.AbstractGameState;
import gg.eris.uhc.core.game.state.AbstractGracePeriodGameState;
import gg.eris.uhc.core.game.state.AbstractPvpGameState;
import gg.eris.uhc.core.game.state.AbstractStartingGameState;
import gg.eris.uhc.core.game.state.AbstractWaitingGameState;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcCountdownGameState;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcDeathmatchState;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcEndedState;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcGracePeriodState;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcPvpState;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcStartingState;
import gg.eris.uhc.customcraft.game.state.CustomCraftUhcWaitingGameState;
import java.util.function.Supplier;

public class CustomCraftUhcGameStateFactory extends
    UhcGameStateFactory<CustomCraftUhcPlayer, CustomCraftUhcGame> {


  public CustomCraftUhcGameStateFactory(CustomCraftUhcGame game) {
    super(game);
    registerGameState(TypeRegistry.WAITING, this::newWaitingGameState);
    registerGameState(TypeRegistry.COUNTDOWN, this::newCountdownGameState);
    registerGameState(TypeRegistry.STARTING, this::newStartingGameState);
    registerGameState(TypeRegistry.GRACE_PERIOD, this::newGracePeriodGameState);
    registerGameState(TypeRegistry.PVP, this::newPvpGameState);
    registerGameState(TypeRegistry.DEATHMATCH, this::newDeathmatchGameState);
    registerGameState(TypeRegistry.ENDED, this::newEndedGameState);
  }


  @Override
  public AbstractCountdownGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newCountdownGameState() {
    return new CustomCraftUhcCountdownGameState(this.game);
  }

  @Override
  public AbstractDeathmatchGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newDeathmatchGameState() {
    return new CustomCraftUhcDeathmatchState(this.game);
  }

  @Override
  public AbstractEndedGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newEndedGameState() {
    return new CustomCraftUhcEndedState(this.game);
  }

  @Override
  public AbstractGracePeriodGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newGracePeriodGameState() {
    return new CustomCraftUhcGracePeriodState(this.game);
  }

  @Override
  public AbstractPvpGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newPvpGameState() {
    return new CustomCraftUhcPvpState(this.game);
  }

  @Override
  public AbstractStartingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newStartingGameState() {
    return new CustomCraftUhcStartingState(this.game);
  }

  @Override
  public AbstractWaitingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> newWaitingGameState() {
    return new CustomCraftUhcWaitingGameState(this.game);
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
