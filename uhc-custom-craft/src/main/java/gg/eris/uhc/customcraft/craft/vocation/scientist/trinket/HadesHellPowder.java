package gg.eris.uhc.customcraft.craft.vocation.scientist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.util.RandomUtil;
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
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HadesHellPowder extends Trinket {

  public HadesHellPowder() {
    super("hades_hell_powder", CraftableInfo.builder()
        .material(Material.FIREWORK_CHARGE)
        .color(CC.DARK_RED)
        .name("Hades' Hell Powder")
        .quote("I'm not giving you a quote.")
        .quoteGiver("Hades")
        .effects(
            "All nether mobs have a 10% chance to drop nether warts"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "qfq",
            "ngn",
            "qbq")
        .setIngredient('q', Material.QUARTZ)
        .setIngredient('n', Material.NETHERRACK)
        .setIngredient('g', Material.SULPHUR)
        .setIngredient('b', Material.BONE);
  }

  @Override
  public String getName() {
    return "Hades' Hell Powder";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    if (event.getEntityType() != EntityType.PLAYER
        && event.getEntity().getWorld().getEnvironment() == Environment.NETHER
        && event.getEntityType() != EntityType.SKELETON) {
      if (event.getEntity().getKiller() != null) {
        Player handle = event.getEntity().getKiller();
        CustomCraftUhcPlayer player =
            (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);
        if (player == null) {
          return;
        }

        if (player.getTrinketBagItem().hasTrinket(this) && RandomUtil.percentChance(10)) {
          StackUtil.dropItem(event.getEntity().getLocation(), true,
              new ItemStack(Material.NETHER_STALK));
        }
      }
    }

  }
}
