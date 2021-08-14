package gg.eris.uhc.customcraft.craft.vocation.scientist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

public final class AbsorberCraft extends Craft {

  public AbsorberCraft() {
    super("absorber", CraftableInfo.builder()
        .material(Material.SLIME_BALL)
        .color(CC.GREEN)
        .name("Absorber")
        .quote("AAAAAAAA.")
        .quoteGiver("Kirby")
        .effects("Gives absorption 2 for 1:30")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }

  @Override
  public int getCraftableAmount() {
    return 1;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 2;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getActualItem())
        .shape(
            " R ",
            "gbg",
            " L "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('b', new MaterialData(Material.POTION, (byte) 0))
        .setIngredient('L', Material.LAPIS_BLOCK);
  }

  @Override
  public String getName() {
    return "Absorber";
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (!isItem(event.getItem())) {
      return;
    }

    event.getPlayer().addPotionEffect(PotionEffectType.ABSORPTION.createEffect(90 * 20, 1), true);
    if (!StackUtil.decrement(event.getItem())) {
      event.getPlayer().setItemInHand(null);
    }
  }

}
