package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class SoulThirsterCraft extends Craft {

  public SoulThirsterCraft() {
    super("soul_thirster", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_SWORD)
            .withEnchantment(Enchantment.DAMAGE_ALL, 1)
            .build())
        .color(CC.DARK_RED)
        .name("Soul Thirster")
        .quote("I want to suck your blood!")
        .quoteGiver("Dracula")
        .effects(
            "Sharpness I Diamond Sword",
            "Increases by 1 sharpness level each kill, up to Sharpness IV",
            "Decays a sharpness level every 5 minutes without a kill, down to Sharpness I"
        ).nonTransformable()
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
        .shape(
            "xdx",
            "lsl",
            "xLx"
        ).setIngredient('x', Material.EXP_BOTTLE)
        .setIngredient('d', Material.DIAMOND)
        .setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
        .setIngredient('L', Material.LAPIS_BLOCK)
        .setIngredient('s', Material.DIAMOND_SWORD);
  }

  @Override
  public String getName() {
    return "Soul Thirster";
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onKill(PlayerDeathEvent event) {
    Player player = event.getEntity();
    Player killer = player.getKiller();

    ItemStack item = killer.getItemInHand();

    if (this.isItem(item)) {
      item.addEnchantment(Enchantment.DAMAGE_ALL, 4);
      item = NBTUtil.setNbtData(item, "kill_thirst", 5 * 60);
      player.setItemInHand(item);
    }
  }


}
