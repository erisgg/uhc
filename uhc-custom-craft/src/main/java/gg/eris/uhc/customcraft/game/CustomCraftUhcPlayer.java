package gg.eris.uhc.customcraft.game;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.UhcPlayer;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.bukkit.entity.Player;

public final class CustomCraftUhcPlayer extends UhcPlayer {

  private final Object2IntMap<Identifier> unlocks;
  private final Object2IntMap<Identifier> crafted;

  public CustomCraftUhcPlayer(Player player) {
    super(player);
    this.unlocks = new Object2IntArrayMap<>();
    this.crafted = new Object2IntArrayMap<>();
  }

  public void loadUnlocks() {

  }

  public int getUnlockLevel(Identifier identifier) {
    return this.unlocks.getOrDefault(identifier, 0);
  }

  public int getTimesCrafted(Identifier identifier) {
    return this.crafted.getOrDefault(identifier, 0);
  }

  public void incrementCraftCount(Identifier identifier) {
    this.crafted.put(identifier, getTimesCrafted(identifier) + 1);
  }

}
