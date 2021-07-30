package gg.eris.uhc.customcraft.game.player;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.UhcPlayer;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import lombok.Getter;


public final class CustomCraftUhcPlayer extends UhcPlayer {

  private final Object2IntMap<Identifier> unlocks;
  private final Object2IntMap<Identifier> crafted;

  @Getter
  private int coins;

  public CustomCraftUhcPlayer(DefaultData data, int coins) {
    super(data);
    this.unlocks = new Object2IntArrayMap<>();
    this.crafted = new Object2IntArrayMap<>();
    this.coins = coins;
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

  /**
   * Gives an amount of coins (amount does not contain boosted amounts)
   *
   * @param amount is the amount of coins to give
   * @return is the amount given with boosters applied
   */
  public int giveCoins(int amount) {
    // TODO: Coin boosters
    this.coins += amount;
    return amount;
  }

  public void spendCoins(int amount) {
    this.coins -= amount;
  }


}
