package gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class ZeusBlessingTrinket extends Trinket {

  public ZeusBlessingTrinket() {
    super("zeus_blessing", CraftableInfo.builder()
        .material(Material.WEB)
        .color(CC.GOLD)
        .name("Zeus' Blessing")
        .quote("Fly!")
        .quoteGiver("Zeus")
        .effects("Any fall damage under 10 blocks will be negated")
        .nonTransformable()
        .build()
    );
  }


  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "flf",
            "lwl",
            "flf"
        ).setIngredient('f', Material.FEATHER)
        .setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
        .setIngredient('w', Material.WATER_BUCKET);
  }

  @Override
  public String getName() {
    return "Zeus' Blessing";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() != EntityType.PLAYER || event.getCause() != DamageCause.FALL) {
      return;
    }

    Player handle = (Player) event.getEntity();
    CustomCraftUhcPlayer player =
        (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);

    if (player.getTrinketBagItem().hasTrinket(this)) {
      if (event.getDamage() < 4.5) {
        event.setCancelled(true);
      }
    }
  }
}
