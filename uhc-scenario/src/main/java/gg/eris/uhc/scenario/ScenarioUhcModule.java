package gg.eris.uhc.scenario;

import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.player.stat.UhcPlayerStatRegistry;

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
    return new ScenarioUhcGame(this.plugin, this);
  }

  @Override
  protected UhcPlayerStatRegistry createStatRegistry() {
    return UhcPlayerStatRegistry.newRegistry(new IdentifierProvider("scenario_stat"));
  }
}
