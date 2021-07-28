package gg.eris.uhc.core.game;

import com.google.common.collect.Maps;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.core.game.state.listener.MultiStateListenerManager;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class UhcGame<T extends UhcPlayer> {

  @Getter
  protected final UhcPlugin plugin;
  @Getter
  private final UhcModule<?> module;

  protected final MultiStateListenerManager multiStateListenerManager;
  protected final UhcGameSettings settings;

  private final Map<UUID, T> players;
  private final UhcGameStateFactory<?, ?> gameStateFactory;

  @Getter(AccessLevel.PROTECTED)
  private final World world;

  @Getter
  private GameState<?, ?> gameState;

  private GameState<?, ?> updatingState;

  public UhcGame(UhcPlugin plugin, UhcModule<?> module, UhcGameSettings settings) {
    this.plugin = plugin;
    this.module = module;
    this.players = Maps.newHashMap();
    this.multiStateListenerManager = new MultiStateListenerManager();

    for (MultiStateListener multiStateListener : getMultiStateListeners()) {
      this.multiStateListenerManager.addListener(multiStateListener);
    }

    this.settings = settings;
    this.world = Bukkit.getWorld(this.settings.getWorldName());
    this.gameStateFactory = newStateFactory();
    this.updatingState = null;
  }

  public final void start() {
    UhcGameTicker ticker = new UhcGameTicker(this);
    ticker.runTaskTimer(this.plugin, 0L, 1L);
  }

  public final void setup() {
    setupWorld();
    this.gameState = this.gameStateFactory.initialState().get();
  }

  public final void setupWorld() {
    this.world.setAutoSave(false);
    WorldBorder border = this.world.getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(this.settings.getBorderRadius());
    this.onWorldSetup(this.world);
  }

  public final void setGameState(GameState.Type type) {
    Validate.isNull(this.updatingState, "state is already being updated");
    this.updatingState = this.gameStateFactory.getNewState(type);
  }

  public final void removePlayer(Player player) {
    this.players.remove(player.getUniqueId());
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

  public final UhcGameSettings getSettings() {
    return this.settings;
  }

  public abstract UhcGameStateFactory<?, ?> newStateFactory();

  public abstract void onWorldSetup(World world);

  protected abstract Collection<MultiStateListener> getMultiStateListeners();

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
        this.game.gameState.end();
        HandlerList.unregisterAll(this.game.gameState);
        this.game.gameState = this.game.updatingState;
        Bukkit.getPluginManager().registerEvents(this.game.gameState, this.game.plugin);
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
