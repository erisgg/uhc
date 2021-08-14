package gg.eris.uhc.customcraft.craft.vocation.extractor.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class DionysusBrewTrinket extends Trinket {

  public DionysusBrewTrinket() {
    super("dionysus_brew", CraftableInfo.builder()
        .material(Material.ROTTEN_FLESH)
        .name("Dionysus' Brew")
        .color(CC.DARK_GREEN)
        .quote("Don't drink it.")
        .quoteGiver("Dionysus")
        .effects(
            "50% chance for mobs to double their loot drops"
        ).nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " r ",
            "bfb",
            " b "
        ).setIngredient('r', Material.ROTTEN_FLESH)
        .setIngredient('b', new MaterialData(Material.INK_SACK, DataUtil.BONE_MEAL))
        .setIngredient('f', Material.FLINT_AND_STEEL);
  }

  @Override
  public String getName() {
    return "Dionysus' Brew";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.EXTRACTOR;
  }

  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    if (event.getEntity() instanceof Monster) {
      if (event.getEntity().getKiller() == null) {
        return;
      }

      Player handle = event.getEntity().getKiller();
      CustomCraftUhcPlayer player =
          (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle);

      if (player.getTrinketBagItem().hasTrinket(this)) {
        if (RandomUtil.percentChance(50)) {
          StackUtil.dropItems(event.getEntity().getLocation(), event.getDrops(), true);
        }
      }
    }
  }

}
