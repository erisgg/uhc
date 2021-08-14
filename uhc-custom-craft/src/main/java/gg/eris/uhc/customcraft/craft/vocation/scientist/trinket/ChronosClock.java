package gg.eris.uhc.customcraft.craft.vocation.scientist.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;

public final class ChronosClock extends Trinket {

  public ChronosClock() {
    super("chronos_clock", CraftableInfo.builder()
        .material(Material.WATCH)
        .color(CC.DARK_PURPLE)
        .name("Chronos' Clock")
        .quote(".emit tuobA")
        .quoteGiver("Chronos")
        .effects("Adds 10 seconds to splash potion effect duration")
        .nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "geg",
            "pbp",
            "gGg"
        )
        .setIngredient('g', Material.GLOWSTONE_DUST)
        .setIngredient('e', Material.EMERALD)
        .setIngredient('p', Material.BLAZE_POWDER)
        .setIngredient('b', Material.GLASS_BOTTLE)
        .setIngredient('G', Material.GOLD_INGOT);
  }

  @Override
  public String getName() {
    return "Chronos's Clock";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }

  @EventHandler
  public void potionSplashEvent(PotionSplashEvent event) {
    for (LivingEntity entity : event.getAffectedEntities()) {
      if (entity instanceof Player) {
        Player handle = (Player) entity;
        CustomCraftUhcPlayer player =
            (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);
        if (player == null) {
          continue;
        }

        Bukkit.getScheduler().runTaskLater(UhcPlugin.getPlugin(), () -> {
          for (PotionEffect effect : event.getPotion().getEffects()) {
            handle.addPotionEffect(new PotionEffect(
                effect.getType(),
                effect.getDuration() + 220,
                effect.getAmplifier()
            ), true);
          }
        }, 2L);
      }
    }
  }

}
