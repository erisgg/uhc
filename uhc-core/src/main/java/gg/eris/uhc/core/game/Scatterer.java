package gg.eris.uhc.core.game;

import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class Scatterer {

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

    for (int i = 0; i < count; i++) {
      int x = RandomUtil.randomInt(-radius, radius + 1);
      int z = RandomUtil.randomInt(-radius, radius + 1);
      int y = this.game.getWorld().getHighestBlockYAt(x, z) + 1;
      locations.add(new Location(this.game.getWorld(), x, y, z));
    }

    return locations;
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
