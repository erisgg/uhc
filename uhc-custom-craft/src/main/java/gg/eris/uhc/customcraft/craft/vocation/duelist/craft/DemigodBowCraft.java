package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.craft.CraftTickable;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class DemigodBowCraft extends Craft implements CraftTickable {

  private final long powerTwoTime;

  public DemigodBowCraft() {
    super("demigod_bow", CraftableInfo.builder()
        .base(new ItemBuilder(Material.BOW)
            .withEnchantment(Enchantment.ARROW_DAMAGE, 1)
            .build()
        )
        .color(CC.AQUA)
        .name("Demigod Bow")
        .quote("You have my bow.")
        .quoteGiver("Legolas")
        .effects(
            "Power I Bow",
            "Power II 15 minutes after PvP",
            "Power III during deathmatch"
        )
        .nonTransformable()
        .build()
    );

    this.powerTwoTime = 20L + (15 * 20L);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
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
            "rbr",
            " r "
        ).setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('b', Material.BOW);
  }

  @Override
  public String getName() {
    return "Demigod Bow";
  }

  @Override
  public void tick(UhcTickEvent event, ItemStack item, int itemSlot, ErisPlayer player) {
    int enchantmentLevel = item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
    if (enchantmentLevel == 3) {
      return;
    }

    if (event.getTick() > this.powerTwoTime && enchantmentLevel == 1) {
      item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
      player.getHandle().getInventory().setItem(itemSlot, item);
      player.getHandle().updateInventory();
      TextController.send(
          player,
          TextType.INFORMATION,
          "Your <h>{0}</h> has been upgraded to <h>Power II</h>.",
          getName()
      );
    } else if (event.getGame().getGameState().getType() == TypeRegistry.DEATHMATCH) {
      item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
      player.getHandle().getInventory().setItem(itemSlot, item);
      player.getHandle().updateInventory();
      TextController.send(
          player,
          TextType.INFORMATION,
          "Your <h>{0}</h> has been upgraded to <h>Power III</h>.",
          getName()
      );
    }
  }

}
