package gg.eris.uhc.customcraft.craft.menu.kit;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.kit.Kit;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import lombok.RequiredArgsConstructor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class KitMenuItem implements MenuItem {

  private static final Int2IntMap PRICES;

  static {
    Int2IntMap map = new Int2IntArrayMap();
    map.put(1, 500);
    map.put(2, 5000);
    map.put(3, 25000);
    map.put(4, 100_000);
    PRICES = Int2IntMaps.unmodifiable(map);
  }

  private final Kit kit;

  @Override
  public ItemStack getItem(MenuViewer viewer, Menu menu) {
    CustomCraftUhcPlayer player = viewer.getErisPlayer();
    int level = player.getKitLevel(this.kit);

    ItemBuilder builder = new ItemBuilder(this.kit.getDisplay())
        .withName(this.kit.getItemDisplayName() + " (Level " + (level + 1) + ")");

    if (this.kit.getIdentifier().equals(player.getActiveKit())) {
      builder.withEnchantment(Enchantment.LUCK, 1)
          .withFlags(ItemFlag.HIDE_ENCHANTS);
    }

    if (level < 4) {
      builder.addLore(CC.RED + "Right click to level up (-" +
          Text.formatInt(PRICES.get(level + 1))
          + " coins)");
    }

    return builder.build();
  }

  @Override
  public void onClick(MenuViewer viewer, InventoryClickEvent event) {
    CustomCraftUhcPlayer player = viewer.getErisPlayer();

    if (event.isRightClick()) {
      int level = player.getKitLevel(this.kit);
      if (level == 5) {
        return;
      }

      int cost = PRICES.get(level + 1);
      if (player.getCoins() < cost) {
        TextController.send(
            player.getHandle(),
            TextType.ERROR,
            "You do not have enough coins to upgrade the <h>{0}</h> kit. You need <h>{1}</h> more "
                + "coins.",
            this.kit.getName(),
            Text.formatInt(cost - player.getCoins())
        );
      } else {
        player.spendCoins(cost);
        TextController.send(
            player.getHandle(),
            TextType.SUCCESS,
            "You have upgraded the <h>{0}</h> kit (-<h>{1}</h> coins).",
            this.kit.getName(),
            Text.formatInt(cost)
        );
        player.levelUpKit(this.kit);
      }
    } else {
      player.setActiveKit(this.kit);
      TextController.send(
          player.getHandle(),
          TextType.SUCCESS,
          "You have selected the <h>{0}</h> kit.",
          this.kit.getName()
      );
    }

    viewer.getViewing().updateMenu(viewer);
  }

}
