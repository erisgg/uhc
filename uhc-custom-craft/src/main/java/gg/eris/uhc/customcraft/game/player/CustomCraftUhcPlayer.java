package gg.eris.uhc.customcraft.game.player;

import com.google.common.collect.Sets;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagItem;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.lang3.Validate;


public final class CustomCraftUhcPlayer extends UhcPlayer {

  private final Object2IntMap<Identifier> perks;
  private final Set<Identifier> craftUnlocks;
  private final Object2IntMap<Identifier> crafted;

  @Getter
  private final TrinketBagItem trinketBagItem;

  @Getter
  private int coins;

  public CustomCraftUhcPlayer(DefaultData data, int wins, int kills, int gamesPlayed, int coins) {
    super(data, wins, kills, gamesPlayed);
    this.perks = new Object2IntArrayMap<>();
    this.craftUnlocks = Sets.newHashSet();
    this.crafted = new Object2IntArrayMap<>();
    this.trinketBagItem = new TrinketBagItem(this);
    this.coins = coins;
  }

  public int getPerkLevel(Identifier identifier) {
    return this.perks.getOrDefault(identifier, 0);
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
    Validate.isTrue(amount >= 0, "cannot give negative coins");
    // TODO: Coin boosters
    this.coins += amount;
    return amount;
  }

  public void spendCoins(int amount) {
    Validate.isTrue(amount >= 0, "cannot take negative coins");
    this.coins -= amount;
  }


}
