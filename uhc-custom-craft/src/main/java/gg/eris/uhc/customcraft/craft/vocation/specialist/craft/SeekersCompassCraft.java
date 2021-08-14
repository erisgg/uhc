package gg.eris.uhc.customcraft.craft.vocation.specialist.craft;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class SeekersCompassCraft extends Craft {

  private static final String USES_KEY = "seekers_uses";

  public SeekersCompassCraft() {
    super("seekers_compass", CraftableInfo.builder()
        .material(Material.COMPASS)
        .color(CC.GREEN)
        .name("Seeker's Compass")
        .quote("All you need is a compass")
        .quoteGiver("Bear Grylls")
        .effects(
            "Right click to locate the nearest player",
            "Can be used twice per craft"
        )
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }

  @Override
  public int getCraftableAmount() {
    return 1;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 2;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " R ",
            "rcr",
            " r "
        )
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('R', Material.REDSTONE_BLOCK)
        .setIngredient('c', Material.COMPASS);
  }

  @Override
  public String getName() {
    return "Seeker's Compass";
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInteract(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (!(isItem(item))) {
      return;
    }

    Player player = event.getPlayer();

    int uses = NBTUtil.getIntNbtData(item, USES_KEY);
    if (uses < 2) {
      item = NBTUtil.setNbtData(item, USES_KEY, uses + 1);
      player.setItemInHand(item);
    } else {
      TextController.send(
          player,
          TextType.ERROR,
          "This <h>{0}</h> has already been used <h>twice</h>.",
          getName()
      );
      return;
    }

    Player closestPlayer = null;
    double dist = Double.MAX_VALUE;

    for (Player target : Bukkit.getOnlinePlayers()) {
      if (target.equals(player) || !UhcPlugin.getPlugin().getUhc().getGame().isPlayer(target) ||
          !target.getWorld().equals(player.getWorld())) {
        continue;
      }

      double targetDistance = player.getLocation().distance(target.getLocation());
      if (targetDistance < dist) {
        dist = targetDistance;
        closestPlayer = target;
      }
    }

    if (closestPlayer != null) {
      player.setCompassTarget(closestPlayer.getLocation());
      TextController.send(
          player,
          TextType.INFORMATION,
          "A player has been <h>found</h> (<h>{0}</h> blocks away).",
          Text.formatDouble(dist)
      );
    } else {
      TextController.send(
          player,
          TextType.ERROR,
          "Could not locate a player. They may be in another dimension?"
      );
    }
  }

}
