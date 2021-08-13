package gg.eris.uhc.core;

import gg.eris.commons.bukkit.ErisBukkitCommons;
import gg.eris.commons.bukkit.command.CommandProvider;
import java.lang.reflect.InvocationTargetException;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class UhcPlugin extends JavaPlugin {

  private static UhcPlugin INSTANCE;

  private ErisBukkitCommons commons;
  @Getter
  private UhcModule<?> uhc;

  @Override
  public void onEnable() {
    INSTANCE = this;

    saveDefaultConfig();
    this.commons = Bukkit.getServicesManager()
        .getRegistration(ErisBukkitCommons.class).getProvider();

    try {
      this.uhc = createUhcModule();
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException err) {
      err.printStackTrace();
      Bukkit.getPluginManager().disablePlugin(this);
    }

    if (!Bukkit.getPluginManager().isPluginEnabled(this)) {
      return;
    }

    for (CommandProvider provider : this.uhc.getCommands()) {
      this.commons.getCommandManager().registerCommands(provider);
    }

    Bukkit.getScheduler().runTask(this, () -> this.uhc.enable());
  }

  @Override
  public void onDisable() {
    if (this.uhc != null) {
      this.uhc.disable();
    }
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

  @Deprecated
  public static UhcPlugin getPlugin() {
    return INSTANCE;
  }

}
