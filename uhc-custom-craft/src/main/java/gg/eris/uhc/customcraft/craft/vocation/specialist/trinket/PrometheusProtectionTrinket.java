package gg.eris.uhc.customcraft.craft.vocation.specialist.trinket;

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

public final class PrometheusProtectionTrinket extends Trinket {

  public PrometheusProtectionTrinket() {
    super("prometheus_protection", CraftableInfo.builder()
        .material(Material.BLAZE_POWDER)
        .name("Prometheus' Protection")
        .color(CC.DARK_RED)
        .quote("That's hot!")
        .quoteGiver("Will Smith")
        .effects(
            "Take 50% less damage from fire"
        ).nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "gfg",
            "glg",
            "ggg"
        ).setIngredient('g', new MaterialData(Material.STAINED_GLASS_PANE, (byte) DataUtil.ORANGE))
        .setIngredient('f', Material.FLINT_AND_STEEL)
        .setIngredient('l', Material.LAVA_BUCKET);
  }

  @Override
  public String getName() {
    return "Prometheus' Protection";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() != EntityType.PLAYER ||
        (event.getCause() != DamageCause.FIRE && event.getCause() != DamageCause.FIRE_TICK)) {
      return;
    }

    Player handle = (Player) event.getEntity();
    CustomCraftUhcPlayer player =
        (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);

    if (player == null) {
      return;w
    }

    if (player.getTrinketBagItem().hasTrinket(this)) {
      event.setDamage(event.getDamage() / 2.0);
    }
  }

}
