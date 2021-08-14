package gg.eris.uhc.customcraft.craft.vocation.healer.craft;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public final class GoldenHeadCraft extends Craft {
  // The problem blob
  private static final String GOLD_HEAD_SKIN_BLOB = "ewogICJ0aW1lc3RhbXAiIDogMTYyODg5OTc1MzMzNCwKICAicHJvZmlsZUlkIiA6ICI4MTExMzRmM2E4ZmY0N2ZmYWRjODRlMWYyZjI3NTdhMyIsCiAgInByb2ZpbGVOYW1lIiA6ICJBbGZpZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iNTE0Yjk2YTk3NmM4YWNhMTRlODI3MDM3NjNkYTVmN2UxNDNkZTUxYzU5ZTY2NTBiZGI0MWY1MTNmMjlhMTc0IgogICAgfQogIH0KfQ==";

  private static final UUID HEAD_UUID = UUID.randomUUID();

  public GoldenHeadCraft() {
    super("golden_head", new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()));

    formatSkull(getItem(), getName());
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
        .setIngredient('s', Material.SKULL_ITEM);
  }

  @Override
  public String getName() {
    return "Golden Head";
  }

  private void formatSkull(ItemStack skull, String displayName) {
    SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

    GameProfile profile = new GameProfile(HEAD_UUID, null);
    profile.getProperties().put("textures", new Property("textures", GOLD_HEAD_SKIN_BLOB));

    try {
      Field field = skullMeta.getClass().getDeclaredField("profile");
      field.setAccessible(true);
      field.set(skullMeta, profile);
      field.setAccessible(false);
    } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException err) {
      err.printStackTrace();
    }

    skullMeta.setDisplayName(displayName);
    skull.setItemMeta(skullMeta);
  }
}
