package gg.eris.uhc.core.lobby.region.type;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class StaticLobbyRegion extends BlockSafeLobbyRegion {

  public StaticLobbyRegion(UhcPlugin plugin, Lobby lobby) {
    super(plugin, lobby);

    registerEntityEvent(FoodLevelChangeEvent.class, event -> {
      if (event.getFoodLevel() != 20) {
        Player player = (Player) event.getEntity();
        player.setSaturation(20f);
        event.setCancelled(true);
      }
    });
    registerEntityEvent(EntityDamageEvent.class, event -> event.setCancelled(true));
    registerEntityEvent(EntitySpawnEvent.class, event -> event.setCancelled(true));
    registerPlayerEvent(PlayerDropItemEvent.class, event -> event.setCancelled(true));
    registerChecklessEvent(InventoryInteractEvent.class, event -> {
      if (isInRegion(event.getWhoClicked().getLocation())) {
        event.setCancelled(true);
      }
    });
  }

}
