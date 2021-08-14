package gg.eris.uhc.customcraft.craft;

import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;

public interface TrinketTickable {

  void tick(UhcTickEvent event, CustomCraftUhcPlayer player);

}
