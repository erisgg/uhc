package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

public final class MonsterSpawnListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onCreatureSpawn(CreatureSpawnEvent event) {
    // Ignore non-monster spawns
    if (!(event.getEntity() instanceof Monster)) {
      if (event.getEntityType() == EntityType.CHICKEN) { // Decreasing chicken spawn rate
        if (RandomUtil.randomInt(0, 100) < 70) {
          event.setCancelled(true);
        }
      }
      return;
    }

    // Don't modify if it's a non-natural spawn
    CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
    if (reason == CreatureSpawnEvent.SpawnReason.CUSTOM
        || reason == CreatureSpawnEvent.SpawnReason.SPAWNER
        || reason == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG
        || reason == CreatureSpawnEvent.SpawnReason.SLIME_SPLIT) {
      return;
    }


    // Lower spawn rates
    if (RandomUtil.randomInt(0, 100) < 55) {
      event.setCancelled(true);
    }
  }

}
