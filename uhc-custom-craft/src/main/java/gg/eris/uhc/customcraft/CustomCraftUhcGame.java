package gg.eris.uhc.customcraft;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {

  public CustomCraftUhcGame(UhcModule<?> module) {
    super(module, UhcGameSettings.builder().build());
  }

}
