package gg.eris.uhc.customcraft.game;

import gg.eris.commons.bukkit.player.ErisPlayerSerializer;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.UhcGameSettings;
import gg.eris.uhc.core.game.state.UhcGameStateFactory;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.CraftListener;
import gg.eris.uhc.customcraft.craft.TrinketListener;
import gg.eris.uhc.customcraft.craft.VocationStateTicker;
import gg.eris.uhc.customcraft.craft.bag.TrinketBag;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagInventoryListener;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagListener;
import gg.eris.uhc.customcraft.craft.menu.main.MainMenu;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenu;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
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
import gg.eris.uhc.customcraft.game.listener.game.PlayerHeadListener;
import gg.eris.uhc.customcraft.game.listener.game.ScoreboardHeartsListener;
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
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.SkullMeta;

public final class CustomCraftUhcGame extends UhcGame<CustomCraftUhcPlayer> {

  public static final int GRACE_PERIOD_TIME = 10 * 60;
  public static final int PVP_PERIOD_TIME = 30 * 60; // Overall
  public static final int PVP_NON_COUNTDOWN_TIME = 20 * 60;
  public static final int DEATHMATCH_COUNTDOWN_TIME = 10 * 60;

  private static final Int2IntMap COIN_MAP;

  static {
    COIN_MAP = new Int2IntArrayMap();
    COIN_MAP.put(10, 500);
  }

  @Getter
  private final MainMenu mainMenu;

  @Getter
  private final RecipeBookMenu recipeBookMenu;

  public CustomCraftUhcGame(UhcPlugin plugin, UhcModule<?> module) {
    super(plugin, module, UhcGameSettings.builder()
        .worldName(CustomCraftUhcIdentifiers.GAME_WORLD)
        .netherName(CustomCraftUhcIdentifiers.GAME_NETHER)
        .deathmatchName(CustomCraftUhcIdentifiers.DEATHMATCH_WORLD)
        .borderRadius(1000)
        .maxHealth(40)
        .requiredPlayers(2)
        .attackCreditDuration(20)

        .pregameCountdownDuration(5)
        .gracePeriodDuration(60)
        .pvpPeriodDuration(120)
        .preDeathmatchCountdownDuration(30)
        .postGameShutdownDelay(15)

        .borderShrunkRadius(400)
        .borderShrinkDelay(1000)
        .borderShrinkDuration(30)

        .deathmatchPlayerThreshold(1)
        .deathmatchBorderRadius(200)
        .deathmatchBorderShrinkDelay(30)
        .deathmatchBorderShrinkDuration(300)
        .deathmatchBorderShrunkRadius(20)
        .deathmatchBlockDecayDelay(30)
        .deathmatchStartCountdownDuration(5)


        .coinsPerKill(300)
        .coinsPerWin(3000)
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
    this.mainMenu = new MainMenu(getPlugin());
    this.recipeBookMenu = new RecipeBookMenu(getPlugin());
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
  protected void filterDrops(CustomCraftUhcPlayer player, List<ItemStack> drops) {
    drops.removeIf(TrinketBag::isBag);
    for (Trinket trinket : player.getTrinketBagItem().getContents()) {
      if (trinket != null) {
        drops.add(trinket.getItem());
      }
    }

    ItemStack head = new ItemBuilder(new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()))
        .applyMeta(SkullMeta.class, meta -> meta.setOwner(player.getDisplayName())).build();

    drops.add(head);
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
        new StrengthNerfListener(),

        // Craft
        new CraftListener(this.getPlugin().getCommons().getErisPlayerManager()),
        new TrinketListener(this),
        new VocationStateTicker(),

        // Regular player heads
       new PlayerHeadListener(this),

        // Tab
       new ScoreboardHeartsListener(this)
    );
  }
}
