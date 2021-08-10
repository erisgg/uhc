package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gnu.trove.iterator.TPrimitiveIterator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class DemigodSwordCraft extends Craft {

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
  public void onDamage(EntityDamageByEntityEvent event){
    double damageAmount = event.getDamage();

    if(event.getEntityType() == EntityType.PLAYER){
      Player player = (Player) event.getEntity();
      ItemStack item = player.getItemInHand();

      if(this.isItem(item)){
        double damage = NBTUtil.getDoubleNbtData(item, "damage");

        item = NBTUtil.setNbtData(item, "damage", damage + damageAmount);
        player.setItemInHand(item);

        if((damage + damageAmount) >= 150){
          item.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        } else if((damage + damageAmount) >= 70){
          item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        }
      }
    }
  }
}
