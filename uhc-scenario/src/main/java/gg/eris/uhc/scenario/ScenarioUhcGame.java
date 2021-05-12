package gg.eris.uhc.scenario;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;

public final class ScenarioUhcGame extends UhcGame<ScenarioUhcPlayer> {

  public ScenarioUhcGame(UhcModule<?> module) {
    super(module, UhcGameSettings.builder().build());
  }
}
