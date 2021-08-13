package gg.eris.uhc.core;

import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.uhc.core.game.UhcGame;
import java.util.Set;
import lombok.Getter;

public abstract class UhcModule<T extends UhcGame<?>> {

  protected final UhcPlugin plugin;
  @Getter
  protected final T game;

  public UhcModule(UhcPlugin plugin) {
    this.plugin = plugin;
    this.game = createGame();
    plugin.getCommons().getErisPlayerManager()
        .setPlayerSerializer(this.game.getErisPlayerSerializer());
  }

  protected final void enable() {
    onEnable();
    this.game.setup();
    this.game.start();
  }

  protected final void disable() {
    onDisable();
  }

  protected abstract void onEnable();

  protected abstract void onDisable();

  protected abstract T createGame();

  protected abstract Set<CommandProvider> getCommands();

}
