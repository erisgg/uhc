package gg.eris.uhc.customcraft.game.listener;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

@RequiredArgsConstructor
public final class GlobalListener extends MultiStateListener {

  private final UhcGame<?> game;

  @Override
  protected Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.WAITING,
        TypeRegistry.COUNTDOWN,
        TypeRegistry.STARTING,
        TypeRegistry.GRACE_PERIOD,
        TypeRegistry.PVP,
        TypeRegistry.DEATHMATCH,
        TypeRegistry.ENDED
    );
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onWeatherChange(WeatherChangeEvent event) {
    if (event.toWeatherState()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent event) {
    if (event.getFoodLevel() != 20) {
      Player player = (Player) event.getEntity();
      player.setSaturation(20f);
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onEntitySpawn(EntitySpawnEvent event) {
    World world = event.getLocation().getWorld();
    if (world == this.game.getWorld() || world == this.game.getNether()) {
      return;
    }

    if (event.getEntity() instanceof LivingEntity) {
      event.setCancelled(true);
    }
  }

  // Message on arrow / rod
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onEntityDamage(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Projectile && event.getEntityType() == EntityType.PLAYER) {
      Projectile projectile = (Projectile) event.getDamager();
      if (projectile.getType() == EntityType.FISHING_HOOK
          || projectile.getType() == EntityType.ARROW) {
        if (projectile.getShooter() != null && projectile.getShooter() instanceof Player) {
          Player shooter = (Player) projectile.getShooter();
          TextController.send(
              shooter,
              TextType.INFORMATION,
              "<h>{0}</h> is on <h>{1}</h> HP.",
              event.getEntity().getName(),
              Text.formatDouble(((Player) event.getEntity()).getHealth())
          );
        }
      }

    }
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    event.setQuitMessage(null);
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerJoin(PlayerJoinEvent event) {
    event.setJoinMessage(null);
  }

  @EventHandler
  public void onAchievementGet(PlayerAchievementAwardedEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onPortalIgnite(EntityCreatePortalEvent event) {
    if (event.getPortalType() != PortalType.NETHER) {
      event.setCancelled(true);
    }
  }
}
