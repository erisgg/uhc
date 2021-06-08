package gg.eris.uhc.core.lobby.region;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class StaticLobbyRegion extends LobbyRegion {

  public StaticLobbyRegion(Lobby lobby) {
    super(lobby);

    registerBlockEvent(BlockBreakEvent.class, event -> event.setCancelled(true));
    registerBlockEvent(BlockPlaceEvent.class, event -> event.setCancelled(true));
    registerEntityEvent(FoodLevelChangeEvent.class, event -> {
      if (event.getFoodLevel() != 20) {
        Player player = (Player) event.getEntity();
        player.setSaturation(20f);
        event.setCancelled(true);
      }
    });
    registerEntityEvent(EntityDamageEvent.class, event -> event.setCancelled(true));
    registerPlayerEvent(PlayerDropItemEvent.class, event -> event.setCancelled(true));
    registerPlayerEvent(PlayerInteractEvent.class, event -> event.setCancelled(true));
    registerPlayerEvent(PlayerInteractEntityEvent.class, event -> event.setCancelled(true));
    registerHangingEvent(HangingBreakEvent.class, event -> event.setCancelled(true));

    registerChecklessEvent(InventoryInteractEvent.class, event -> {
      if (isInRegion(event.getWhoClicked().getLocation())) {
        event.setCancelled(true);
      }
    });
  }

}
