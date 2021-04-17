package gg.eris.uhc.core;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class UhcPlugin extends JavaPlugin {

  private UhcModule uhc;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    FileConfiguration config = getConfig();
    try {
      this.uhc = createUhcModule(config.getString("uhc-type", "custom_craft"));
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException err) {
      err.printStackTrace();
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }


    Bukkit.getScheduler().runTask(this, () -> this.uhc.onEnable());
  }

  private UhcModule createUhcModule(String typeName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    UhcModule.Type type = UhcModule.Type.valueOf(typeName.toUpperCase(Locale.ROOT));
    return type.getModuleClass().getConstructor().newInstance();
  }

}
