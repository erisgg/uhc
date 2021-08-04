package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import java.util.List;
import javax.swing.DefaultFocusManager;
import lombok.Builder;
import lombok.Value;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Builder
@Value
public class CraftableInfo {

  ItemStack base;
  String name;
  String quote;
  String quoteGiver;
  String[] effects;
  CC color;


  public ItemStack buildTrinket() {
    return buildItem("TRINKET", this);
  }

  public ItemStack buildCraft() {
    return buildItem("CRAFT", this);
  }

  private static ItemStack buildItem(String prefix, CraftableInfo info) {
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

  public static class CraftableInfoBuilder {

    public CraftableInfoBuilder material(Material material) {
      this.base = new ItemStack(material);
      return this;
    }

    public CraftableInfoBuilder effects(String... effects) {
      this.effects = effects;
      return this;
    }

  }

}
