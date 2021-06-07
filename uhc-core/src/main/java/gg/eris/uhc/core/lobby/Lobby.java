package gg.eris.uhc.core.lobby;

import com.google.common.collect.Sets;
import gg.eris.uhc.core.UhcPlugin;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class Lobby {

  protected final UhcPlugin plugin;
  @Getter
  private boolean enabled;

  private final Set<LobbyRegion> regions;

  private Lobby(UhcPlugin plugin) {
    this.plugin = plugin;
    this.regions = Sets.newHashSet();
  }

  public synchronized final void enable() {
    Validate.isTrue(!this.enabled, "lobby already enabled");
    this.enabled = true;
    for (LobbyRegion region : this.regions) {
      region.enable();
    }
  }

  public final void disable() {
    Validate.isTrue(this.enabled, "lobby is already disabled");
    this.enabled = false;
    for (LobbyRegion region : this.regions) {
      region.disable();
    }
  }

  public final void addRegion(LobbyRegion region) {
    this.regions.add(region);
  }

}
