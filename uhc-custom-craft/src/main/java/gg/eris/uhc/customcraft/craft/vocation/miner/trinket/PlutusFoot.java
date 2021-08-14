package gg.eris.uhc.customcraft.craft.vocation.miner.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.customcraft.craft.TrinketTickable;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class PlutusFoot extends Trinket implements TrinketTickable {

  public PlutusFoot() {
    super("plutus_foot", CraftableInfo.builder()
        .material(Material.RABBIT_FOOT)
        .name("Plutus' Foot")
        .color(CC.GOLD)
        .quote("What's the price?")
        .quoteGiver("Plutus, with 2 feet")
        .effects(
            "Gives the player Haste I permanently",
            "Haste bonuses become Haste II"
        )
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            "sss",
            "fpf",
            "ccc")
        .setIngredient('s', Material.SUGAR)
        .setIngredient('c', Material.SUGAR_CANE)
        .setIngredient('f', Material.FEATHER)
        .setIngredient('p', Material.IRON_PICKAXE);
  }

  @Override
  public String getName() {
    return "Plutus' Foot";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }

  @Override
  public void tick(UhcTickEvent event, CustomCraftUhcPlayer player) {
    boolean hasHaste = false;
    for (PotionEffect effect : player.getHandle().getActivePotionEffects()) {
      if (effect.getType() == PotionEffectType.FAST_DIGGING && effect.getAmplifier() == 1) {
        hasHaste = true;
        break;
      }
    }

    if (!hasHaste) {
      player.getHandle().addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(7 * 20, 0));
    }
  }
}
