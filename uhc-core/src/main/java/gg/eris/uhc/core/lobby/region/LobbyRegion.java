package gg.eris.uhc.core.lobby.region;

import com.google.common.collect.Sets;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.lobby.Lobby;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.hanging.HangingEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;

public abstract class LobbyRegion {

  private final UhcPlugin plugin;
  private final Lobby lobby;

  private final Set<RegionListener<?>> regionListeners;

  private final Set<UUID> inRegion;

  public LobbyRegion(UhcPlugin plugin, Lobby lobby) {
    this.plugin = plugin;
    this.lobby = lobby;
    this.regionListeners = Sets.newHashSet();
    this.inRegion = Sets.newHashSet();
  }

  public abstract void onEnter(Player player);

  public abstract void onLeave(Player player);

  public abstract boolean isInRegion(Location location);

  public final void enable() {
    for (RegionListener<?> regionListener : this.regionListeners) {
      Bukkit.getPluginManager().registerEvent(
          regionListener.eventClass,
          regionListener,
          EventPriority.NORMAL,
          regionListener,
          plugin
      );
    }
  }

  public final void disable() {
    for (RegionListener<?> regionListener : this.regionListeners) {
      HandlerList.unregisterAll(regionListener);
    }
  }

  public final void updateInRegion(Player player) {
    boolean inRegion = isInRegion(player.getLocation());
    if (inRegion) {
      this.inRegion.add(player.getUniqueId());
    } else {
      this.inRegion.remove(player.getUniqueId());
    }
  }

  protected final <T extends PlayerEvent> void registerPlayerEvent(Class<T> eventClass,
      Consumer<T> callback) {
    RegionListener<T> regionListener = new RegionListener<>(
        this.lobby,
        eventClass,
        event -> {
          if (this.inRegion.contains(event.getPlayer().getUniqueId())) {
            callback.accept(event);
          }
        }
    );

    registerRegionListener(regionListener);
  }

  protected final <T extends EntityEvent> void registerEntityEvent(Class<T> eventClass,
      Consumer<T> callback) {
    RegionListener<T> regionListener = new RegionListener<>(
        this.lobby,
        eventClass,
        event -> {
          Entity entity = event.getEntity();
          if (this.lobby.getRegion(entity.getUniqueId()) == this) {
            callback.accept(event);
          }
        }
    );

    registerRegionListener(regionListener);
  }


  protected final <T extends BlockEvent> void registerBlockEvent(Class<T> eventClass,
      Consumer<T> callback) {
    RegionListener<T> regionListener = new RegionListener<>(
        this.lobby,
        eventClass,
        event -> {
          if (this.isInRegion(event.getBlock().getLocation())) {
            callback.accept(event);
          }
        }
    );

    registerRegionListener(regionListener);
  }

  protected final <T extends HangingEvent> void registerHangingEvent(Class<T> eventClass,
      Consumer<T> callback) {
    RegionListener<T> regionListener = new RegionListener<>(
        this.lobby,
        eventClass,
        event -> {
          if (this.isInRegion(event.getEntity().getLocation())) {
            callback.accept(event);
          }
        }
    );

    registerRegionListener(regionListener);
  }

  /* NOTE: Does not make any check for applicability of the event to the region.
  This must be done in the consumer by the implementation. */
  protected final <T extends Event> void registerChecklessEvent(Class<T> eventClass,
      Consumer<T> callback) {
    RegionListener<T> regionListener = new RegionListener<>(
        this.lobby,
        eventClass,
        callback
    );
    registerRegionListener(regionListener);
  }

  private <T extends Event> void registerRegionListener(RegionListener<T> regionListener) {
    this.regionListeners.add(regionListener);
  }

  @RequiredArgsConstructor
  private static final class RegionListener<T extends Event> implements EventExecutor,
      Listener {

    private final Lobby lobby;
    private final Class<T> eventClass;
    private final Consumer<T> consumer;

    @Override
    public void execute(Listener listener, Event event) {
      if (this.lobby.isEnabled() && eventClass.isInstance(event)) {
        consumer.accept(eventClass.cast(event));
      }
    }

  }

}
