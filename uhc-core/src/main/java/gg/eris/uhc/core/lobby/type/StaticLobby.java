package gg.eris.uhc.core.lobby.type;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class StaticLobby extends Lobby {

  public StaticLobby(UhcPlugin plugin) {
    super(plugin);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onBlockBreak(BlockBreakEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onBlockBreak(BlockPlaceEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
    if (event.getFoodLevel() != 20) {
      Player player = (Player) event.getEntity();
      player.setSaturation(20f);
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onHangingBreak(HangingBreakEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onEntityDamage(EntityDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onPlayerDropItem(PlayerDropItemEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onPlayerInteract(PlayerInteractEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onInventoryClick(InventoryClickEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onInventoryDrag(InventoryDragEvent event) {
    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public final void onInventoryInteract(InventoryInteractEvent event) {
    event.setCancelled(true);
  }


}
