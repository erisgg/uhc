package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Builder
@Value
public class CraftableInfo {

  ItemStack base;
  ItemStack actual;
  String name;
  String quote;
  String quoteGiver;
  String[] effects;
  CC color;
  boolean withoutNbt;

  public ItemStack buildTrinket() {
    return buildDisplayItem("TRINKET", this);
  }

  public ItemStack buildDisplayCraft() {
    return buildDisplayItem("CRAFT", this);
  }

  public ItemStack buildActualCraft() {
    return this.actual == null ? buildDisplayCraft() : this.actual;
  }

  public ItemStack buildUltimateCraft() {
    return buildDisplayItem("ULTIMATE", this);
  }


  private static ItemStack buildDisplayItem(String prefix, CraftableInfo info) {
    ItemBuilder builder = new ItemBuilder(info.getBase())
        .withName(
            CC.WHITE.bold().underline() + prefix + ":" + info.getColor().bold()
                + " " + info.getName().toUpperCase())
        .addLore("");

    List<String> quotes = Text.splitWords(info.getQuote(), 6);
    if (quotes.size() == 1) {
      builder.addLore(
          info.getColor() + "\"" + info.getColor().italic() + quotes.get(0) + info.getColor()
              + "\" - " + info.getQuoteGiver());
    } else {
      builder.addLore(info.getColor() + "\"" + info.getColor().italic() + quotes.get(0));
    }
    for (int i = 1; i < quotes.size(); i++) {
      if (i + 1 == quotes.size()) {
        builder.addLore(info.getColor().italic() + quotes.get(i) + info.getColor() + "\" - " + info
            .getQuoteGiver());
      } else {
        builder.addLore(info.getColor().italic() + quotes.get(i));
      }
    }

    if (info.getEffects().length > 0) {
      builder.addLore(
          "",
          CC.GOLD.bold().underline() + "EFFECT" + (info.getEffects().length > 1 ? "S" : ""),
          ""
      );

      for (String effect : info.getEffects()) {
        List<String> effectList = Text.splitWords(effect, 6);
        builder.addLore(CC.WHITE + " - " + effectList.get(0));
        for (int i = 1; i < effectList.size(); i++) {
          builder.addLore(CC.WHITE + "   " + effectList.get(i));
        }
      }
    }

    return builder.build();
  }

  public static final class CraftableInfoBuilder {

    public CraftableInfoBuilder material(Material material) {
      this.base = new ItemStack(material);
      return this;
    }

    public CraftableInfoBuilder nonAnvillable() {
      this.base = new ItemBuilder(this.base).nonAnvillable().build();
      return this;
    }

    public CraftableInfoBuilder nonCraftable() {
      this.base = new ItemBuilder(this.base).nonCraftable().build();
      return this;
    }

    public CraftableInfoBuilder nonBrewable() {
      this.base = new ItemBuilder(this.base).nonBrewable().build();
      return this;
    }

    public CraftableInfoBuilder unstackable() {
      this.base = new ItemBuilder(this.base).unstackable().build();
      return this;
    }

    public CraftableInfoBuilder nonTransformable() {
      return nonAnvillable().nonCraftable().nonBrewable().unstackable();
    }

    public CraftableInfoBuilder effects(String... effects) {
      this.effects = effects;
      return this;
    }

  }

}
