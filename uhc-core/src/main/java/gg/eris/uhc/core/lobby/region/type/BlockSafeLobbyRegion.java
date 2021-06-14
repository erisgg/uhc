package gg.eris.uhc.core.lobby.region.type;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class BlockSafeLobbyRegion extends LobbyRegion {

  public BlockSafeLobbyRegion(UhcPlugin plugin, Lobby lobby) {
    super(plugin, lobby);

    registerBlockEvent(BlockBreakEvent.class, event -> event.setCancelled(true));
    registerBlockEvent(BlockPlaceEvent.class, event -> event.setCancelled(true));
    registerHangingEvent(HangingBreakEvent.class, event -> event.setCancelled(true));
    registerPlayerEvent(PlayerInteractEvent.class, event -> event.setUseInteractedBlock(Result.DENY));
    registerPlayerEvent(PlayerInteractEntityEvent.class, event -> {
      EntityType entityType = event.getRightClicked().getType();
      switch (entityType) {
        case ARMOR_STAND:
        case BOAT:
        case DROPPED_ITEM:
        case ENDER_CRYSTAL:
        case FALLING_BLOCK:
        case ITEM_FRAME:
        case MINECART:
        case MINECART_CHEST:
        case MINECART_COMMAND:
        case MINECART_FURNACE:
        case MINECART_HOPPER:
        case MINECART_MOB_SPAWNER:
        case MINECART_TNT:
        case PAINTING:
        case PRIMED_TNT:
          event.setCancelled(true);
      }
    });
  }

}
