package gg.eris.uhc.customcraft.game.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcTiers;
import org.bukkit.Location;

public final class TierInfo {

  private final Hologram hologram;

  public TierInfo(UhcPlugin plugin, Location location) {
    this.hologram = HologramsAPI.createHologram(plugin, location);
    loadHologram();
  }

  private void loadHologram() {
    this.hologram.appendTextLine(CC.YELLOW.bold() + "ERIS" + CC.GOLD.bold() + " TIER INFO");

    for (int i = 1; i <= 10; i++) {
      this.hologram.appendTextLine("");
      this.hologram
          .appendTextLine(CC.YELLOW.bold().toString() + i + " " + CustomCraftUhcIdentifiers.STAR
              + CC.GOLD + " - "
              + Text.formatInt(CustomCraftUhcTiers.getPointsForTier(i)) + " Points");
    }
  }

  public void remove() {
    if (!this.hologram.isDeleted()) {
      this.hologram.delete();
    }
  }
}
