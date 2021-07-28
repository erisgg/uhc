package gg.eris.uhc.customcraft.game;

import gg.eris.commons.bukkit.ErisBukkitCommons;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.game.listener.LobbyListener;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.bukkit.World;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {

  @Getter
  private final ErisBukkitCommons commons;

  public CustomCraftUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder()
        .worldName("world")
        .borderRadius(1000)
        .shrunkBorderRadius(200)
        .deathmatchBorderRadius(100)
        .deathmatchBorderShrinkDelay(300)
        .deathmatchBorderShrinkTime(300)
        .deathmatchBorderShrunkRadius(10)
        .coinsPerKill(100)
        .coinsPerSurvive(10, 50)
        .coinsPerSurvive(5, 100)
        .coinsPerWin(500)
        .build());

    this.commons = plugin.getCommons();
  }

  @Override
  public void onWorldSetup(World world) {
  }

  @Override
  public UhcGameStateFactory<?, ?> newStateFactory() {
    return new CustomCraftUhcGameStateFactory(this);
  }

  @Override
  protected Collection<MultiStateListener> getMultiStateListeners() {
    return List.of(
        new LobbyListener(this, this.commons.getErisPlayerManager())
    );
  }
}
