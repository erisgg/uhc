package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.uhc.core.event.UhcTickEvent;
import org.bukkit.inventory.ItemStack;

@FunctionalInterface
public interface Tickable {

  void tick(UhcTickEvent event, ItemStack item, int itemSlot, ErisPlayer player);

}
