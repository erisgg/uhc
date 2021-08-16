package gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.RandomUtil;
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
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class DefendersMedallionTrinket extends Trinket {

  public DefendersMedallionTrinket() {
    super("defenders_medallion", CraftableInfo.builder()
        .material(Material.YELLOW_FLOWER)
        .color(CC.GOLD)
        .name("Defender's Medallion")
        .quote("Those who are defended are stronger!")
        .quoteGiver("Anicetus")
        .effects("Gives a 33% chance to reduce damage taken by 20%")
        .nonTransformable()
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "iii",
            "iGi",
            "iii"
        ).setIngredient('i', Material.IRON_INGOT)
        .setIngredient('G', Material.GOLD_BLOCK);
  }

  @Override
  public String getName() {
    return "Defender's Medallion";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    Player handle = (Player) event.getEntity();
    CustomCraftUhcPlayer player =
        (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);

    if (player == null) {
      return;
    }

    if (player.getTrinketBagItem().hasTrinket(this)) {
      if (RandomUtil.percentChance(33)) {
        event.setDamage(event.getDamage() * 0.8);
      }
    }
  }

}
