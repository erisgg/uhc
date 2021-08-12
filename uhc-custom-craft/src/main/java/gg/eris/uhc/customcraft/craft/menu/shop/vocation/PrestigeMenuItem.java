package gg.eris.uhc.customcraft.craft.menu.shop.vocation;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuItem;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public class PrestigeMenuItem implements MenuItem {

  private static final int FIRST_PRICE = 50_000;
  private static final int SECOND_PRICE = 100_000;

  private static final ItemStack FIRST_LOCKED;
  private static final ItemStack SECOND_LOCKED;
  private static final ItemStack FIRST_PURCHASABLE;
  private static final ItemStack SECOND_PURCHASABLE;
  private static final ItemStack FIRST_UNLOCKED;
  private static final ItemStack SECOND_UNLOCKED;

  static {
    ItemStack firstBase = new ItemBuilder(Material.STAINED_GLASS)
        .withName(CC.WHITE.bold().underline() + "PRESTIGE I")
        .withLore(
            "",
            CC.GOLD.bold().underline() + "EFFECT",
            "",
            CC.WHITE + " - You can craft everything one more time",
            ""
        ).build();

    ItemStack secondBase = new ItemBuilder(Material.STAINED_GLASS)
        .withName(CC.WHITE.bold().underline() + "PRESTIGE II")
        .withLore(
            "",
            CC.GOLD.bold().underline() + "EFFECT",
            "",
            CC.WHITE + " - Crafts, trinkets and perks become stronger",
            ""
        ).build();

    FIRST_LOCKED = new ItemBuilder(firstBase)
        .addLore(CC.RED.bold() + "LOCKED")
        .withDurability(DataUtil.RED)
        .build();

    FIRST_PURCHASABLE = new ItemBuilder(firstBase)
        .addLore(CC.GOLD.bold() + "CLICK TO PURCHASE (" + Text.formatInt(FIRST_PRICE) + " COINS)")
        .withDurability(DataUtil.RED)
        .build();

    FIRST_UNLOCKED = new ItemBuilder(firstBase)
        .addLore(CC.GREEN.bold() + "UNLOCKED")
        .withDurability(DataUtil.LIME)
        .build();

    SECOND_LOCKED = new ItemBuilder(secondBase)
        .addLore(CC.RED.bold() + "LOCKED")
        .withDurability(DataUtil.RED)
        .build();

    SECOND_PURCHASABLE = new ItemBuilder(secondBase)
        .addLore(CC.GOLD.bold() + "CLICK TO PURCHASE (" + Text.formatInt(SECOND_PRICE) + " COINS)")
        .withDurability(DataUtil.RED)
        .build();

    SECOND_UNLOCKED = new ItemBuilder(secondBase)
        .addLore(CC.GREEN.bold() + "UNLOCKED")
        .withDurability(DataUtil.LIME)
        .build();
  }

  private final Vocation vocation;
  private final boolean first;

  @Override
  public ItemStack getItem(MenuViewer menuViewer, Menu menu) {
    CustomCraftUhcPlayer player = menuViewer.getErisPlayer();

    int prestigeLevel = player.getPrestigeLevel(this.vocation);
    if (prestigeLevel == 2) {
      return this.first ? FIRST_UNLOCKED : SECOND_UNLOCKED;
    } else if (prestigeLevel == 1) {
      return this.first ? FIRST_UNLOCKED : SECOND_PURCHASABLE;
    } else {
      return this.first ? (hasFullTree(player) ? FIRST_PURCHASABLE : FIRST_LOCKED) : SECOND_LOCKED;
    }
  }

  @Override
  public void onClick(MenuViewer menuViewer, InventoryClickEvent event) {
    CustomCraftUhcPlayer player = menuViewer.getErisPlayer();

    int prestigeLevel = player.getPrestigeLevel(this.vocation);

    if (prestigeLevel == 2 || (prestigeLevel == 1 && this.first)) {
      return;
    }

    if (this.first) {
      if (!hasFullTree(player)) {
        TextController.send(
            player.getHandle(),
            TextType.ERROR,
            "You need the <h>full</h> skill tree in order to buy this prestige."
        );
        return;
      }

      if (FIRST_PRICE > player.getCoins()) {
        TextController.send(
            player.getHandle(),
            TextType.ERROR,
            "You do not have enough coins to prestige. You need <h>{0}</h> more coins.",
            Text.formatInt(FIRST_PRICE - player.getCoins())
        );
      } else {
        TextController.send(
            player.getHandle(),
            TextType.SUCCESS,
            "You have bought the <h>first</h> prestige for the <h>{0}</h> skill tree.",
            this.vocation.getDisplay()
        );
        player.addPrestige(this.vocation);
        menuViewer.getViewing().updateMenu(menuViewer);
      }
    } else {
      if (prestigeLevel != 1) {
        TextController.send(
            player.getHandle(),
            TextType.ERROR,
            "You need to buy the <h>first</h> prestige before you can buy the second."
        );
        return;
      }

      if (SECOND_PRICE > player.getCoins()) {
        TextController.send(
            player.getHandle(),
            TextType.ERROR,
            "You do not have enough coins to prestige. You need <h>{0}</h> more coins.",
            Text.formatInt(SECOND_PRICE - player.getCoins())
        );
      } else {
        TextController.send(
            player.getHandle(),
            TextType.SUCCESS,
            "You have bought the <h>second</h> prestige for the <h>{0}</h> skill tree.",
            this.vocation.getDisplay()
        );
        player.addPrestige(this.vocation);
        menuViewer.getViewing().updateMenu(menuViewer);
      }
    }
  }

  private boolean hasFullTree(CustomCraftUhcPlayer player) {
    for (VocationUnlockable unlockable : this.vocation.getRegistry().values()) {
      if (!player.hasUnlockable(unlockable)) {
        return false;
      }
    }

    return true;
  }

}