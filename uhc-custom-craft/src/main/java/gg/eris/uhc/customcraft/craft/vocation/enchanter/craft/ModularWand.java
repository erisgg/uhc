package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import java.util.concurrent.TimeUnit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ModularWand extends Craft {

  private static final String COOLDOWN_KEY = "modular_wand_cooldown";
  private static final String MODE_KEY = "modular_wand_mode";
  private static final long COOLDOWN = 10_000;

  public enum Mode {
    LIGHTNING,
    FIRE;
  }

  public ModularWand() {
    super("modular_wand", CraftableInfo.builder()
        .material(Material.BLAZE_ROD)
        .color(CC.RED)
        .name("Modular Wand")
        .quote("Expelliarmus!")
        .quoteGiver("Harry Potter")
        .effects(
            "Lightning mode: Hit an opponent to strike them with lightning",
            "Fire mode: Hit an opponent to set them on fire",
            "Right click to swap",
            "10s cooldown"
        )
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
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
            " t ",
            " s ",
            "pbp"
        )
        .setIngredient('t', Material.TNT)
        .setIngredient('s', Material.SKULL_ITEM)
        .setIngredient('p', Material.BLAZE_POWDER)
        .setIngredient('b', Material.BLAZE_ROD);
  }

  @Override
  public String getName() {
    return "Modular Wand";
  }


  @EventHandler(ignoreCancelled = true)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getEntityType() != EntityType.PLAYER
        || event.getDamager().getType() != EntityType.PLAYER) {
      return;
    }

    Player damager = (Player) event.getDamager();
    Player damaged = (Player) event.getEntity();

    ItemStack item = damager.getItemInHand();

    if (!isItem(item)) {
      return;
    }

    long canUse = NBTUtil.getLongNbtData(item, COOLDOWN_KEY);
    if (canUse != 0) {
      if (System.currentTimeMillis() < canUse) {
        TextController.send(
            damager,
            TextType.ERROR,
            "You can use the <h>{0}</h> again in <h>{1}</h>.",
            getName(),
            Time.toShortDisplayTime(canUse - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
        );
        return;
      }
    }

    item = NBTUtil.setNbtData(item, COOLDOWN_KEY, System.currentTimeMillis() + COOLDOWN);
    damager.setItemInHand(item);

    int modeOrdinal = NBTUtil.getIntNbtData(item, MODE_KEY);
    if (modeOrdinal < 0 || modeOrdinal > Mode.values().length) {
      modeOrdinal = 0;
    }

    Mode mode = Mode.values()[modeOrdinal];

    if (mode == Mode.LIGHTNING) {
      damaged.setHealth(Math.max(0, damaged.getHealth() - 3));
      damaged.getWorld().strikeLightningEffect(damaged.getLocation());
      TextController.send(
          damager,
          TextType.INFORMATION,
          "You struck <h>{0}</h> with lightning from your <h>{1}</h>.",
          UhcPlugin.getPlugin().getCommons().getErisPlayerManager().getPlayer(damaged)
              .getDisplayName(),
          getName()
      );
    } else {
      damaged.setFireTicks(20 * 5);
      TextController.send(
          damager,
          TextType.INFORMATION,
          "You set <h>{0}</h> on fire with your <h>{1}</h>.",
          UhcPlugin.getPlugin().getCommons().getErisPlayerManager().getPlayer(damaged)
              .getDisplayName(),
          getName()
      );
    }
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_AIR
        && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    ItemStack item = event.getItem();
    if (!isItem(item)) {
      return;
    }

    int modeOrdinal = NBTUtil.getIntNbtData(item, MODE_KEY);
    if (modeOrdinal < 0 || modeOrdinal > Mode.values().length) {
      modeOrdinal = 0;
    }

    if (modeOrdinal == 0) {
      modeOrdinal = 1;
    } else {
      modeOrdinal = 0;
    }

    event.getPlayer().setItemInHand(NBTUtil.setNbtData(item, MODE_KEY, modeOrdinal));

    Mode mode = Mode.values()[modeOrdinal];
    if (mode == Mode.LIGHTNING) {
      TextController.send(
          event.getPlayer(),
          TextType.INFORMATION,
          "Your <h>{0}</h> is now in <h>lightning</h> mode.",
          getName()
      );
    } else {
      TextController.send(
          event.getPlayer(),
          TextType.INFORMATION,
          "Your <h>{0}</h> is now in <h>fire</h> mode.",
          getName()
      );
    }
  }

}
