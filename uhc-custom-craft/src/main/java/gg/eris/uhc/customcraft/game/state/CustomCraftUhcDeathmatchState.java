package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.commons.core.util.Text;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractDeathmatchGameState;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2LongArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public final class CustomCraftUhcDeathmatchState extends
    AbstractDeathmatchGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final World WORLD = new WorldCreator(CustomCraftUhcIdentifiers.DEATHMATCH_WORLD)
      .createWorld();

  private static final Identifier SCOREBOARD_IDENTIFIER =
      CustomCraftUhcIdentifiers.SCOREBOARD_ID.id("deathmatch");

  private static final Location[] SPAWNS = {
      new Location(
          WORLD,
          -53.5,
          48,
          -80.5,
          0,
          0
      ),
      new Location(
          WORLD,
          -80.5,
          48,
          -53.5,
          -90,
          0
      ),
      new Location(
          WORLD,
          -80.5,
          48,
          -21.5,
          -90,
          0
      ),
      new Location(
          WORLD,
          -80.5,
          48,
          22.5,
          -90,
          0
      ),
      new Location(
          WORLD,
          -80.5,
          48,
          54.5,
          -90,
          0
      ),
      new Location(
          WORLD,
          -53.5,
          48,
          81.5,
          180,
          0
      ),
      new Location(
          WORLD,
          -21.5,
          48,
          81.5,
          180,
          0
      ),
      new Location(
          WORLD,
          22.5,
          48,
          81.5,
          180,
          0
      ),
      new Location(
          WORLD,
          -80.5,
          48,
          54.5,
          -90,
          0
      ),
      new Location(
          WORLD,
          81.5,
          48,
          22.5,
          90,
          0
      ),
      new Location(
          WORLD,
          81.5,
          48,
          -21.5,
          90,
          0
      ),
      new Location(
          WORLD,
          81.5,
          48,
          -53.5,
          90,
          0
      ),
      new Location(
          WORLD,
          54.5,
          48,
          -80.5,
          0,
          0
      ),
      new Location(
          WORLD,
          22.5,
          48,
          -80.5,
          0,
          0
      ),
      new Location(
          WORLD,
          -21.5,
          48,
          -80.5,
          0,
          0
      ),
      new Location(
          WORLD,
          81.5,
          48,
          54.5,
          90,
          0
      )
  };

  private final Object2LongMap<Location> placedBlocks;
  private final CommonsScoreboard scoreboard;

  private int countdown;
  private int deathmatchDuration;
  private boolean borderStarted;
  private boolean borderFinshed;

  public CustomCraftUhcDeathmatchState(CustomCraftUhcGame game) {
    super(game);
    this.placedBlocks = new Object2LongArrayMap<>();
    this.countdown = 0;
    this.deathmatchDuration = 0;
    this.borderStarted = false;
    this.borderFinshed = false;

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.YELLOW.bold() + "Eris " + CC.GOLD.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine((player, tick) -> this.countdown > 0 ? CC.GRAY + "Deathmatch in:" :
        CC.GRAY + "Deathmatch has ", 1);
    this.scoreboard.addLine((player, tick) -> this.countdown > 0 ?
            CC.YELLOW + Time.toShortDisplayTime(this.countdown, TimeUnit.SECONDS)
            : CC.YELLOW + "started",
        1
    );
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlayers().size(), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Kills: " + CC.YELLOW + ((CustomCraftUhcPlayer) player)
            .getGameKills(), 1);
    this.scoreboard.addLine("");
    this.scoreboard
        .addLine((player, ticks) ->
            CC.GRAY + "Border: " + CC.YELLOW + Math
                .round(game.getDeathmatch().getWorldBorder().getSize() / 2), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onStart() {
    this.countdown = this.game.getSettings().getDeathmatchStartCountdownDuration();
    int index = 0;
    IntList indexList = RandomUtil.randomList(SPAWNS.length);

    for (Player handle : Bukkit.getOnlinePlayers()) {
      if (this.game.isPlayer(handle)) {
        CustomCraftUhcPlayer player = this.game.getPlayer(handle);
        Location location = SPAWNS[indexList.get(index++)];
        if (index > indexList.size()) {
          index = 0;
        }
        handle.teleport(location);

        int coins = player.giveCoins(this.game.getSettings().getCoinsPerDeathmatch());
        TextController.send(
            handle,
            TextType.INFORMATION,
            "You have survived to <h>deathmatch</h> (+<h>{0}</h> coins).",
            Text.formatInt(coins)
        );
      } else {
        handle.teleport(new Location(this.game.getDeathmatch(), 0,
            this.game.getWorld().getHighestBlockYAt(0, 0) + 50, 0));
      }
    }

    WorldBorder border = this.game.getDeathmatch().getWorldBorder();
    border.setSize(
        this.game.getSettings().getDeathmatchBorderRadius()
    );

    this.scoreboard.addAllPlayers();

    Bukkit.getScheduler().runTaskLater(this.game.getPlugin(), () -> {
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The server will shut down in <h>5 minutes</h>."
      );
      Bukkit.getScheduler().runTaskLater(this.game.getPlugin(), Bukkit::shutdown, 5 * 60 * 20);
    }, 15 * 60 * 20);
  }

  @Override
  public void onTick(int tick) {
    if (this.countdown == 0 && !this.borderStarted
        && this.game.getSettings().getDeathmatchBorderShrinkDelay() == 0) {
      startBorderShrink();
    }

    if (this.ticks % 20 != 0) {
      return;
    }

    if (this.countdown > 0) {
      if (--this.countdown == 0) {
        TextController.broadcastToServer(
            TextType.INFORMATION,
            "Deathmatch has <h>begun!</h>"
        );
      } else {
        TextController.broadcastToServer(
            TextType.INFORMATION,
            "Deathmatch begins in <h>{0}</h>!",
            Time.toLongDisplayTime(this.countdown, TimeUnit.SECONDS)
        );
      }
    } else {
      if (!this.borderStarted && this.deathmatchDuration == this.game.getSettings()
          .getDeathmatchBorderShrinkDelay()) {
        startBorderShrink();
      } else if (this.borderStarted && !this.borderFinshed) {
        if (this.game.getDeathmatch().getWorldBorder().getSize() / 2 == this.game.getSettings()
            .getDeathmatchBorderShrunkRadius()) {
          this.borderFinshed = true;
          TextController.broadcastToServer(
              TextType.INFORMATION,
              "The deathmatch border has <h>stopped</h> shrinking."
          );
        }
      }

      this.deathmatchDuration++;
    }
  }

  @Override
  public void onEnd() {
    this.scoreboard.removeAllPlayers();
    this.game.getPlugin().getCommons().getScoreboardController().removeScoreboard(this.scoreboard);
  }

  private void startBorderShrink() {
    WorldBorder border = this.game.getDeathmatch().getWorldBorder();
    border.setSize(
        this.game.getSettings().getDeathmatchBorderShrunkRadius() * 2,
        this.game.getSettings().getDeathmatchBorderShrinkDuration()
    );
    TextController.broadcastToServer(
        TextType.INFORMATION,
        "The deathmatch border has <h>begun</h> shrinking."
    );
    this.borderStarted = true;
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (!this.game.isPlayer(event.getPlayer())) {
      return;
    }

    if (this.countdown > 0) {
      Location from = event.getFrom();
      Location to = event.getTo();
      if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
        event.setTo(from);
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onEntityDamage(EntityDamageEvent event) {
    if (this.countdown > 0) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!this.placedBlocks.containsKey(event.getBlock().getLocation())
        && event.getBlock().getType().isSolid()) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    long time = System.currentTimeMillis();
    this.placedBlocks.put(event.getBlockPlaced().getLocation(), time);
    Bukkit.getScheduler().runTaskLater(this.game.getPlugin(), () -> {
      if (this.placedBlocks.get(event.getBlockPlaced().getLocation()) == time) {
        event.getBlock().setType(Material.AIR);
        this.placedBlocks.remove(event.getBlockPlaced().getLocation());
      }
    }, this.game.getSettings().getDeathmatchBlockDecayDelay() * 20L);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    this.scoreboard.addPlayer(event.getPlayer());
  }

}
