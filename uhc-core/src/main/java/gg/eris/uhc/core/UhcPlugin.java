package gg.eris.uhc.core;

import java.util.Locale;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class UhcPlugin extends JavaPlugin {

  private UhcModule uhc;

  @Override
  public void onEnable() {
    saveDefaultConfig();

    FileConfiguration config = getConfig();
    this.uhc = getUhcModule(config.getString("uhc-type", ))

  }

  private UhcModule getUhcModule(String typeName) {
    UhcModule.Type type = UhcModule.Type.valueOf(typeName.toUpperCase(Locale.ROOT));

    return type
  }

}
