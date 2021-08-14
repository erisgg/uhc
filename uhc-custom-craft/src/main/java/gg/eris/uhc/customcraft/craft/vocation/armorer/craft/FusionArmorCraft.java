package gg.eris.uhc.customcraft.craft.vocation.armorer.craft;

import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

public class FusionArmorCraft extends Craft {

  public FusionArmorCraft() {
    super("fusion_armor", CraftableInfo.builder()
        .material(Material.DIAMOND_HELMET)
        .color(CC.LIGHT_PURPLE)
        .name("Fusion Armor")
        .nonBrewable()
        .quote("I'll see what I can do")
        .quoteGiver("Hephaestus")
        .effects("Gives a random Protection III Unbreaking I Piece of Diamond Armor")
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
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
    return new ShapelessRecipe(new ItemBuilder(Material.DIAMOND_HELMET)
        .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
        .withEnchantment(Enchantment.DURABILITY, 1)
        .build()
    ).addIngredient(Material.DIAMOND_BOOTS)
        .addIngredient(Material.DIAMOND_BOOTS)
        .addIngredient(Material.DIAMOND_BOOTS)
        .addIngredient(Material.DIAMOND_BOOTS);
  }

  @Override
  public List<Recipe> getAlternativeRecipes() {
    Material[] materials = {
        Material.DIAMOND_BOOTS,
        Material.DIAMOND_CHESTPLATE,
        Material.DIAMOND_HELMET,
        Material.DIAMOND_LEGGINGS
    };

    List<Recipe> recipes = Lists.newArrayList();
    for (Material one : materials) {
      for (Material two : materials) {
        for (Material three : materials) {
          for (Material four : materials) {
            if (one == two && two == three && three == four && four == Material.DIAMOND_BOOTS) {
              continue;
            }

            recipes.add(new ShapelessRecipe(
                    new ItemBuilder(materials[RandomUtil.randomInt(materials.length)])
                        .withEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
                        .withEnchantment(Enchantment.DURABILITY, 1)
                        .build()
                ).addIngredient(one)
                    .addIngredient(two)
                    .addIngredient(three)
                    .addIngredient(four)
            );
          }
        }
      }
    }

    return recipes;
  }

  @Override
  public String getName() {
    return "Fusion Armor";
  }
}
