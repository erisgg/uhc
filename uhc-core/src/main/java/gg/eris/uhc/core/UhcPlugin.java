package gg.eris.uhc.core;

import gg.eris.commons.bukkit.ErisBukkitCommons;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class UhcPlugin extends JavaPlugin {

  private ErisBukkitCommons commons;
  private UhcModule<?> uhc;

  @Override
  public void onEnable() {
    saveDefaultConfig();
    try {
      this.uhc = createUhcModule();
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException err) {
      err.printStackTrace();
      Bukkit.getPluginManager().disablePlugin(this);
    }
    this.commons = Bukkit.getServicesManager().getRegistration(ErisBukkitCommons.class)
        .getProvider();
    Bukkit.getScheduler().runTask(this, () -> this.uhc.enable());
  }

  @Override
  public void onDisable() {
    this.uhc.disable();
  }

  protected UhcModule<?> createUhcModule()
      throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    return (UhcModule<?>) Class.forName(getConfig().getString(
        "uhc-module-path",
        "gg.eris.uhc.customcraft.CustomCraftUhcModule"
    )).getConstructor(UhcPlugin.class).newInstance(this);
  }

  public ErisBukkitCommons getCommons() {
    return this.commons;
  }

}
