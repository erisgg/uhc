package gg.eris.uhc.customcraft.craft.vocation.miner.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.customcraft.craft.CraftHelper;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SmeltersShovel extends Craft {

  private static final Map<Material, Material> DROP_MAP = Map.of(
      Material.SAND, Material.GLASS,
      Material.GRAVEL, Material.FLINT
  );

  public SmeltersShovel() {
    super("smelters_shovel", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_SPADE).withEnchantment(Enchantment.DIG_SPEED, 1)
            .build())
        .color(CC.GRAY)
        .name("Smelter's Shovel")
        .quote("Diggy diggy dirt!")
        .quoteGiver("Dirtscast")
        .effects("Efficiency 1 Iron Shovel", "Automatically smelts sand into glass",
            "100% chance of receiving flint from gravel")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }

  @Override
  public int getCraftableAmount() {
    return 3;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 4;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getActualItem())
        .shape("ici", " s ", "   ")
        .setIngredient('i', Material.IRON_ORE)
        .setIngredient('c', Material.COAL)
        .setIngredient('s', CraftHelper.durabilityIgnored(Material.STONE_SPADE));
  }

  @Override
  public String getName() {
    return "Smelters's Shovel";
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!isItem(event.getPlayer().getItemInHand())) {
      return;
    }

    Block block = event.getBlock();
    Material type = block.getType();
    if (DROP_MAP.containsKey(type)) {
      event.setCancelled(true);
      block.setType(Material.AIR);
      ItemStack drop = new ItemStack(DROP_MAP.get(type));
      StackUtil.dropItem(block, drop);
    }
  }

}
