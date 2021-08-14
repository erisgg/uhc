package gg.eris.uhc.customcraft.game.listener;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.bukkit.tablist.TablistController;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenuViewer;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.leaderboard.Leaderboard;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

/**
 * MultiStage listener for waiting and countdown state
 */
public final class LobbyListener extends MultiStateListener {

  private static final Set<Type> TYPES = Set.of(
      TypeRegistry.WAITING,
      TypeRegistry.COUNTDOWN,
      TypeRegistry.STARTING
  );

  private final static int LOWER_LIMIT = 20;
  private static final int PVP_LIMIT = 55;

  // PvP items
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

  private final CustomCraftUhcGame game;
  private final ErisPlayerManager erisPlayerManager;
  private final Location spawn;
  private final Set<UUID> pvping;

  private final Leaderboard killsLeaderboard;
  private final Leaderboard winsLeaderboard;

  public LobbyListener(CustomCraftUhcGame game) {
    this.game = game;
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();
    this.spawn = new Location(
        new WorldCreator(CustomCraftUhcIdentifiers.PREGAME_WORLD).createWorld(),
        0.5,
        73,
        0.5,
        -90.0f,
        0.0f
    );

    this.pvping = Sets.newHashSet();

    this.killsLeaderboard = new Leaderboard(
        this.game.getPlugin(),
        "kills",
        new Location(new WorldCreator(CustomCraftUhcIdentifiers.PREGAME_WORLD).createWorld(),
            24.5,
            76.5,
            -8.5
        )
    );

    this.winsLeaderboard = new Leaderboard(
        this.game.getPlugin(),
        "wins",
        new Location(new WorldCreator(CustomCraftUhcIdentifiers.PREGAME_WORLD).createWorld(),
            29.5,
            76.5,
            -8.5
        )
    );
  }

  @Override
  protected Set<Type> getApplicableTypes() {
    return TYPES;
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {
    World world = this.spawn.getWorld();
    world.setWeatherDuration(0);
    world.setAutoSave(false);
    addTask(() -> Bukkit.getScheduler().runTaskTimer(this.game.getPlugin(), () -> {
      world.setFullTime(6000L);
      world.setTime(6000L);
    }, 0L, 1L).getTaskId());

    // Tablist
    TablistController tablistController = game.getPlugin().getCommons().getTablistController();
    tablistController.setHeader(CC.YELLOW + "You are playing " + CC.GREEN.bold() + "UHC"
        + CC.YELLOW + " on " + CC.GOLD.bold() + "ERIS.GG");
    tablistController.setFooter(CC.GOLD + "Visit our store at "
        + CC.YELLOW.bold() + "STORE.ERIS.GG");
    tablistController.setDisplayNameFunction((player, viewer) -> {
      Rank rank = player.getNicknameProfile().getPriorityDisplayRank();
      return rank == RankRegistry.get().DEFAULT ?
          CC.GRAY + player.getDisplayName() : rank.getColor().getColor() +
          "[" + rank.getRawDisplay() + "] " + CC.WHITE + player.getNicknameProfile()
          .getDisplayName();
    });

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {
    this.killsLeaderboard.remove();
    this.winsLeaderboard.remove();
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    event.setRespawnLocation(this.spawn);
    PlayerUtil.resetPlayer(event.getPlayer());
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    if (this.erisPlayerManager.getPlayers().size()
        < this.game.getSettings().getRequiredPlayers()
        && this.game.getGameState().getType() != TypeRegistry.WAITING) {
      this.game.setGameState(TypeRegistry.WAITING);
    }
    this.pvping.remove(event.getPlayer().getUniqueId());
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if ((!isInPvp(event.getEntity().getLocation()) || !(event instanceof EntityDamageByEntityEvent))
        && event.getCause() != DamageCause.VOID) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onItemConsume(PlayerItemConsumeEvent event) {
    if (event.getItem().getType() == Material.GOLDEN_APPLE) {
      if (!StackUtil.decrement(event.getItem())) {
        event.getPlayer().setItemInHand(null);
      }

      event.getPlayer().setHealth(event.getPlayer().getMaxHealth());
    }
  }

  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent event) {
    if (event.getItemDrop().getItemStack().getType() != Material.GOLDEN_APPLE
        || !isInPvp(event.getPlayer().getLocation())) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (isInPvp(event.getWhoClicked().getLocation())) {
      // allowing hotbar (slots from 36->44 inclusive both ends)
      Bukkit.broadcastMessage("raw: " + event.getRawSlot());
      if (event.getRawSlot() >= 0 && event.getRawSlot() <= 8) {
        if (event.getClick().isShiftClick()) {
          event.setCancelled(true);
        }
      } else {
        event.setCancelled(true);
      }
    } else {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!event.hasChangedBlock()) {
      return;
    }

    Player player = event.getPlayer();
    if (event.getTo().getBlockY() < LOWER_LIMIT) {
      event.getPlayer().setMaxHealth(20);
      PlayerUtil.resetPlayer(event.getPlayer());
      event.getPlayer().teleport(this.spawn);
      TextController.send(
          player,
          TextType.INFORMATION,
          "You fell off of the map... Don't do that!"
      );
      this.pvping.remove(player.getUniqueId());
    } else if (event.getTo().getBlockY() < PVP_LIMIT) {
      if (this.pvping.add(player.getUniqueId())) {
        player.getInventory().clear();
        player.setMaxHealth(40);
        player.setHealth(40);
        player.getInventory().setArmorContents(ARMOR);
        player.getInventory().addItem(SWORD_ITEM, ROD_ITEM, BOW_ITEM);
        player.getInventory().setItem(9, ARROW_ITEM);
      }
    }
  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() != EntityType.PLAYER
        || event.getEntityType() != EntityType.PLAYER || !isInPvp(event.getEntity().getLocation()))  {
      event.setCancelled(true);
    }
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void handlePvpDeath(EntityDamageByEntityEvent event) {
    Player damager = (Player) event.getDamager();
    Player target = (Player) event.getEntity();


    if (target.getHealth() - event.getFinalDamage() > 0) {
      return;
    }

    event.setCancelled(true);

    target.setMaxHealth(20);
    PlayerUtil.resetPlayer(target);
    target.teleport(this.spawn);

    damager.getInventory().addItem(GAPPLE_REWARD);
    damager.getInventory().addItem(ARROW_REWARD);

    TextController.send(
        damager,
        TextType.INFORMATION,
        "You have killed <h>{0}</h>. +1 golden apple, +2 arrows",
        target.getName()
    );

    PlayerUtil.playSound(damager, Sound.LEVEL_UP);

    TextController.send(
        target,
        TextType.INFORMATION,
        "You have been killed by <h>{0}</h>",
        damager.getName()
    );
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    event.setCancelled(true);
    event.setBuild(false);
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (isInPvp(event.getPlayer().getLocation())) {
      return;
    }

    event.setCancelled(true);

    if (event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      ItemStack item = event.getItem();
      if (StackUtil.isNullOrAir(item)) {
        return;
      }

      switch (item.getType()) {
        case EMERALD:
          this.game.getMainMenu().openMenu(event.getPlayer());
          break;
        case ENCHANTED_BOOK:
          this.game.getRecipeBookMenu().openMenu(new RecipeBookMenuViewer(event.getPlayer()));
          break;
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    event.setCancelled(true);
  }

  private boolean isInPvp(Location location) {
    return location.getY() <= 65;
  }

}
