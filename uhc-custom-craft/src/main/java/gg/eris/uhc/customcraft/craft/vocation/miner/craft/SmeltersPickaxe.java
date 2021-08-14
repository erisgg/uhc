package gg.eris.uhc.customcraft.craft.vocation.miner.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.specialist.SpecialistVocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.specialist.perk.SpecialistPerk;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Collections;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SmeltersPickaxe extends Craft {

  private static final Map<Material, Material> DROP_MAP = Map.of(
      Material.GOLD_ORE, Material.GOLD_INGOT,
      Material.IRON_ORE, Material.IRON_INGOT
  );

  public SmeltersPickaxe() {
    super("smelters_pickaxe", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_PICKAXE).withEnchantment(Enchantment.DIG_SPEED, 1)
            .build())
        .color(CC.GRAY)
        .name("Smelter's Pickaxe")
        .quote("Diggy diggy hole!")
        .quoteGiver("Yogscast")
        .effects("Efficiency 1 Iron Pickaxe", "Automatically smelts ores into ingots")
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
        .setIngredient('s', Material.STONE_PICKAXE);
  }

  @Override
  public String getName() {
    return "Smelters's Pickaxe";
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    Player handle = event.getPlayer();
    if (!isItem(handle.getItemInHand())) {
      return;
    }

    Block block = event.getBlock();
    Material type = block.getType();
    if (DROP_MAP.containsKey(type)) {
      event.setCancelled(true);
      event.getBlock().setType(Material.AIR);

      CustomCraftUhcPlayer player =
          UhcPlugin.getPlugin().getCommons().getErisPlayerManager().getPlayer(event.getPlayer());
      int level = player.getPerkLevel(SpecialistVocationRegistry.get().getPerk());
      StackUtil.dropItems(
          block,
          SpecialistPerk.handle(player.getHandle(),
              Collections.singleton(new ItemStack(DROP_MAP.get(type))), level).getValue(),
          true
      );
      if (!StackUtil.damage(handle.getItemInHand())) {
        handle.setItemInHand(null);
      }
      ExperienceOrb orb = block.getWorld().spawn(block.getLocation(), ExperienceOrb.class);
      orb.setExperience(1);
    }
  }

}
