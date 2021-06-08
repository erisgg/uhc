package gg.eris.uhc.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class UhcPlugin extends JavaPlugin {

  private UhcModule<?> uhc;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    this.uhc = createUhcModule();
    Bukkit.getScheduler().runTask(this, () -> this.uhc.enable());
  }

  @Override
  public void onDisable() {
    this.uhc.disable();
  }

  protected abstract UhcModule<?> createUhcModule();

}
