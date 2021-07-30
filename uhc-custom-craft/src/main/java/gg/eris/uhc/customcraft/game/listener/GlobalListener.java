package gg.eris.uhc.customcraft.game.listener;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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

    event.setCancelled(true);
  }



}
