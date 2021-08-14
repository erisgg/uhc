package gg.eris.uhc.customcraft.craft.vocation.healer.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class ForbiddenFruitTrinket extends Trinket {

  public ForbiddenFruitTrinket() {
    super("forbidden_fruit", CraftableInfo.builder()
        .base(new ItemStack(Material.RAW_FISH, 1, (short) 3))
        .color(CC.GOLD)
        .name("Forbidden Fruit")
        .quote("Don't eat that!")
        .quoteGiver("Zeus")
        .effects("Gives the player two extra hearts")
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "sss",
            "sas",
            "sss"
        ).setIngredient('s', Material.SEEDS)
        .setIngredient('a', Material.GOLDEN_APPLE);

  }

  @Override
  public String getName() {
    return "Forbidden Fruit";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

  @Override
  public void onAdd(CustomCraftUhcPlayer player) {
    player.getHandle().setMaxHealth(player.getHandle().getMaxHealth() + 4);
  }

  @Override
  public void onRemove(CustomCraftUhcPlayer player) {
    player.getHandle().setMaxHealth(player.getHandle().getMaxHealth() - 4);
  }

}
