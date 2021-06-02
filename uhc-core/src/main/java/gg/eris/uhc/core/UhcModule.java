package gg.eris.uhc.core;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.stat.UhcPlayerStatRegistry;
import gg.eris.uhc.customcraft.CustomCraftUhcModule;
import gg.eris.uhc.scenario.ScenarioUhcModule;

public abstract class UhcModule<T extends UhcGame<?>> {

  public enum Type {
    CUSTOM_CRAFT(CustomCraftUhcModule.class),
    SCENARIO(ScenarioUhcModule.class);

    private final Class<? extends UhcModule<?>> clazz;

    Type(Class<? extends UhcModule<?>> clazz) {
      this.clazz = clazz;
    }

    protected Class<? extends UhcModule<?>> getModuleClass() {
      return this.clazz;
    }
  }

  protected final UhcPlugin plugin;
  protected final T game;

  public UhcModule(UhcPlugin plugin) {
    this.plugin = plugin;
    this.game = createGame();
  }

  protected void enable() {
    this.game.setupWorld();
    onEnable();
  }

  protected void disable() {
    onDisable();
  }

  protected abstract void onEnable();
  protected abstract void onDisable();
  protected abstract T createGame();
  protected abstract UhcPlayerStatRegistry createStatRegistry();

}
