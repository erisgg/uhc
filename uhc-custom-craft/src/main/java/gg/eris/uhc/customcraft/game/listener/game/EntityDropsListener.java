package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@RequiredArgsConstructor
public final class EntityDropsListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(ignoreCancelled = true)
  public void onEntityDeath(EntityDeathEvent event) {
    Entity entity = event.getEntity();

    if (!event.getDrops().isEmpty() && entity instanceof Animals) {
      event.getDrops().removeIf(item -> item.getType().isEdible());
    }

    switch (entity.getType()) {
      case COW:
        event.getDrops().add(new ItemStack(Material.LEATHER, 1));
        break;
      case CREEPER:
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.SULPHUR));
        break;
      case ENDERMAN:
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.ENDER_PEARL));
        break;
      case SKELETON:
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.ARROW));
        event.getDrops().add(new ItemStack(Material.BONE));
        break;
      case CHICKEN:
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.FEATHER));
        break;
      case SHEEP:
        event.getDrops().clear();
        event.getDrops().add(new ItemStack(Material.WOOL));
        break;
    }
  }


}
