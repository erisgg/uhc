package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;

public final class MobBurnListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onEntityCombust(EntityCombustEvent event) {
    if (event.getEntity().getType() == EntityType.SKELETON
        || event.getEntity().getType() == EntityType.ZOMBIE) {
      event.setCancelled(true);
    }
  }

}
