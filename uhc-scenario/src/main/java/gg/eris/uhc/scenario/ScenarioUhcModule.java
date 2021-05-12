package gg.eris.uhc.scenario;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;

public class ScenarioUhcModule extends UhcModule<ScenarioUhcGame> {

  public ScenarioUhcModule(UhcPlugin plugin) {
    super(plugin);
  }

  @Override
  public void onEnable() {

  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected ScenarioUhcGame createGame() {
    return new ScenarioUhcGame(this);
  }
}
