package gg.eris.uhc.customcraft.game;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import java.util.Collection;
import java.util.List;
import org.bukkit.World;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {

  public CustomCraftUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder().build());
  }

  @Override
  public void onWorldSetup(World world) {
    // Nothing special needed here. Standard
  }

  @Override
  public UhcGameStateFactory<?, ?> newStateFactory() {
    return new CustomCraftUhcGameStateFactory(this);
  }

  @Override
  protected Collection<MultiStateListener> getMultiStateListeners() {
    return List.of();
  }
}
