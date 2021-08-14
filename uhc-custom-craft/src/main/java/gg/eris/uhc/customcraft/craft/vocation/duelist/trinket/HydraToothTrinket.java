package gg.eris.uhc.customcraft.craft.vocation.duelist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagItem;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HydraToothTrinket extends Trinket {

  public HydraToothTrinket() {
    super("hydra_tooth", CraftableInfo.builder()
        .material(Material.GHAST_TEAR)
        .color(CC.DARK_PURPLE)
        .name("Hydra Tooth")
        .quote("Ouch.")
        .quoteGiver("The Hydra")
        .effects("Increases sword damage by 0.5")
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " dI",
            "fSd",
            "ff "
        ).setIngredient('d', Material.DIAMOND)
        .setIngredient('f', Material.FLINT)
        .setIngredient('S', Material.IRON_SWORD)
        .setIngredient('I', Material.IRON_BLOCK);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
  }

  @Override
  public String getName() {
    return "Hydra Tooth";
  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() != EntityType.PLAYER) {
      return;
    }

    Player handle = (Player) event.getDamager();
    CustomCraftUhcPlayer player =
        (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);

    ItemStack item = handle.getItemInHand();
    if (StackUtil.isNullOrAir(item)) {
      return;
    }

    if (item.getType() == Material.WOOD_SWORD || item.getType() == Material.STONE_SWORD
        || item.getType() == Material.IRON_SWORD || item.getType() == Material.GOLD_SWORD
        || item.getType() == Material.DIAMOND_SWORD) {
      TrinketBagItem trinketBagItem = player.getTrinketBagItem();
      if (trinketBagItem.hasTrinket(this)) {
        event.setDamage(event.getDamage() + 0.5);
      }
    }
  }

}
