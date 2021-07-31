package gg.eris.uhc.customcraft.game;

import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.game.listener.GameListener;
import gg.eris.uhc.customcraft.game.listener.GlobalListener;
import gg.eris.uhc.customcraft.game.listener.LobbyListener;
import gg.eris.uhc.customcraft.game.listener.PvpListener;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayerSerializer;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import java.util.Collection;
import java.util.List;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {

  private static final Int2IntMap COIN_MAP;

  static {
    COIN_MAP = new Int2IntArrayMap();
    COIN_MAP.put(10, 50);
    COIN_MAP.put(1, 20);
  }

  public CustomCraftUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder()
        .worldName(CustomCraftUhcIdentifiers.GAME_WORLD)
        .netherName(CustomCraftUhcIdentifiers.GAME_NETHER)
        .deathmatchName(CustomCraftUhcIdentifiers.DEATHMATCH_WORLD)
        .borderRadius(1000)
        .maxHealth(40)
        .requiredPlayers(2)
        .pregameCountdownDuration(30)
        .gracePeriodDuration(30)
        .pvpPeriodDuration(60)
        .borderShrunkRadius(400)
        .borderShrinkDelay(10)
        .borderShrinkDuration(30)
        .preDeathmatchCountdownDuration(60)
        .attackCreditDuration(20)
        .deathmatchPlayerThreshold(12)
        .deathmatchBorderRadius(300)
        .deathmatchBorderShrinkDelay(30)
        .deathmatchBorderShrinkDuration(300)
        .deathmatchBorderShrunkRadius(40)
        .deathmatchBlockDecayDelay(30)
        .deathmatchStartCountdownDuration(5)
        .postGameDelay(30)
        .coinsPerKill(100)
        .coinsPerWin(500)
        .coinsPerSurvive(Int2IntMaps.unmodifiable(COIN_MAP))
        .build()
    );
  }


  /*
  Boring overrides
   */
  @Override
  public ErisPlayerSerializer<CustomCraftUhcPlayer> getErisPlayerSerializer() {
    return new CustomCraftUhcPlayerSerializer();
  }

  @Override
  public UhcGameStateFactory<?, ?> newStateFactory() {
    return new CustomCraftUhcGameStateFactory(this);
  }

  @Override
  protected Collection<MultiStateListener> getMultiStateListeners() {
    return List.of(
        new GlobalListener(this),
        new LobbyListener(this),
        new GameListener(this),
        new PvpListener()
    );
  }
}
