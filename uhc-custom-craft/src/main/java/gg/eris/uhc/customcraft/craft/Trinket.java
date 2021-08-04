package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import java.util.List;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Trinket extends Unlockable implements Craftable, Listener {

  private final ItemStack item;
  @Getter
  private final TrinketInfo info;

  public Trinket(String identifierValue, TrinketInfo info) {
    super(identifierValue);
    this.info = info;

    ItemBuilder builder = new ItemBuilder(info.getMaterial())
        .withName(
            CC.WHITE.bold().underline() + "TRINKET:" + info.getColor().bold()
                + " " + info.getName().toUpperCase())
        .addLore("");

    List<String> quotes = Text.splitWords(info.getQuote(), 4);
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

    builder.addLore(
        "",
        CC.GOLD.bold() + "EFFECT",
        ""
    );

    List<String> effect = Text.splitWords(info.getEffect(), 4);
    builder.addLore(CC.WHITE + " - " + effect.get(0));
    for (int i = 1; i < effect.size(); i++) {
      builder.addLore(CC.WHITE + "   " + effect.get(i));
    }

    this.item = builder.build();
  }

  public final ItemStack getItem() {
    return this.item;
  }

}
