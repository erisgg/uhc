package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

public final class VocationStateTicker extends GameStateListener {

  @EventHandler
  public void onTick(UhcTickEvent event) {
    if (event.getTick() % 10 != 0) {
      return;
    }

    for (CustomCraftUhcPlayer player : ((CustomCraftUhcGame) event.getGame()).getPlayers()) {

      Player handle = player.getHandle();
      if (handle == null) {
        continue;
      }

      for (int i = 0; i < handle.getInventory().getContents().length; i++) {
        ItemStack item = handle.getInventory().getContents()[i];
        VocationUnlockable unlockable = Vocation.getUnlockable(item);
        if (unlockable == null) {
          continue;
        }

        if (unlockable instanceof CraftTickable) {
          ((CraftTickable) unlockable).tick(event, item, i, player);
        }
      }

      for (int i = 0; i < handle.getInventory().getArmorContents().length; i++) {
        ItemStack item = handle.getInventory().getArmorContents()[i];
        VocationUnlockable unlockable = Vocation.getUnlockable(item);
        if (unlockable == null) {
          continue;
        }

        if (unlockable instanceof CraftTickable) {
          ((CraftTickable) unlockable).tick(event, item, -1 - i, player); // armor has negative slot to indicate armor
        }
      }

      for (Trinket trinket : player.getTrinketBagItem().getContents()) {
        if (trinket instanceof TrinketTickable) {
          ((TrinketTickable) trinket).tick(event, player);
        }
      }
    }
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }
}
