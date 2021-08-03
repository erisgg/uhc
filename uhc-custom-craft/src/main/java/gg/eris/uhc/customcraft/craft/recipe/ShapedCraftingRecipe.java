package gg.eris.uhc.customcraft.craft.recipe;

import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public abstract class ShapedCraftingRecipe {

  @Getter
  private final Function<CustomCraftUhcPlayer, ItemStack> resultFunction;
  private final Material[][] ingredients;

  public final boolean applicable(ItemStack[][] matrix) {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        ItemStack matrixItem = matrix[x][y];
        Material wanted = this.ingredients[x][y];
        boolean matrixItemNull = StackUtil.isNullOrAir(matrixItem);
        if (!matrixItemNull) {
          if (matrixItem.getType() != wanted) {
            return false;
          }
        } else if (wanted != null) {
          return false;
        }
      }
    }

    return true;
  }

  // Consumes the correct amounts, ran on the assumption it matches
  public final void consume(ItemStack[][] matrix) {
    for (int x = 0; x < 3; x++) {
      for (int y = 0; y < 3; y++) {
        ItemStack matrixItem = matrix[x][y];
        if (!StackUtil.isNullOrAir(matrixItem)) {
          boolean exists = StackUtil.decrement(matrixItem);
          if (!exists) {
            matrix[x][y] = null;
          }
        }
      }
    }
  }

  public static class MaterialBuilder {

    private final Char2ObjectMap<Material> itemMap;

    public MaterialBuilder() {
      this.itemMap = new Char2ObjectArrayMap<>();
    }

    private String[] shape;

    public MaterialBuilder shape(String... shape) {
      Validate.isTrue(shape.length == 3, "matrix is 3x3");
      for (String s : shape) {
        Validate.isTrue(s.length() == 3, "matrix is 3x3");
      }
      this.shape = shape;
      return this;
    }

    public MaterialBuilder setIngredient(char key, Material material) {
      this.itemMap.put(key, material);
      return this;
    }

    public Material[][] build() {
      Material[][] wanted = new Material[3][];
      for (int i = 0; i < 3; i++) {
        wanted[i] = new Material[3];
      }

      for (int y = 0; y < 3; y++) {
        String line = this.shape[y];
        for (int x = 0; x < 3; x++) {
          char character = line.charAt(x);
          Material wantedType = null;
          if (character != ' ') {
            Validate.isTrue(this.itemMap.containsKey(character), "character '" + character
                + "' not in map");
            wantedType = this.itemMap.get(character);
          }
          wanted[y][x] = wantedType;
        }
      }

      return wanted;
    }

  }

}
