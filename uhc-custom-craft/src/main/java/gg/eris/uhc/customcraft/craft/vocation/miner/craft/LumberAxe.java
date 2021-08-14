package gg.eris.uhc.customcraft.craft.vocation.miner.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

public final class LumberAxe extends Craft {

  public LumberAxe() {
    super("lumber_axe", CraftableInfo.builder()
        .material(Material.IRON_AXE)
        .color(CC.DARK_BLUE)
        .name("Lumber Axe")
        .quote("What's that snapping sound?")
        .quoteGiver("(Dead) Lumberjack")
        .effects("Instantly chops down trees")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }

  @Override
  public int getCraftableAmount() {
    return 2;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 3;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getActualItem())
        .shape(
            "fff",
            "lal",
            "   "
        ).setIngredient('f', Material.FLINT)
        .setIngredient('l', Material.LOG)
        .setIngredient('a', Material.IRON_AXE);
  }

  @Override
  public String getName() {
    return "Lumber Axe";
  }

  @EventHandler(ignoreCancelled = true)
  private void handleWoodBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    Material type = block.getType();
    if (type != Material.LOG && type != Material.LOG_2) {
      return;
    } else if (!isItem(event.getPlayer().getItemInHand())) {
      return;
    }

    new BukkitRunnable() {
      private Block next = event.getBlock().getRelative(BlockFace.UP);

      @Override
      public void run() {
        this.next.breakNaturally(event.getPlayer().getItemInHand());
        this.next = this.next.getRelative(BlockFace.UP);
        if (next.getType() != Material.LOG && next.getType() != Material.LOG_2) {
          this.cancel();
        }
      }
    }.runTaskTimer(UhcPlugin.getPlugin(), 0, 2);
  }
}
