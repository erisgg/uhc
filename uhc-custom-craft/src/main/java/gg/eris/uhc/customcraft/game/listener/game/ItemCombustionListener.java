package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public final class ItemCombustionListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() == EntityType.DROPPED_ITEM) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onEntityCombust(EntityCombustEvent event) {
    if (event.getEntityType() == EntityType.DROPPED_ITEM) {
      event.setCancelled(true);
    }
  }

}
