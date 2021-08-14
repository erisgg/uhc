package gg.eris.uhc.customcraft.craft.vocation.healer.trinket;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.healer.HealerVocationRegistry;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Handled in PvP phase starting as it is not applicable in here
 */
public class AsclepiusGraceTrinket extends Trinket {

  public AsclepiusGraceTrinket() {
    super("asclepius_grace", CraftableInfo.builder()
        .material(Material.NETHER_STAR)
        .color(CC.GOLD)
        .name("Asclepius' Grace")
        .quote("Don't eat an apple a day")
        .quoteGiver("Doctors")
        .effects("Gives a final heal at the end of the grace period")
        .build()
    );
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " i ",
            "igi",
            " i "
        ).setIngredient('i', Material.IRON_INGOT)
        .setIngredient('g', Material.GOLD_INGOT);
  }

  @Override
  public String getName() {
    return "Asclepius' Grace";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

  public static void performFinalHeal() {
    for (UhcPlayer player : UhcPlugin.getPlugin().getUhc().getGame().getPlayers()) {
      CustomCraftUhcPlayer customCraftUhcPlayer = (CustomCraftUhcPlayer) player;
      if (customCraftUhcPlayer.getTrinketBagItem()
          .hasTrinket(HealerVocationRegistry.get().getSecondTrinket())) {
        player.getHandle().setHealth(player.getHandle().getMaxHealth());
        TextController.send(
            player,
            TextType.INFORMATION,
            "Your <h>{0}</h> has granted you a <h>final heal</h>.",
            "Asclepius' Grace"
        );
      }
    }
  }

  @Override
  public boolean canRemove(CustomCraftUhcPlayer player) {
    return false;
  }
}
