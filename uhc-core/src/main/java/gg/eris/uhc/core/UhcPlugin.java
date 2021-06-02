package gg.eris.uhc.core;

import gg.eris.uhc.core.UhcModule.Type;
import gg.eris.uhc.core.game.UhcGame;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class UhcPlugin extends JavaPlugin {

  private static final UhcModule.Type DEFAULT_TYPE = Type.CUSTOM_CRAFT;

  private UhcModule<?> uhc;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    FileConfiguration config = getConfig();
    try {
      this.uhc = createUhcModule(config.getString("uhc-type", DEFAULT_TYPE.name()));
    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException err) {
      err.printStackTrace();
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }

    Bukkit.getScheduler().runTask(this, () -> this.uhc.enable());
  }

  @Override
  public void onDisable() {
    this.uhc.disable();
  }


  private <T extends UhcGame<?>> UhcModule<T> createUhcModule(String typeName)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    UhcModule.Type type = UhcModule.Type.valueOf(typeName.toUpperCase(Locale.ROOT));
    return createUhcModule(type);
  }

  private <T extends UhcGame<?>> UhcModule<T> createUhcModule(UhcModule.Type type)
      throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    return (UhcModule<T>) type.getModuleClass().getConstructor().newInstance();
  }

}
