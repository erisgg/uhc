package gg.eris.uhc.core;

import gg.eris.uhc.customcraft.CustomCraftUhcModule;
import gg.eris.uhc.scenario.ScenarioUhcModule;

public abstract class UhcModule {

  public enum Type {
    CUSTOM_CRAFT(CustomCraftUhcModule.class),
    SCENARIO(ScenarioUhcModule.class);

    private final Class<? extends UhcModule> clazz;

    Type(Class<? extends UhcModule> clazz) {
      this.clazz = clazz;
    }

    protected Class<? extends UhcModule> getModuleClass() {
      return this.clazz;
    }
  }

  protected final UhcPlugin plugin;

  public UhcModule(UhcPlugin plugin) {
    this.plugin = plugin;
  }

  public abstract void onEnable();
}
