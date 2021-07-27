package gg.eris.uhc.core.lobby;

import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public abstract class Lobby implements Listener {

  protected final UhcPlugin plugin;
  @Getter
  private boolean enabled;

  @Getter
  private final Location spawn;

  private final Int2ObjectAVLTreeMap<LobbyRegion> regions;
  private final Map<UUID, LobbyRegion> playerRegions;

  private int lobbyTask;

  public Lobby(UhcPlugin plugin, Location spawn) {
    this.plugin = plugin;
    this.regions = new Int2ObjectAVLTreeMap<>(Collections.reverseOrder());
    this.playerRegions = Maps.newHashMap();
    this.spawn = spawn;
  }

  public synchronized final void enable() {
    Validate.isTrue(!this.enabled, "lobby already enabled");
    this.enabled = true;
    for (LobbyRegion region : this.regions.values()) {
      region.enable();
    }

    Bukkit.getPluginManager().registerEvent(
        PlayerJoinEvent.class,
        this,
        EventPriority.LOW,
        (listener, event) -> reset(((PlayerEvent) event).getPlayer()),
        this.plugin
    );

    Bukkit.getPluginManager().registerEvent(
        PlayerRespawnEvent.class,
        this,
        EventPriority.LOW,
        (listener, event) -> Bukkit.getScheduler()
            .runTask(this.plugin, () -> reset(((PlayerEvent) event).getPlayer())),
        this.plugin
    );

    Bukkit.getPluginManager().registerEvent(
        PlayerQuitEvent.class,
        this,
        EventPriority.LOW,
        (listener, event) -> {
          Player player = ((PlayerEvent) event).getPlayer();
          for (LobbyRegion region : this.regions.values()) {
            region.quit(player);
          }
        },
        this.plugin
    );

    this.lobbyTask = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
      for (Player player : Bukkit.getOnlinePlayers()) {
        checkRegion(player);
      }
      this.spawn.getWorld().setTime(6000L);
      this.spawn.getWorld().setFullTime(6000L);
    }, 1L, 1L).getTaskId();
  }

  public final void disable() {
    Validate.isTrue(this.enabled, "lobby is already disabled");
    this.enabled = false;
    for (LobbyRegion region : this.regions.values()) {
      region.disable();
    }
    Bukkit.getScheduler().cancelTask(this.lobbyTask);
  }

  public final LobbyRegion getRegion(UUID uuid) {
    return this.playerRegions.get(uuid);
  }

  public void reset(Player player) {
    PlayerUtil.resetPlayer(player);
    player.teleport(this.spawn);
    checkRegion(player);
  }

  public final void addRegion(int priority, LobbyRegion region) {
    Validate.isTrue(priority >= 0, "priority must be >= 0");
    Validate.isTrue(!this.regions.containsKey(priority));
    this.regions.put(priority, region);
  }

  private void checkRegion(Player player) {
    LobbyRegion region = null;
    for (Entry<Integer, LobbyRegion> regionEntry : this.regions.entrySet()) {
      if (regionEntry.getValue().isInRegion(player.getLocation())) {
        region = regionEntry.getValue();
      }
    }

    if (this.playerRegions.get(player.getUniqueId()) == region) {
      return;
    }

    LobbyRegion oldRegion = this.playerRegions.get(player.getUniqueId());
    if (oldRegion != null) {
      oldRegion.leave(player);
    }

    if (region != null) {
      this.playerRegions.put(player.getUniqueId(), region);
      region.enter(player);
    } else {
      this.playerRegions.remove(player.getUniqueId());
    }
  }

}
