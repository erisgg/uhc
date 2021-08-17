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
import gg.eris.uhc.customcraft.game.listener.game.BowNerfListener;
import gg.eris.uhc.customcraft.game.listener.game.EntityDropsListener;
import gg.eris.uhc.customcraft.game.listener.game.GameDamageListener;
import gg.eris.uhc.customcraft.game.listener.game.GoldenAppleListener;
import gg.eris.uhc.customcraft.game.listener.game.ItemCombustionListener;
import gg.eris.uhc.customcraft.game.listener.game.MobBurnListener;
import gg.eris.uhc.customcraft.game.listener.game.MonsterSpawnListener;
import gg.eris.uhc.customcraft.game.listener.game.PlayerHeadListener;
import gg.eris.uhc.customcraft.game.listener.game.ScoreboardHeartsListener;
import gg.eris.uhc.customcraft.game.listener.game.SheepShearListener;
import gg.eris.uhc.customcraft.game.listener.game.StrengthNerfListener;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayerSerializer;
import gg.eris.uhc.customcraft.game.spectator.SpectatorChatManager;
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
  public static final int PVP_NON_COUNTDOWN_TIME = 15 * 60;
  public static final int DEATHMATCH_COUNTDOWN_TIME = 10 * 60;

  @Getter
  private final SpectatorChatManager spectatorChatManager;

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
        .attackCreditDuration(20)

        .requiredPlayers(12)
        .shortenPlayers(25)
        .maxPlayers(70)
        .pregameCountdownDuration(5 * 60 + 30)
        .shortenCountdownDuration(90)

        .gracePeriodDuration(GRACE_PERIOD_TIME)
        .pvpPeriodDuration(PVP_NON_COUNTDOWN_TIME)
        .preDeathmatchCountdownDuration(DEATHMATCH_COUNTDOWN_TIME)
        .postGameShutdownDelay(15)

        .borderShrunkRadius(400)
        .borderShrinkDelay(0)
        .borderShrinkDuration(900)

        .deathmatchPlayerThreshold(10)
        .deathmatchBorderRadius(200)
        .deathmatchBorderShrinkDelay(3 * 60)
        .deathmatchBorderShrinkDuration(240)
        .deathmatchBorderShrunkRadius(10)
        .deathmatchBlockDecayDelay(20)
        .deathmatchStartCountdownDuration(5)

        .coinsPerKill(300)
        .coinsPerWin(3000)
        .coinsPerDeathmatch(500)
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

    this.spectatorChatManager = new SpectatorChatManager(this);

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
        new GoldenAppleListener(),
        new ItemCombustionListener(),
        new MobBurnListener(),
        new MonsterSpawnListener(),
        new StrengthNerfListener(),
        new BowNerfListener(),
        new SheepShearListener(),

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
