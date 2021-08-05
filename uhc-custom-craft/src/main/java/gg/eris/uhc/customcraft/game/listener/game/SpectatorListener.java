package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class SpectatorListener extends GameStateListener {

  private final CustomCraftUhcGame game;

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerUtil.resetPlayer(player);
    player.setGameMode(GameMode.CREATIVE);
    for (Player other : Bukkit.getOnlinePlayers()) {
      other.hidePlayer(player);
      player.hidePlayer(other);
    }

    Bukkit.getScheduler().runTask(this.game.getPlugin(), () -> {
      if (this.game.getGameState().getType() == TypeRegistry.DEATHMATCH) {
        player.teleport(new Location(this.game.getDeathmatch(), 0, 60, 0));
      } else {
        player.teleport(new Location(this.game.getWorld(), 0, 60, 0));
      }
    });
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player handle = event.getPlayer();
    Collection<ItemStack> items = PlayerUtil.getItems(handle);
    StackUtil.dropItems(handle.getLocation(), items, true);

    if (this.game.isPlayer(handle)) {
      CustomCraftUhcPlayer player = this.game.getPlayer(event.getPlayer());
      this.game.killPlayer(player, null);
    }
  }

  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    if (!this.game.isPlayer(event.getPlayer())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() == EntityType.PLAYER && !this.game.isPlayer(event.getDamager().getUniqueId())) {
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
