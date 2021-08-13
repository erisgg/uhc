package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffectType;

public final class SpeedSoupCraft extends Craft {

  public SpeedSoupCraft() {
    super("speed_soup", CraftableInfo.builder()
        .material(Material.MUSHROOM_SOUP)
        .color(CC.GREEN)
        .name("Speed Soup")
        .quote("Zoom zoom zoom!")
        .quoteGiver("Sheldon Cooper as The Flash")
        .effects("Instantly heals you 3 hearts", "Gives you Speed I for 10 seconds")
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
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
    return new ShapedRecipe(getItem())
        .shape(
            "gsg",
            "cbc",
            "   "
        ).setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('s', Material.SUGAR)
        .setIngredient('c', Material.SUGAR_CANE)
        .setIngredient('b', Material.MUSHROOM_SOUP);
  }

  @Override
  public String getName() {
    return "Speed Soup";
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInteract(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (!(isItem(item))) {
      return;
    }

    Player player = event.getPlayer();
    player.getInventory().setItemInHand(null);

    player.setHealth(Math.min(player.getHealth() + 6, player.getMaxHealth()));
    player.addPotionEffect(PotionEffectType.SPEED.createEffect(20 * 10, 0));
    TextController.send(
        player,
        TextType.INFORMATION,
        "You have consumed <h>{0}</h>.",
        getName()
    );
  }

}
