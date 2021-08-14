package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public final class BlockBreakListener extends GameStateListener {

  private static final ItemStack SEEDS = new ItemStack(Material.SEEDS);
  private static final ItemStack FLINT = new ItemStack(Material.FLINT);
  private static final ItemStack APPLE = new ItemStack(Material.APPLE);
  private static final ItemStack COBBLESTONE = new ItemStack(Material.COBBLESTONE);

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    Player player = event.getPlayer();
    Block block = event.getBlock();

    switch (event.getBlock().getType()) {
      case LONG_GRASS:
        if (RandomUtil.percentChance(15)) {
          StackUtil.dropItem(event.getBlock(), true, SEEDS);
        }
        break;
      case GRAVEL:
        if (RandomUtil.percentChance(25)) {
          event.setCancelled(true);
          event.getBlock().setType(Material.AIR);
          StackUtil.dropItem(event.getBlock(), true, FLINT);
        }
        break;
      case LEAVES:
      case LEAVES_2:
        if (player.getItemInHand().getType() == Material.SHEARS) {
          StackUtil.dropItem(event.getBlock().getLocation(), true, APPLE);
        }
        break;
      case STONE:
        if (event.getBlock().getData() != 0) {
          event.setCancelled(true);
          block.setType(Material.AIR);
          block.getLocation().getWorld().dropItem(block.getLocation(), COBBLESTONE);
        }
        break;
    }
  }

}
