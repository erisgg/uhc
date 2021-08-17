package gg.eris.uhc.customcraft.game.listener;

import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.core.util.LobbyUtil;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public final class WaitingCountdownListener extends MultiStateListener {

  // Hotbar items
  private final static ItemStack MAIN_MENU = new ItemBuilder(Material.EMERALD)
      .withName(CC.GOLD.bold() + "Main Menu" + CC.DARK_GRAY + " (Right Click)").build();

  private final static ItemStack RECIPE_BOOK = new ItemBuilder(Material.ENCHANTED_BOOK)
      .withName(CC.GREEN.bold() + "Recipe Book" + CC.DARK_GRAY + " (Right Click)").build();

  private static final Location SPAWN = new Location(
      new WorldCreator(CustomCraftUhcIdentifiers.PREGAME_WORLD).createWorld(),
      0.5,
      73,
      0.5,
      -90.0f,
      0.0f
  );

  private final CustomCraftUhcGame game;
  private final ErisPlayerManager erisPlayerManager;

  @Override
  protected Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.WAITING,
        TypeRegistry.COUNTDOWN
    );
  }

  public WaitingCountdownListener(CustomCraftUhcGame game) {
    this.game = game;
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    event.setJoinMessage(null);

    if (Bukkit.getOnlinePlayers().size() > this.game.getSettings().getMaxPlayers()) {
      player.kickPlayer(CC.YELLOW.bold() + "(!) " + CC.GOLD + "The game is full!");
      return;
    }

    ErisPlayer erisPlayer = this.erisPlayerManager.getPlayer(player);
    erisPlayer.addLoadingConsumer(joined -> LobbyUtil.broadcastJoin(joined,
        WaitingCountdownListener.this.erisPlayerManager.getPlayers().size()));

    Bukkit.getScheduler().runTaskLater(this.game.getPlugin(), () -> {
      sendToSpawn(event.getPlayer());
    }, 1L);
  }

  public static void sendToSpawn(Player handle) {
    handle.setMaxHealth(20);
    handle.setHealth(20);
    handle.getInventory().clear();
    handle.getInventory().setArmorContents(new ItemStack[4]);
    Bukkit.getScheduler().runTaskLater(UhcPlugin.getPlugin(), () -> {
      handle.getInventory().setItem(0, RECIPE_BOOK);
      handle.getInventory().setItem(4, MAIN_MENU);
      handle.getInventory().setHeldItemSlot(4);
    }, 3L);
    Bukkit.getScheduler()
        .runTaskLater(UhcPlugin.getPlugin(), () -> handle.teleport(SPAWN), 2L);
  }
}
