package gg.eris.uhc.core.lobby;

import com.google.common.collect.Maps;
import gg.eris.commons.core.util.Validate;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import gg.eris.uhc.core.lobby.region.type.SpawnLobbyRegion;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class Lobby {

  protected final UhcPlugin plugin;
  @Getter
  private boolean enabled;

  @Getter
  private Location spawn;

  private final Int2ObjectAVLTreeMap<LobbyRegion> regions;
  private final Map<UUID, LobbyRegion> playerRegions;

  private int lobbyTask;

  public Lobby(UhcPlugin plugin, Location spawn) {
    this.plugin = plugin;
    this.regions = new Int2ObjectAVLTreeMap<>(Collections.reverseOrder());
    this.playerRegions = Maps.newHashMap();
    this.spawn = spawn;
    this.regions.put(0, new SpawnLobbyRegion(plugin, this));
  }

  public synchronized final void enable() {
    Validate.isTrue(!this.enabled, "lobby already enabled");
    this.enabled = true;
    for (LobbyRegion region : this.regions.values()) {
      region.enable();
    }


    this.lobbyTask = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
      for (Player player : Bukkit.getOnlinePlayers()) {
        LobbyRegion region = null;
        for (Entry<Integer, LobbyRegion> regionEntry : this.regions.entrySet()) {
          if (regionEntry.getValue().isInRegion(player.getLocation())) {
            region = regionEntry.getValue();
          }
        }

        if (this.playerRegions.get(player.getUniqueId()) == region) {
          continue;
        }

        LobbyRegion oldRegion = this.playerRegions.get(player.getUniqueId());
        if (oldRegion != null) {
          oldRegion.onLeave(player);
        }

        if (region != null) {
          this.playerRegions.put(player.getUniqueId(), region);
          region.onEnter(player);
        } else {
          this.playerRegions.remove(player.getUniqueId());
        }
      }
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

  public final void addRegion(int priority, LobbyRegion region) {
    Validate.isTrue(priority >= 0, "priority must be >= 0");
    Validate.isTrue(!this.regions.containsKey(priority));
    this.regions.put(priority, region);
  }

}
