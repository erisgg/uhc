package gg.eris.uhc.core.game;

import com.google.common.collect.Maps;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.event.uhc.UhcEndEvent;
import gg.eris.uhc.core.event.uhc.UhcStartEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class UhcGame<T extends UhcPlayer> {

  protected final UhcPlugin plugin;
  private final UhcModule<?> module;
  private final Map<UUID, T> players;

  @Getter(AccessLevel.PROTECTED)
  private final World world;

  @Getter
  private GameState gameState;

  protected final UhcGameSettings settings;

  public UhcGame(UhcPlugin plugin, UhcModule<?> module, UhcGameSettings settings) {
    this.plugin = plugin;
    this.module = module;
    this.players = Maps.newHashMap();
    this.settings = settings;
    this.world = Bukkit.getWorld(settings.getWorldName());
    this.gameState = GameState.WAITING;
  }

  public abstract void onWorldSetup(World world);

  public final void setupWorld() {
    this.world.setAutoSave(false);
    this.onWorldSetup(this.world);
  }

  public void setup() {
    setupWorld();
  }

  public synchronized final void start() {
    if (!this.gameState.canStart()) {
      throw new IllegalArgumentException("Cannot start game with gameState=" + this.gameState);
    }

    UhcStartEvent startEvent = new UhcStartEvent(this);
    Bukkit.getPluginManager().callEvent(startEvent);
    this.gameState = GameState.STARTING;

    Scatterer scatterer = new Scatterer(
        this.plugin,
        this,
        10
    );
    scatterer.scatter();
  }

  public synchronized final void end() {
    if (!this.gameState.canEnd()) {
      throw new IllegalArgumentException("Cannot end game with gameState=" + this.gameState);
    }

    UhcEndEvent endEvent = new UhcEndEvent(this);
    Bukkit.getPluginManager().callEvent(endEvent);
    this.gameState = GameState.ENDED;
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

  public Collection<T> getPlayers() {
    return List.copyOf(this.players.values());
  }

  public final UhcGameSettings getSettings() {
    return this.settings;
  }

}
