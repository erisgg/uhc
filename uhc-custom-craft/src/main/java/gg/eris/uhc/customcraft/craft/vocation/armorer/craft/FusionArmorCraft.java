package gg.eris.uhc.customcraft.craft.vocation.armorer.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;

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
    return null;
  }

  @Override
  public String getName() {
    return "Fusion Armor";
  }
}
