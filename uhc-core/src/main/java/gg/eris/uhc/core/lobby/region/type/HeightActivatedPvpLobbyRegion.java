package gg.eris.uhc.core.lobby.region.type;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class HeightActivatedPvpLobbyRegion extends BlockSafeLobbyRegion {

  private static final int UPPER_LIMIT = 60;
  private static final int LOWER_LIMIT = 20;

  private static final ItemStack SWORD_ITEM;
  private static final ItemStack ROD_ITEM;
  private static final ItemStack BOW_ITEM;
  private final static ItemStack ARROW_ITEM;
  private final static ItemStack[] ARMOR;
  private final static ItemStack GAPPLE_REWARD;
  private final static ItemStack ARROW_REWARD;

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
    GAPPLE_REWARD = new ItemBuilder(Material.GOLDEN_APPLE).withLore(
        CC.YELLOW.italic() + "Heals you back to full HP").build();
    ARROW_REWARD = new ItemStack(Material.ARROW, 2);
  }

  public HeightActivatedPvpLobbyRegion(UhcPlugin plugin, Lobby lobby) {
    super(plugin, lobby);

    registerEntityEvent(FoodLevelChangeEvent.class, event -> {
      if (event.getFoodLevel() != 20) {
        Player player = (Player) event.getEntity();
        player.setSaturation(20f);
        event.setCancelled(true);
      }
    });

    registerPlayerEvent(PlayerDropItemEvent.class, event -> {
      if (event.getItemDrop().getItemStack().getType() != Material.GOLDEN_APPLE) {
        event.setCancelled(true);
      }
    });

    registerEntityEvent(EntityDamageEvent.class, event -> {
      if (!(event instanceof EntityDamageByEntityEvent)) {
        event.setCancelled(true);
      }
    });

    registerPlayerEvent(PlayerItemConsumeEvent.class, event -> {
      if (event.getItem().getType() == Material.GOLDEN_APPLE) {
        event.setCancelled(true);
        if (!StackUtil.decrement(event.getItem())) {
          event.getPlayer().setItemInHand(null);
        }

        event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
      }
    });

    registerPlayerEvent(PlayerMoveEvent.class, event -> {
      if (event.getTo().getBlockY() < LOWER_LIMIT) {
        lobby.reset(event.getPlayer());
        TextController.send(
            event.getPlayer(),
            TextType.INFORMATION,
            "You have fallen off of the map. Don't do that!"
        );
      }
    });

    registerEntityEvent(EntityDamageByEntityEvent.class, event -> {
      if (event.getDamager().getType() != EntityType.PLAYER
          || event.getEntityType() != EntityType.PLAYER) {
        return;
      }

      Player damager = (Player) event.getDamager();
      Player target = (Player) event.getEntity();

      if (target.getHealth() - event.getFinalDamage() > 0) {
        return;
      }

      event.setCancelled(true);
      lobby.reset(target);
      damager.getInventory().addItem(GAPPLE_REWARD);
      damager.getInventory().addItem(ARROW_REWARD);

      TextController.send(
          damager,
          TextType.INFORMATION,
          "You have killed <h>{0}</h>. +1 golden apple, +2 arrows",
          target.getName()
      );

      TextController.send(
          target,
          TextType.INFORMATION,
          "You have been killed by <h>{0}</h>",
          damager.getName()
      );
    });
  }

  @Override
  public boolean isInRegion(Location location) {
    return location.getY() <= UPPER_LIMIT;
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
