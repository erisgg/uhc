package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.customcraft.craft.Tickable;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class DemigodSwordCraft extends Craft implements Tickable {

  private static final String DAMAGE_KEY = "demigod_damage";

  public DemigodSwordCraft() {
    super("demigod_sword", CraftableInfo.builder()
        .base(new ItemBuilder(Material.IRON_SWORD)
            .withEnchantment(Enchantment.DAMAGE_ALL, 1)
            .build())
        .color(CC.AQUA)
        .name("Demigod Sword")
        .quote("I can kill anything with this!")
        .quoteGiver("Forgotten Adventurer")
        .effects(
            "Sharpness I Iron Sword",
            "Sharpness II after 70 damage dealt",
            "Sharpness III after 160 damage dealt"
        )
        .nonTransformable()
        .build()
    );
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
    return new ShapedRecipe(getItem())
        .shape(" R ", "rsr", " r ")
        .setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('s', Material.IRON_SWORD);
  }

  @Override
  public String getName() {
    return "Demigod Sword";
  }


  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onDamage(EntityDamageByEntityEvent event) {
    if (event.getEntityType() == EntityType.PLAYER
        && event.getDamager().getType() == EntityType.PLAYER) {
      double damageAmount = event.getFinalDamage();
      Player player = (Player) event.getDamager();
      ItemStack item = player.getItemInHand();
      if (isItem(item)) {
        double damage = NBTUtil.getDoubleNbtData(item, DAMAGE_KEY);
        player.setItemInHand(NBTUtil.setNbtData(item, DAMAGE_KEY, damage + damageAmount));
        player.updateInventory();
      }
    }
  }

  @Override
  public void tick(UhcTickEvent event, ItemStack item, int itemSlot, ErisPlayer player) {
    int enchantmentLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
    if (enchantmentLevel == 3) {
      return;
    }

    double damage = NBTUtil.getDoubleNbtData(item, DAMAGE_KEY);

    if (damage >= 160) {
      item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
      player.getHandle().updateInventory();
      TextController.send(
          player,
          TextType.INFORMATION,
          "Your <h>{0}</h> has dealt <h>160 damage</h> and has been upgraded to <h>Sharpness "
              + "III</h>.",
          getName()
      );
    } else if (damage >= 70 && enchantmentLevel == 1) {
      item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
      player.getHandle().updateInventory();
      TextController.send(
          player,
          TextType.INFORMATION,
          "Your <h>{0}</h> has dealt <h>70 damage</h> and has been upgraded to <h>Sharpness "
              + "II</h>.",
          getName()
      );
    }
  }
}
