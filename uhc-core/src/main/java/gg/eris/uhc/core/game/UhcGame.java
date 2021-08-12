package gg.eris.uhc.core.game;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.event.UhcChangeStateEvent;
import gg.eris.uhc.core.event.UhcPlayerDeathEvent;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.core.game.state.listener.MultiStateListenerManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

public abstract class UhcGame<T extends UhcPlayer> {

  @Getter
  protected final UhcPlugin plugin;
  @Getter
  private final UhcModule<?> module;

  protected final MultiStateListenerManager multiStateListenerManager;
  protected final UhcGameSettings settings;

  private final Map<UUID, T> players;
  private final UhcGameStateFactory<?, ?> gameStateFactory;

  private final UhcGameTicker ticker;

  @Getter
  private final World world;

  @Getter
  private final World nether;

  @Getter
  private final World deathmatch;

  @Getter
  private GameState<?, ?> gameState;

  private GameState<?, ?> updatingState;

  @Getter
  private T winner;

  public UhcGame(UhcPlugin plugin, UhcModule<?> module, UhcGameSettings settings) {
    this.plugin = plugin;
    this.module = module;
    this.players = Maps.newHashMap();
    this.multiStateListenerManager = new MultiStateListenerManager(plugin);
    for (MultiStateListener multiStateListener : getMultiStateListeners()) {
      this.multiStateListenerManager.addListener(multiStateListener);
    }
    this.gameStateFactory = newStateFactory();
    this.ticker = new UhcGameTicker(this);
    this.settings = settings;
    this.world = Bukkit.getWorld(this.settings.getWorldName());
    this.nether = Bukkit.getWorld(this.settings.getNetherName());
    this.deathmatch = Bukkit.getWorld(this.settings.getDeathmatchName());
    this.updatingState = null;
    this.gameState = null;
  }

  public abstract UhcGameStateFactory<?, ?> newStateFactory();

  public abstract ErisPlayerSerializer<T> getErisPlayerSerializer();

  protected abstract Collection<MultiStateListener> getMultiStateListeners();

  /**
   * Filter drops on player death
   *
   * @param drops are the drops
   */
  protected abstract void filterDrops(List<ItemStack> drops);

  public final void setup() {
    setupWorld();
    this.updatingState = this.gameStateFactory.initialState().get(); // Ticker sets the state
  }

  public final void start() {

    this.ticker.runTaskTimer(this.plugin, 0L, 1L);
  }

  public final void setupWorld() {
    for (World world : Bukkit.getWorlds()) {
      world.setAutoSave(false);
      world.setGameRuleValue("naturalRegeneration", "false");
      world.setGameRuleValue("randomTickSpeed", "3");
      world.setDifficulty(Difficulty.NORMAL);
      world.setWeatherDuration(0);
      for (Entity entity : world.getEntities()) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER) {
          entity.remove();
        }
      }
      Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
        world.setFullTime(6000L);
        world.setTime(6000L);
      }, 0L, 1L);
    }
  }

  public final void setGameState(GameState.Type type) {
    Validate.isNull(this.updatingState, "state is already being updated");
    if (this.gameState != null) {
      Validate.isTrue(type != this.gameState.getType(), "state set to its own type");
    }
    this.updatingState = this.gameStateFactory.getNewState(type);
  }

  public final void killPlayer(T killed, T killer) {
    Player killedHandle = killed.getHandle();

    killed.died();

    // Handling effects and stat tracking
    if (killer != null) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "<h>{0}</h> has been killed by <h>{1}</h>.",
          killed.getDisplayName(),
          killer.getDisplayName()
      );

      killer.incrementKills();
      killer.getHandle().playSound(killer.getHandle().getLocation(), Sound.ORB_PICKUP, 1, 1);
    } else {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "<h>{0}</h> has died.",
          killed.getDisplayName()
      );
    }

    killedHandle.getLocation().getWorld().strikeLightningEffect(killed.getHandle().getLocation())
        .getLocation();

    killedHandle.sendTitle(new Title(
        CC.RED.bold() + "YOU DIED!",
        killer != null ? CC.GRAY + "You were killed by " + CC.RED + killer.getDisplayName() + "." : null,
        20,
        20,
        20
    ));

    // Hiding the player
    killedHandle.setGameMode(GameMode.CREATIVE);
    for (Player other : Bukkit.getOnlinePlayers()) {
      if (killedHandle != other) {
        other.hidePlayer(killed.getHandle());
      }
    }

    List<ItemStack> drops = new ArrayList<>(
        Arrays.asList(killedHandle.getInventory().getContents()));
    drops.addAll(Arrays.asList(killedHandle.getInventory().getArmorContents()));
    drops.removeIf(StackUtil::isNullOrAir);
    filterDrops(drops);

    UhcPlayerDeathEvent uhcPlayerDeathEvent = new UhcPlayerDeathEvent(this, killed, killer, drops);
    Bukkit.getPluginManager().callEvent(uhcPlayerDeathEvent);

    StackUtil.dropItems(killedHandle.getLocation(), drops, true);

    removePlayer(killed);
    checkGameEnd();
  }

  public final void removePlayer(T player) {
    this.players.remove(player.getUniqueId());
  }

  public final T getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  public final T getPlayer(UUID uuid) {
    return this.players.get(uuid);
  }

  public final boolean isPlayer(Player player) {
    return isPlayer(player.getUniqueId());
  }

  public final boolean isPlayer(UUID uuid) {
    return this.players.containsKey(uuid);
  }

  public final void setPlayers() {
    Validate.isTrue(this.players.isEmpty(), "players have already been set");
    this.plugin.getCommons().getErisPlayerManager().getPlayers()
        .forEach(player -> this.players.put(player.getUniqueId(), (T) player));
  }

  public final Collection<T> getPlayers() {
    return List.copyOf(this.players.values());
  }

  private void checkGameEnd() {
    T winner = calculateWinnerInternal();

    if (winner != null) {
      this.winner = winner;
      setGameState(TypeRegistry.ENDED);
    }
  }

  public final UhcGameSettings getSettings() {
    return this.settings;
  }

  public final int getTick() {
    return this.ticker.tick;
  }

  public final void shutdown() {
    for (Player player : Bukkit.getOnlinePlayers()) {
      player.kickPlayer("UHC game has ended");
    }

    Bukkit.getServer().shutdown();
  }

  private T calculateWinnerInternal() {
    if (this.players.size() == 1) {
      return this.getPlayers().stream().findFirst().orElse(null);
    } else {
      T winner = null;
      int count = 0;
      for (T player : this.getPlayers()) {
        if (player.isOnline()) {
          count++;
          if (count > 1) {
            return null;
          } else {
            winner = player;
          }
        }
      }

      return winner;
    }
  }

  @RequiredArgsConstructor
  private static class UhcGameTicker extends BukkitRunnable {

    private final UhcGame<?> game;
    private int tick;

    @Override
    public void run() {
      this.tick++;

      UhcTickEvent tickEvent = new UhcTickEvent(this.game, this.tick);
      Bukkit.getPluginManager().callEvent(tickEvent);

      if (tickEvent.isCancelled()) {
        return;
      }

      if (this.game.updatingState != null) {
        if (this.game.gameState != null) { // Checking for initial start
          this.game.gameState.end();
        }

        this.game.gameState = this.game.updatingState;
        this.game.gameState.start();
        this.game.multiStateListenerManager.onStateStart(this.game.gameState);
        this.game.updatingState = null;

        UhcChangeStateEvent stateEvent = new UhcChangeStateEvent(this.game, this.game.gameState);
        Bukkit.getPluginManager().callEvent(stateEvent);
      }

      if (this.game.getGameState() != null) {
        this.game.gameState.tick();
      } else {
        throw new IllegalStateException("Cannot tick null game state [tick=" + this.tick + "]");
      }
    }
  }

}
