package gg.eris.uhc.customcraft.craft.vocation.duelist.craft;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.bukkit.util.RomanNumeral;
import gg.eris.uhc.core.event.UhcPlayerDeathEvent;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.customcraft.craft.Tickable;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public final class SoulThirsterCraft extends Craft implements Tickable {

  private static final String NBT_KEY = "soul_thirster_decay";
  private static final long DECAY_TIME = 1000 * 60 * 5;

  public SoulThirsterCraft() {
    super("soul_thirster", CraftableInfo.builder()
        .base(new ItemBuilder(Material.DIAMOND_SWORD)
            .withEnchantment(Enchantment.DAMAGE_ALL, 1)
            .build())
        .color(CC.DARK_RED)
        .name("Soul Thirster")
        .quote("I want to suck your blood!")
        .quoteGiver("Dracula")
        .effects(
            "Sharpness I Diamond Sword",
            "Increases by 1 sharpness level each kill, up to Sharpness IV",
            "Decays a sharpness level every 5 minutes without a kill, down to Sharpness I"
        ).nonTransformable()
        .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
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
            "xdx",
            "lsl",
            "xLx"
        ).setIngredient('x', Material.EXP_BOTTLE)
        .setIngredient('d', Material.DIAMOND)
        .setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
        .setIngredient('L', Material.LAPIS_BLOCK)
        .setIngredient('s', Material.DIAMOND_SWORD);
  }

  @Override
  public String getName() {
    return "Soul Thirster";
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onKill(UhcPlayerDeathEvent event) {
    Player killer = event.getKiller().getHandle();

    if (killer == null) {
      return;
    }

    // Checking for arrows and switching hand
    if (event.getHandle() instanceof EntityDamageByEntityEvent && ((EntityDamageByEntityEvent) event.getHandle()).getDamager().getType() != EntityType.PLAYER) {
      return;
    } else if (event.getKilled().getLastAttacker() == null || System.currentTimeMillis() - event.getKilled().getLastAttacker().getValue() > 1000) {
      Bukkit.broadcastMessage("Last atacker is: " + event.getKilled().getLastAttacker());
      // Only level up the sword if they log out within a second of last being hit
      // a not-100%-safe way of checking if they were last hit
      // TODO: Switch to storing data about the last damage event they took from the attacker
      // TOOD: and checking that
      return;
    }

    ItemStack item = killer.getItemInHand();
    if (isItem(item)) {
      int sharpnessLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
      if (sharpnessLevel >= 4) {
        return;
      }

      sharpnessLevel++;
      item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, sharpnessLevel);
      item = NBTUtil.setNbtData(item, NBT_KEY, System.currentTimeMillis());
      killer.setItemInHand(item);
      TextController.send(
          killer,
          TextType.INFORMATION,
          "Your <h>{0}</h> has levelled up to <h>Sharpness {1}</h>.",
          getName(),
          RomanNumeral.toRoman(sharpnessLevel)
      );
    }
  }

  @Override
  public void tick(UhcTickEvent event, ItemStack item, int itemSlot, ErisPlayer player) {
    int sharpnessLevel = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
    if (sharpnessLevel == 1) {
      return;
    }

    long time = NBTUtil.getLongNbtData(item, NBT_KEY);
    if (time == 0) {
      return;
    }

    long current = System.currentTimeMillis();
    if (current - DECAY_TIME > time) {
      sharpnessLevel--;
      item.addEnchantment(Enchantment.DAMAGE_ALL, sharpnessLevel);
      item = NBTUtil.setNbtData(item, NBT_KEY, System.currentTimeMillis());
      player.getHandle().getInventory().setItem(itemSlot, item);
      TextController.send(
          player,
          TextType.INFORMATION,
          "Your <h>{0}</h> has decayed to <h>Sharpness {1}</h>",
          getName(),
          RomanNumeral.toRoman(sharpnessLevel)
      );
    }


  }
}
