package gg.eris.uhc.core.lobby;

import com.google.common.collect.Maps;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.region.LobbyRegion;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Lobby {

  protected final UhcPlugin plugin;
  @Getter
  private boolean enabled;

  private final Int2ObjectAVLTreeMap<LobbyRegion> regions;
  private final Map<UUID, LobbyRegion> playerRegions;

  private int lobbyTask;

  public Lobby(UhcPlugin plugin) {
    this.plugin = plugin;
    this.regions = new Int2ObjectAVLTreeMap<>(Collections.reverseOrder());
    this.playerRegions = Maps.newHashMap();
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
    Validate.isTrue(!this.regions.containsKey(priority));
    this.regions.put(priority, region);
  }

}
