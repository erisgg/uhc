package gg.eris.uhc.core;

import gg.eris.commons.bukkit.ErisBukkitCommons;
import org.bukkit.Bukkit;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class UhcPlugin extends JavaPlugin {

  private ErisBukkitCommons commons;
  private UhcModule<?> uhc;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    this.uhc = createUhcModule();
    this.commons = Bukkit.getServicesManager().getRegistration(ErisBukkitCommons.class).getProvider();
    Bukkit.getScheduler().runTask(this, () -> this.uhc.enable());
  }

  @Override
  public void onDisable() {
    this.uhc.disable();
  }

  protected abstract UhcModule<?> createUhcModule();

  public ErisBukkitCommons getCommons() {
    return this.commons;
  }

}
