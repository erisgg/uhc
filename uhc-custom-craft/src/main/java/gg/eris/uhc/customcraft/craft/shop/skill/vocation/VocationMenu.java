package gg.eris.uhc.customcraft.craft.shop.skill.vocation;

import gg.eris.commons.bukkit.menu.Menu;
import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.shop.BackMenuItem;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import org.checkerframework.checker.index.qual.PolyUpperBound;

public final class VocationMenu extends Menu {

  private static final int BACK_SLOT = 49;

  public static final int FIRST_CRAFT_SLOT = 9;
  public static final int SECOND_CRAFT_SLOT = 27;
  public static final int THIRD_CRAFT_SLOT = 22;
  public static final int FOURTH_CRAFT_SLOT = 25;
  public static final int FIRST_TRINKET_SLOT = 21;
  public static final int SECOND_TRINKET_SLOT = 26;

  public static final int[] PERK_SLOTS = new int[]{
      10, 11, 12, 28, 29, 30, 14, 15, 32, 33
  };

  public static final IntSet PERK_SLOTS_SET = IntSets.unmodifiable(new IntArraySet(PERK_SLOTS));

  private final Vocation vocation;

  public VocationMenu(UhcPlugin plugin, Menu parent, Vocation vocation) {
    super(plugin, CustomCraftUhcIdentifiers.MENU_ID.id("vocation-menu-" + vocation.name()), 6);
    this.vocation = vocation;

    setFillItem(Menu.DARK_FILLER);
    setParent(parent);

    if (vocation.getRegistry() == null) {
      return;
    }

    // Hardcoded joy :D :D :D :D
    addItem(9, new VocationMenuItem(vocation.getRegistry().getFirstCraft(), 100, FIRST_CRAFT_SLOT));
    addItem(27, new VocationMenuItem(vocation.getRegistry().getSecondCraft(), 100, SECOND_CRAFT_SLOT));
    addItem(10, new VocationMenuItem(vocation.getRegistry().getPerk(), 250, PERK_SLOTS[0], FIRST_CRAFT_SLOT));
    addItem(11, new VocationMenuItem(vocation.getRegistry().getPerk(), 500, PERK_SLOTS[1], PERK_SLOTS[0]));
    addItem(12, new VocationMenuItem(vocation.getRegistry().getPerk(), 1_000, PERK_SLOTS[2], PERK_SLOTS[1]));
    addItem(28, new VocationMenuItem(vocation.getRegistry().getPerk(), 250, PERK_SLOTS[3], SECOND_CRAFT_SLOT));
    addItem(29, new VocationMenuItem(vocation.getRegistry().getPerk(), 500, PERK_SLOTS[4], PERK_SLOTS[3]));
    addItem(30, new VocationMenuItem(vocation.getRegistry().getPerk(), 1_000, PERK_SLOTS[5], PERK_SLOTS[4]));
    addItem(21, new VocationMenuItem(vocation.getRegistry().getFirstTrinket(), 5_000, FIRST_TRINKET_SLOT, PERK_SLOTS[2], PERK_SLOTS[5]));
    addItem(22, new VocationMenuItem(vocation.getRegistry().getThirdCraft(), 7_500, THIRD_CRAFT_SLOT, PERK_SLOTS[2], PERK_SLOTS[5]));
    addItem(14, new VocationMenuItem(vocation.getRegistry().getPerk(), 10_000, PERK_SLOTS[6], THIRD_CRAFT_SLOT));
    addItem(15, new VocationMenuItem(vocation.getRegistry().getPerk(), 15_000, PERK_SLOTS[7], PERK_SLOTS[6]));
    addItem(32, new VocationMenuItem(vocation.getRegistry().getPerk(), 10_000, PERK_SLOTS[8], THIRD_CRAFT_SLOT));
    addItem(33, new VocationMenuItem(vocation.getRegistry().getPerk(), 15_000, PERK_SLOTS[9], PERK_SLOTS[8]));
    addItem(25, new VocationMenuItem(vocation.getRegistry().getFourthCraft(), 25_000, FOURTH_CRAFT_SLOT, PERK_SLOTS[7], PERK_SLOTS[9]));
    addItem(26, new VocationMenuItem(vocation.getRegistry().getSecondTrinket(), 25_000, SECOND_TRINKET_SLOT, FOURTH_CRAFT_SLOT));

    addItem(BACK_SLOT, new BackMenuItem());
  }

  @Override
  public String getTitle(MenuViewer viewer) {
    return this.vocation.getDisplay() + " Shop";
  }
}
