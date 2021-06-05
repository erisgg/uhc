package gg.eris.uhc.core.lobby;

import gg.eris.uhc.core.UhcPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class Lobby implements Listener {

  protected final UhcPlugin plugin;

  public Lobby(UhcPlugin plugin) {
    this.plugin = plugin;
  }

  public final void register() {
    Bukkit.getPluginManager().registerEvents(this, this.plugin);
  }

  public final void unregister() {
    HandlerList.unregisterAll(this);
  }

}
