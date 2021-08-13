package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

public final class VoidBagCraft extends Craft {

  public VoidBagCraft() {
    super("void_bag", CraftableInfo.builder()
        .material(Material.ENDER_CHEST)
        .color(CC.BLACK)
        .name("Void Bag")
        .quote("*static*")
        .quoteGiver("Void Astronaut")
        .effects("When opened, gives you a random item")
        .nonTransformable()
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
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
    return new ShapedRecipe(getItem())
        .shape(
            "lol",
            "oco",
            "eee"
        ).setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
        .setIngredient('o', Material.OBSIDIAN)
        .setIngredient('c', Material.CHEST)
        .setIngredient('e', Material.EXP_BOTTLE);
  }

  @Override
  public String getName() {
    return "Void Bag";
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onInteract(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (!(isItem(item))) {
      return;
    }

    Player player = event.getPlayer();
    player.getInventory().setItemInHand(null);
    player.getInventory().addItem(getReward());
    TextController.send(
        player,
        TextType.PROMPT,
        "The void has been <h>tempted</h>. An item has been <h>added</h> to your inventory."
    );
  }

  private ItemStack getReward() {
    int seed = RandomUtil.randomInt(100);
    if (seed < 10) {
      return new ItemStack(Material.DIAMOND);
    } else if (seed < 15) {
      return new ItemStack(Material.SKULL);
    } else if (seed < 20) {
      return Vocation.HEALER.getRegistry().getThirdCraft().getItem(); // Speed soup
    } else if (seed < 30) {
      return new ItemStack(Material.GOLD_INGOT, 4);
    } else if (seed < 40) {
      return new ItemStack(Material.SULPHUR, 6);
    } else if (seed < 45) {
      return new ItemStack(Material.DIAMOND, 2);
    } else if (seed < 50) {
      return new ItemStack(Material.BLAZE_ROD, 2);
    } else if (seed < 55) {
      return new ItemStack(Material.DIAMOND_BOOTS);
    } else if (seed < 65) {
      return new ItemStack(Material.IRON_BLOCK);
    } else if (seed < 75) {
      return new ItemStack(Material.EXP_BOTTLE, 16);
    } else if (seed < 85) {
      return new ItemStack(Material.LAPIS_BLOCK, 3);
    } else if (seed < 95) {
      return new ItemStack(Material.REDSTONE_BLOCK, 3);
    } else {
      return new ItemStack(Material.OBSIDIAN, 5);
    }
  }

}
