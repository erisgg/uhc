package gg.eris.uhc.customcraft.game.listener;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.core.util.Pair;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

@RequiredArgsConstructor
public final class SpectatorListener extends MultiStateListener {

  @Override
  protected Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.STARTING,
        TypeRegistry.GRACE_PERIOD,
        TypeRegistry.PVP,
        TypeRegistry.DEATHMATCH
    );
  }

  private final CustomCraftUhcGame game;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerUtil.resetPlayer(player);

    ErisPlayer erisPlayer =
        this.game.getPlugin().getCommons().getErisPlayerManager().getPlayer(player);
    boolean hasPermission =
        erisPlayer.hasPermission(CustomCraftUhcIdentifiers.VIEWSPECTATORS_PERMISSION);

    for (Player other : Bukkit.getOnlinePlayers()) {
      if (!this.game.isPlayer(other)) { // If the online player is not alive
        if (!hasPermission) { // If they don't have permission to see spectators
          player.hidePlayer(other); // Hide the joining spectator
        }
      }

      // If the other player already on the server can't see spectators
      if (!this.game.getPlugin().getCommons().getErisPlayerManager().getPlayer(other)
          .hasPermission(CustomCraftUhcIdentifiers.VIEWSPECTATORS_PERMISSION)) {
        other.hidePlayer(player); // Hide the new joining player
      }
    }

    Bukkit.getScheduler().runTaskLater(this.game.getPlugin(), () -> {
      PlayerUtil.setSafeGameMode(player, GameMode.CREATIVE);
      player.setAllowFlight(true);
      player.setFlying(true);

      Bukkit.getScheduler().runTaskLater(this.game.getPlugin(), () -> {
        if (this.game.getGameState().getType() == TypeRegistry.DEATHMATCH) {
          player.teleport(new Location(this.game.getDeathmatch(), 0,
              this.game.getWorld().getHighestBlockYAt(0, 0) + 50, 0));
        } else {
          player.teleport(new Location(this.game.getWorld(), 0,
              this.game.getWorld().getHighestBlockYAt(0, 0) + 5, 0));
        }
      }, 4L);
    }, 4L);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player handle = event.getPlayer();
    if (this.game.isPlayer(handle)) {
      CustomCraftUhcPlayer player = this.game.getPlayer(event.getPlayer());
      CustomCraftUhcPlayer killer = null;
      Pair<UUID, Long> lastAttacker = player.getLastAttacker();
      if (lastAttacker != null) {
        killer = this.game.getPlayer(lastAttacker.getKey());
      }
      this.game.killPlayer(player, killer, null);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    if (!this.game.isPlayer(event.getPlayer())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onEntityTarget(EntityTargetLivingEntityEvent event) {
    if (event.getTarget() != null && !this.game.isPlayer(event.getTarget().getUniqueId())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (!this.game.isPlayer(event.getPlayer())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onShearSheep(PlayerShearEntityEvent event) {
    if (!this.game.isPlayer(event.getPlayer())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() == EntityType.PLAYER && !this.game.isPlayer(
        event.getDamager().getUniqueId())) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onBlockBreak(BlockBreakEvent event) {
    if (!(this.game.isPlayer(event.getPlayer()))) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onBlockPlace(BlockPlaceEvent event) {
    if (!(this.game.isPlayer(event.getPlayer()))) {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onBlockPlace(PlayerDropItemEvent event) {
    if (!(this.game.isPlayer(event.getPlayer()))) {
      event.setCancelled(true);
    }
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }
}
