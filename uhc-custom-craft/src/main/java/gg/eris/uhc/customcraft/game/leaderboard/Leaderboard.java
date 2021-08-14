package gg.eris.uhc.customcraft.game.leaderboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Sorts;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class Leaderboard {

  private final Location location;
  private final String name;

  private final Hologram hologram;
  private final List<HologramLine> lines;

  private final int taskId;

  public Leaderboard(UhcPlugin plugin, String name, Location location) {
    this.location = location;

    this.name = name;
    this.hologram = HologramsAPI.createHologram(plugin, this.location);
    this.lines = Lists.newArrayList();
    loadHologram();

    this.taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
      Bson query = Sorts.descending("games." + CustomCraftUhcIdentifiers.JSON_KEY + "." + name);
      List<JsonNode> results = null;
      try {
        results = plugin.getCommons().getOfflineDataManager().performSort(query, 10);
      } catch (JsonProcessingException err) {
        err.printStackTrace();
      }

      if (results == null) {
        return;
      }

      AtomicInteger position = new AtomicInteger();
      for (JsonNode result : results) {
        int value = 0;

        if (result.has("games")
            && result.get("games").has(CustomCraftUhcIdentifiers.JSON_KEY)
            && result.get("games").get(CustomCraftUhcIdentifiers.JSON_KEY).has(name)) {
          value = result.get("games").get(CustomCraftUhcIdentifiers.JSON_KEY).get(name).asInt();
        }

        if (value > 0) {
        int finalValue = value;
        Bukkit.getScheduler().runTask(plugin, () -> setPosition(
            position.getAndIncrement(), result.get("name").asText(), finalValue));
        }
      }
    }, 0, 5 * 20 * 60).getTaskId();
  }

  private void loadHologram() {
    this.hologram.appendTextLine(CC.YELLOW.bold() + this.name.toUpperCase(Locale.ROOT)
        + CC.GOLD.bold() + " LEADERBOARD");

    for (int i = 0; i < 10; i++) {
      this.hologram.appendTextLine("");
      this.lines.add(this.hologram.appendTextLine(""));
      setPosition(i, "-", 0);
    }
  }

  private void setPosition(int position, String fieldName, int value) {
    HologramLine line = this.lines.get(position);
    line.removeLine();
    this.lines.set(position, this.hologram.insertTextLine(2 * (position + 1),
        CC.YELLOW.bold().toString() + (position + 1) + ". " + CC.GOLD + fieldName + ": " + value));
  }

  public void remove() {
    if (!this.hologram.isDeleted()) {
      this.hologram.delete();
    }
  }
}
