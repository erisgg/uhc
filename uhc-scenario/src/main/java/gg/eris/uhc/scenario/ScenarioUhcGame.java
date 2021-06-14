package gg.eris.uhc.scenario;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import java.util.Collection;
import java.util.List;
import org.bukkit.World;

public final class ScenarioUhcGame extends UhcGame<ScenarioUhcPlayer> {

  public ScenarioUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder().build());
  }

  @Override
  public void onWorldSetup(World world) {

  }

  @Override
  public UhcGameStateFactory<?, ?> newStateFactory() {
    return null;
  }

  @Override
  protected Collection<MultiStateListener> getMultiStateListeners() {
    return List.of();
  }
}
