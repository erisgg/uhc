package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.lang.reflect.Field;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class GoldenHeadCraft extends Craft {

  // The problem blob
  private static final String GOLD_HEAD_SKIN_BLOB =
      "ewogICJ0aW1lc3RhbXAiIDogMTYyODkyNTc5ODQ0NSwKICAicHJvZmlsZUlkIiA6ICJjNjc3MGJjZWMzZjE0ODA3ODc4MTU0NWRhMGFmMDI1NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE2IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzE5NjU4ODY4OGJmNGJmNTgyYmRmMzU5NWNiZTc2YmVhNTVhODFmYTFmZDI1NGEyNjAxZjBjZmM4MjMxMmI5MWMiCiAgICB9CiAgfQp9";

  private static final UUID HEAD_UUID = UUID.randomUUID();

  public GoldenHeadCraft() {
    super("golden_head",
        CraftableInfo.builder().base(new ItemBuilder(new ItemStack(Material.SKULL_ITEM, 1,
            (short) SkullType.PLAYER.ordinal()))
            .applyMeta(SkullMeta.class, (meta) -> {
              GameProfile profile = new GameProfile(HEAD_UUID, null);
              profile.getProperties()
                  .put("textures", new Property("textures", GOLD_HEAD_SKIN_BLOB));

              try {
                Field field = meta.getClass().getDeclaredField("profile");
                field.setAccessible(true);
                field.set(meta, profile);
                field.setAccessible(false);
              } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException err) {
                err.printStackTrace();
              }
            }).build())
            .color(CC.YELLOW)
            .name("Golden Head")
            .quote("Shiny")
            .quoteGiver("Tamatoa")
            .effects("Gives Renegeration IV for 5 seconds", "Gives Absorption I for 1:30 seconds")
            .nonTransformable()
            .build()
    );
  }

  @Override
  public Vocation getVocation() {
    return Vocation.HEALER;
  }

  @Override
  public int getCraftableAmount() {
    return 3;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 4;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape("ggg", "gsg", "ggg")
        .setIngredient('g', Material.GOLD_INGOT)
        .setIngredient('s',
            new MaterialData(Material.SKULL_ITEM, (byte) SkullType.PLAYER.ordinal()));
  }

  @Override
  public String getName() {
    return "Golden Head";
  }

  @EventHandler(priority = EventPriority.LOW)
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player handle = event.getPlayer();
    ItemStack item = handle.getItemInHand();

    if (!isItem(item)) {
      return;
    }

    if (event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      event.getPlayer()
          .addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 3), true);
      event.getPlayer()
          .addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 90 * 20, 0), true);

      int healerLevel =
          ((CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame().getPlayer(handle))
              .getPerkLevel(Vocation.HEALER.getRegistry().getPerk());

      if (healerLevel > 0) {
        int duration = (15 + (healerLevel - 1)) * 20;
        event.getPlayer()
            .addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 1), true);
      }

      event.setCancelled(true);
      if (!StackUtil.decrement(item)) {
        event.getPlayer().setItemInHand(null);
      }
      event.getPlayer().updateInventory();
    }
  }

}
