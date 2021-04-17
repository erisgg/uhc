package gg.eris.uhc.core;

public abstract class UhcModule {

  public enum Type {
    CUSTOM_CRAFT(null),
    REDDIT(null);

    @
    private final Class<? extends UhcModule> clazz;

    Type(Class<? extends UhcModule> clazz) {
      this.clazz = clazz;
    }
  }

  protected final UhcPlugin plugin;

  public UhcModule(UhcPlugin plugin) {
    this.plugin = plugin;
  }
}
