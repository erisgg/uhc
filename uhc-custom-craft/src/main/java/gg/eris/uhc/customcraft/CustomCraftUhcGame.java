package gg.eris.uhc.customcraft;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import org.bukkit.World;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {


  public CustomCraftUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder().build());
  }

  @Override
  public void onWorldSetup(World world) {

  }

}
