package gg.eris.uhc.customcraft.game;

import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.rank.Rank;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.UhcPlayer;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public final class CustomCraftUhcPlayer extends UhcPlayer {

  private final Object2IntMap<Identifier> unlocks;
  private final Object2IntMap<Identifier> crafted;

  public CustomCraftUhcPlayer(UUID uuid, String name, List<String> nameHistory,
      long firstLogin, long lastLogin, Rank rank,
      List<Permission> permissions) {
    super(uuid, name, nameHistory, firstLogin, lastLogin, rank, permissions);
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
