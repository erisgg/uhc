package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

public final class SheepShearListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onSheepShear(PlayerShearEntityEvent event) {
    if (event.getEntity().getType() == EntityType.SHEEP && RandomUtil.randomBoolean()) {
      StackUtil.dropItem(event.getEntity().getLocation(), new ItemStack(Material.STRING));
    }
  }
}
