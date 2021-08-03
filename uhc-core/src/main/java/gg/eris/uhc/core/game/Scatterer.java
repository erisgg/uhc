package gg.eris.uhc.core.game;

import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class Scatterer {

  private static final int MINIMUM_SPACING = 50;

  // Factor from center is like a percentage from center, e.g. 0 is (0, 0) and 1 touches the border.
  private static final double MINIMUM_FACTOR_FROM_CENTER = 0.25;
  private static final double MAXIMUM_FACTOR_FROM_CENTER = 0.75;

  private final UhcGame<?> game;
  private final int scattersPerSecond;
  private final Runnable finishCallback;

  public Scatterer(UhcGame<?> game, int scattersPerSecond, Runnable finishCallback) {
    Validate.isTrue(scattersPerSecond > 0, "cannot scatter <= 0 players per second");
    this.game = game;
    this.scattersPerSecond = scattersPerSecond;
    this.finishCallback = finishCallback;
  }

  private boolean hasStarted = false;

  public synchronized void scatter() {
    if (this.hasStarted) {
      throw new IllegalStateException("Scatter has already begun");
    }

    this.hasStarted = true;

    List<Player> players = this.game.getPlayers().stream()
        .map(UhcPlayer::getHandle)
        .collect(Collectors.toList());

    List<Location> locations = generateLocations(players.size());

    ScatterRunnable runnable = new ScatterRunnable(this, players, locations);
    runnable.runTaskTimer(this.game.plugin, 20L, 20L);
  }

  private void finish() {
    this.finishCallback.run();
  }

  private List<Location> generateLocations(int count) {
    List<Location> locations = new ArrayList<>(count);

    int radius = this.game.settings.getBorderRadius();

    double step = Math.PI * 2 / count;

    for (int i = 0; i < count; i++) {
      double angle = i * step;

      double xOffset = Math.cos(angle);
      double zOffset = Math.sin(angle);

      double multiplier;
      Location newLocation;

      do {
        // Sqrt compresses the range allowing for (0, 0) bias.
        multiplier = Math.min(MAXIMUM_FACTOR_FROM_CENTER,
            1 + MINIMUM_FACTOR_FROM_CENTER - Math.sqrt(1 - RandomUtil.randomDouble(0, 1)));

        int x = (int) (xOffset * radius * multiplier);
        int z = (int) (zOffset * radius * multiplier);
        int y = this.game.getWorld().getHighestBlockYAt(x, z);

        newLocation = new Location(this.game.getWorld(), x, y, z);

        System.out.println("Attempted to generate location:");
        System.out.println("Block: " + newLocation.getBlock().getType());
        System.out
            .println("Block above: " + newLocation.getBlock().getRelative(BlockFace.UP).getType());

      } while (!isLegalLocation(newLocation, locations));

      locations.add(newLocation);
    }

    return locations;
  }

  private boolean isLegalLocation(Location location, List<Location> locations) {
    // If the block above the highest solid block is a liquid, spawning there should be disallowed.
    if (!location.getBlock().getRelative(BlockFace.UP).isEmpty()) {
      return false;
    }

    // Ensures location is at least the minimum block distance away from all others.
    for (Location otherLocation : locations) {
      // Squared since it can reduce the expensiveness of a sqrt call.
      if (location.distanceSquared(otherLocation) < MINIMUM_SPACING * MINIMUM_SPACING) {
        return false;
      }
    }

    return true;
  }

  @RequiredArgsConstructor
  private static class ScatterRunnable extends BukkitRunnable {

    private final Scatterer parent;
    private final List<Player> players;
    private final List<Location> locations;

    @Override
    public void run() {
      for (int i = 0; i < Math.min(this.parent.scattersPerSecond, this.players.size()); i++) {
        Player player = this.players.remove(0);
        Location location = this.locations.remove(0);
        player.teleport(location);
      }

      if (this.players.isEmpty() || this.locations.isEmpty()) {
        this.parent.finish();
        cancel();
      }
    }
  }

}
