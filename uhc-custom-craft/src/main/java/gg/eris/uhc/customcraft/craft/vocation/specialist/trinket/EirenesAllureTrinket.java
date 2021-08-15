package gg.eris.uhc.customcraft.craft.vocation.specialist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class EirenesAllureTrinket extends Trinket {

  public EirenesAllureTrinket() {
    super("eirenes_allure", CraftableInfo.builder()
        .material(Material.BARRIER)
        .name("Eirene's Allure")
        .color(CC.YELLOW)
        .quote("An eye for an eye makes the whole world blind")
        .quoteGiver("Mahatma Gandhi")
        .effects(
            "Hostile mobs won't target you in the nether"
        ).nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "qiq",
            "iRi",
            "qiq")
        .setIngredient('q', Material.QUARTZ)
        .setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('i', Material.IRON_INGOT);
  }

  @Override
  public String getName() {
    return "Eirene's Allure";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }

  @EventHandler
  public void onEntityTarget(EntityTargetLivingEntityEvent event) {
    if (event.getTarget() == null || event.getTarget().getWorld() == null || event.getTarget().getWorld().getEnvironment() != Environment.NETHER) {
      return;
    }


    if (event.getTarget().getType() == EntityType.PLAYER) {
      Player handle = (Player) event.getTarget();
      CustomCraftUhcPlayer player =
          (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);

      if (player == null) {
        return;
      }

      if (player.getTrinketBagItem().hasTrinket(this)) {
        event.setCancelled(true);
      }
    }
  }

}
