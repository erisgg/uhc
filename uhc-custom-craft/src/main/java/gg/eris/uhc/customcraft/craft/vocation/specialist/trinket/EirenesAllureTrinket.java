package gg.eris.uhc.customcraft.craft.vocation.specialist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class EirenesAllureTrinket extends Trinket {

  public EirenesAllureTrinket() {
    super("eirenes_allure", CraftableInfo.builder()
        .material(Material.BARRIER)
        .name("Eirene's Allure")
        .color(CC.YELLOW)
        .quote("An eye for an eye makes the whole world blind")
        .quoteGiver("Mahatma Gandhi")
        .effects(
            "Hostile mobs won't target you in the nether"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "qiq",
            "iRi",
            "qiq")
        .setIngredient('q', Material.QUARTZ)
        .setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('i', Material.IRON_INGOT);
  }

  @Override
  public String getName() {
    return "Eirene's Allure";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }

}
