package gg.eris.uhc.core.game;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.core.game.state.listener.MultiStateListenerManager;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
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
import org.bukkit.WorldBorder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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

  @Getter
  private final World world;

  @Getter
  private final World nether;

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
    this.settings = settings;
    this.world = Bukkit.getWorld(this.settings.getWorldName());
    this.nether = Bukkit.getWorld(this.settings.getNetherName());
    this.gameStateFactory = newStateFactory();
    this.updatingState = null;
    this.gameState = null;
  }

  public abstract UhcGameStateFactory<?, ?> newStateFactory();

  public abstract ErisPlayerSerializer<T> getErisPlayerSerializer();

  protected abstract Collection<MultiStateListener> getMultiStateListeners();

  public final void setup() {
    setupWorld();
    this.updatingState = this.gameStateFactory.initialState().get(); // Ticker sets the state
  }

  public final void start() {
    UhcGameTicker ticker = new UhcGameTicker(this);
    ticker.runTaskTimer(this.plugin, 0L, 1L);
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

    // Setting borders for world and nether
    WorldBorder border = this.world.getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(this.settings.getBorderRadius() * 2);
    border = this.nether.getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(this.settings.getBorderRadius() * 2);
  }

  public final void setGameState(GameState.Type type) {
    Validate.isNull(this.updatingState, "state is already being updated");
    if (this.gameState != null) {
      Validate.isTrue(type != this.gameState.getType(), "state set to its own type");
    }
    this.updatingState = this.gameStateFactory.getNewState(type);
  }

  public final void killPlayer(T player, T killer) {
    player.setAlive(false);

    // Handling effects and stat tracking
    if (killer != null) {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "<h>{0}</h> has been killed by <h>{1}</h>.",
          player.getName(),
          killer.getName()
      );

      killer.incrementKills();
      killer.getHandle().playSound(killer.getHandle().getLocation(), Sound.ORB_PICKUP, 1, 1);
    } else {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "<h>{0}</h> has died.",
          player.getName()
      );
    }

    player.getHandle().getLocation().getWorld().strikeLightningEffect(player.getHandle().getLocation()).getLocation();

    player.getHandle().sendTitle(new Title(
        CC.RED.bold() + "YOU DIED!",
        killer != null ? CC.GRAY + "You were killed by " + CC.RED + killer.getName() : null,
        20,
        20,
        20
    ));

    // Hiding the player
    player.getHandle().setGameMode(GameMode.CREATIVE);
    for (Player other : Bukkit.getOnlinePlayers()) {
      if (player.getHandle() != other) {
        other.hidePlayer(player.getHandle());
      }
    }

    this.players.remove(player.getUniqueId());

    checkGameEnd();
  }

  public final T getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  public final T getPlayer(UUID uuid) {
    return this.players.get(uuid);
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
      }

      if (this.game.getGameState() != null) {
        this.game.gameState.tick();
      } else {
        throw new IllegalStateException("Cannot tick null game state [tick=" + this.tick + "]");
      }
    }
  }

}
