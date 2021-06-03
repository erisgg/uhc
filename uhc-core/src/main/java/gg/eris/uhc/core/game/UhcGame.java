package gg.eris.uhc.core.game;

import com.google.common.collect.Maps;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.event.UhcTickEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class UhcGame<T extends UhcPlayer> {

  protected final UhcPlugin plugin;
  private final UhcModule<?> module;
  private final Map<UUID, T> players;
  protected final UhcGameSettings settings;
  private final UhcGameStateFactory<?, ?> gameStateFactory;

  @Getter(AccessLevel.PROTECTED)
  private final World world;

  @Getter
  private GameState<?, ?> gameState;

  public UhcGame(UhcPlugin plugin, UhcModule<?> module, UhcGameSettings settings) {
    this.plugin = plugin;
    this.module = module;
    this.players = Maps.newHashMap();
    this.settings = settings;
    this.world = Bukkit.getWorld(settings.getWorldName());
    this.gameStateFactory = newStateFactory();
  }

  public abstract void onWorldSetup(World world);

  public final void setupWorld() {
    this.world.setAutoSave(false);
    this.onWorldSetup(this.world);
  }

  public void setup() {
    setupWorld();
    this.gameState = this.gameStateFactory.initialState().get();
  }

  public final void addPlayer(T player) {
    this.players.put(player.getUniqueId(), player);
  }

  public final T removePlayer(Player player) {
    return removePlayer(player.getUniqueId());
  }

  public final T removePlayer(UUID uuid) {
    return this.players.remove(uuid);
  }

  public final T getPlayer(Player player) {
    return getPlayer(player.getUniqueId());
  }

  public final T getPlayer(UUID uuid) {
    return this.players.get(uuid);
  }

  public final Collection<T> getPlayers() {
    return List.copyOf(this.players.values());
  }

  public final UhcGameSettings getSettings() {
    return this.settings;
  }

  public abstract UhcGameStateFactory<?, ?> newStateFactory();

  @RequiredArgsConstructor
  private static class UhcGameTicker extends BukkitRunnable {

    private final UhcGame<?> game;
    private int tick;

    @Override
    public void run() {
      this.tick++;

      if (this.game.getGameState() != null) {
        this.game.getGameState().tick();
      } else {
        throw new IllegalStateException("null gamestate tick=" + this.tick);
      }

      UhcTickEvent tickEvent = new UhcTickEvent(this.game, this.tick);
      Bukkit.getPluginManager().callEvent(tickEvent);
    }

    public static void start(UhcGame<?> game) {
      UhcGameTicker ticker = new UhcGameTicker(game);
      ticker.runTaskTimer(game.plugin, 0L, 1L);
    }

  }

}
