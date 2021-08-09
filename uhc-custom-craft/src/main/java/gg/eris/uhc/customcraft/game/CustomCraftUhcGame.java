package gg.eris.uhc.customcraft.game;

import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagInventoryListener;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagListener;
import gg.eris.uhc.customcraft.craft.shop.skill.SkillShopMenu;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.game.listener.GlobalListener;
import gg.eris.uhc.customcraft.game.listener.LobbyListener;
import gg.eris.uhc.customcraft.game.listener.PvpListener;
import gg.eris.uhc.customcraft.game.listener.SpectatorListener;
import gg.eris.uhc.customcraft.game.listener.WaitingCountdownListener;
import gg.eris.uhc.customcraft.game.listener.game.BlockBreakListener;
import gg.eris.uhc.customcraft.game.listener.game.EntityDropsListener;
import gg.eris.uhc.customcraft.game.listener.game.GameDamageListener;
import gg.eris.uhc.customcraft.game.listener.game.ItemCombustionListener;
import gg.eris.uhc.customcraft.game.listener.game.MobBurnListener;
import gg.eris.uhc.customcraft.game.listener.game.MonsterSpawnListener;
import gg.eris.uhc.customcraft.game.listener.game.StrengthNerfListener;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayerSerializer;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Recipe;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {

  private static final Int2IntMap COIN_MAP;

  static {
    COIN_MAP = new Int2IntArrayMap();
    //COIN_MAP.put(10, 50);
  }

  @Getter
  private final SkillShopMenu shopMenu;

  public CustomCraftUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder()
        .worldName(CustomCraftUhcIdentifiers.GAME_WORLD)
        .netherName(CustomCraftUhcIdentifiers.GAME_NETHER)
        .deathmatchName(CustomCraftUhcIdentifiers.DEATHMATCH_WORLD)
        .borderRadius(1000)
        .maxHealth(40)
        .requiredPlayers(2)
        .pregameCountdownDuration(5)
        .gracePeriodDuration(30)
        .pvpPeriodDuration(60 * 30)
        .borderShrunkRadius(400)
        .borderShrinkDelay(1000)
        .borderShrinkDuration(30)
        .preDeathmatchCountdownDuration(60)
        .attackCreditDuration(20)
        .deathmatchPlayerThreshold(12)
        .deathmatchBorderRadius(200)
        .deathmatchBorderShrinkDelay(30)
        .deathmatchBorderShrinkDuration(300)
        .deathmatchBorderShrunkRadius(20)
        .deathmatchBlockDecayDelay(30)
        .deathmatchStartCountdownDuration(5)
        .postGameDelay(30)
        .coinsPerKill(100)
        .coinsPerWin(500)
        .coinsPerSurvive(Int2IntMaps.unmodifiable(COIN_MAP))
        .build()
    );

    // Registering custom crafts and trinkets
    for (Vocation vocation : Vocation.values()) {
      VocationRegistry vocationRegistry = vocation.getRegistry();
      if (vocationRegistry == null) {
        continue;
      }
      for (Recipe recipe : vocationRegistry.getRecipes()) {
        Bukkit.addRecipe(recipe);
      }
    }

    // Shop
    this.shopMenu = new SkillShopMenu(this.plugin);
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
        // Standalone listeners
        new GlobalListener(this),
        new WaitingCountdownListener(this),
        new LobbyListener(this),
        new SpectatorListener(this),
        new PvpListener(),

        // Trinket (contained in craft package)
        new TrinketBagListener(this),
        new TrinketBagInventoryListener(this),

        // Game listeners
        new BlockBreakListener(),
        new EntityDropsListener(),
        new GameDamageListener(this),
        new ItemCombustionListener(),
        new MobBurnListener(),
        new MonsterSpawnListener(),
        new StrengthNerfListener()
    );
  }
}
