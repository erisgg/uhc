package gg.eris.uhc.customcraft;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;

public final class CustomCraftUhcModule extends UhcModule<CustomCraftUhcGame> {

  public CustomCraftUhcModule(UhcPlugin plugin) {
    super(plugin);
  }

  @Override
  public void onEnable() {

  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected CustomCraftUhcGame createGame() {
    return new CustomCraftUhcGame(this.plugin, this);
  }

}
