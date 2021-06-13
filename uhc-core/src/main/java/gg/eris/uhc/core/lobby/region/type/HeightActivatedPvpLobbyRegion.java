package gg.eris.uhc.core.lobby.region.type;

import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HeightActivatedPvpLobbyRegion extends LobbyRegion {

  private static final ItemStack SWORD_ITEM;
  private static final ItemStack ROD_ITEM;
  private static final ItemStack BOW_ITEM;
  private final static ItemStack ARROW_ITEM;
  private final static ItemStack[] ARMOR;

  static {
    SWORD_ITEM = new ItemBuilder(Material.DIAMOND_SWORD).unbreakable().build();
    ROD_ITEM = new ItemBuilder(Material.FISHING_ROD).unbreakable().build();
    BOW_ITEM = new ItemBuilder(Material.BOW).unbreakable().build();
    ARROW_ITEM = new ItemStack(Material.ARROW, 8);
    ARMOR = new ItemStack[]{
        new ItemBuilder(Material.DIAMOND_BOOTS).unbreakable().build(),
        new ItemBuilder(Material.CHAINMAIL_LEGGINGS).unbreakable().build(),
        new ItemBuilder(Material.IRON_CHESTPLATE).unbreakable().build(),
        new ItemBuilder(Material.DIAMOND_HELMET).unbreakable().build()
    };
  }

  private final int y;

  public HeightActivatedPvpLobbyRegion(UhcPlugin plugin, Lobby lobby, int y) {
    super(plugin, lobby);
    this.y = y;
  }

  @Override
  public boolean isInRegion(Location location) {
    return location.getY() <= this.y;
  }

  @Override
  public void onEnter(Player player) {
    player.setGameMode(GameMode.ADVENTURE);
    PlayerUtil.resetPlayer(player);
    player.getInventory().addItem(SWORD_ITEM, ROD_ITEM, BOW_ITEM, ARROW_ITEM);
    player.getInventory().setArmorContents(ARMOR);
    player.updateInventory();
  }

  @Override
  public void onLeave(Player player) {
  }




}
