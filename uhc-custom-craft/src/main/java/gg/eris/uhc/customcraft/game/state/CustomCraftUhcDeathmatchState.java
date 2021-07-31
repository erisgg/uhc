package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractDeathmatchGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.objects.Object2LongArrayMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public final class CustomCraftUhcDeathmatchState extends
    AbstractDeathmatchGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final World WORLD = Bukkit.getWorld(CustomCraftUhcIdentifiers.DEATHMATCH_WORLD);

  private static final Identifier SCOREBOARD_IDENTIFIER = Identifier.of("scoreboard", "deathmatch");

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

  private final CommonsScoreboard scoreboard;
  private final Object2LongMap<Location> placedBlocks;

  private int countdown;

  public CustomCraftUhcDeathmatchState(CustomCraftUhcGame game) {
    super(game);
    this.placedBlocks = new Object2LongArrayMap<>();

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.GOLD.bold() + "Eris " + CC.YELLOW.bold() + "UHC");
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
        (player, ticks) -> CC.GRAY + "Players: " + CC.YELLOW + game.getPlugin().getCommons()
            .getErisPlayerManager().getPlayers().size(), 1);
    this.scoreboard.addLine("");
    this.scoreboard
        .addLine(CC.GRAY + "Border: " + CC.YELLOW + game.getSettings().getBorderRadius());
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onStart() {
    this.countdown = this.game.getSettings().getDeathmatchStartCountdownDuration();
    int index = 0;
    for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
      Location location = SPAWNS[index++];
      if (index > 16) {
        index = 0;
      }
      player.getHandle().teleport(location);
    }
    this.scoreboard.addAllPlayers();
  }

  @Override
  public void onEnd() {
    this.scoreboard.removeAllPlayers();
    this.game.getPlugin().getCommons().getScoreboardController().removeScoreboard(this.scoreboard);
  }

  @Override
  public void onTick(int tick) {
    if (this.ticks % 20 == 0 && this.countdown > 0) {
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
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (this.countdown > 0) {
      Location from = event.getFrom();
      Location to = event.getTo();
      if (from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (this.countdown > 0) {
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!this.placedBlocks.containsKey(event.getBlock().getLocation())) {
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
    }, this.game.getSettings().getDeathmatchBlockDecayDelay());
  }

}
