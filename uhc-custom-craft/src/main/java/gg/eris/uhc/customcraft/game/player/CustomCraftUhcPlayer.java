package gg.eris.uhc.customcraft.game.player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.customcraft.craft.Craft;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagItem;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.lang3.Validate;


public final class CustomCraftUhcPlayer extends UhcPlayer {

  private final Object2IntMap<Identifier> perks;
  private final Set<Identifier> craftUnlocks;
  private final Object2IntMap<Identifier> crafted;
  private final Map<Vocation, IntSet> treeData;

  @Getter
  private final TrinketBagItem trinketBagItem;

  @Getter
  private int coins;

  @Getter
  private final int star;

  public CustomCraftUhcPlayer(DefaultData data, int wins, int kills, int gamesPlayed, int coins) {
    super(data, wins, kills, gamesPlayed);
    this.perks = new Object2IntArrayMap<>();
    this.craftUnlocks = Sets.newHashSet();
    this.crafted = new Object2IntArrayMap<>();
    this.treeData = Maps.newHashMap();
    this.trinketBagItem = new TrinketBagItem(this);
    this.coins = coins;
    this.star = CustomCraftUhcTiers.getTier(CustomCraftUhcTiers.getPoints(kills, wins));
  }

  public int getPerkLevel(Perk perk) {
    return getPerkLevel(perk.getIdentifier());
  }

  public int getPerkLevel(Identifier identifier) {
    return this.perks.getOrDefault(identifier, 0);
  }

  public int getTimesCrafted(Craft craft) {
    return getTimesCrafted(craft.getIdentifier());
  }

  public int getTimesCrafted(Trinket trinket) {
    return getTimesCrafted(trinket.getIdentifier());
  }

  public int getTimesCrafted(Identifier identifier) {
    return this.crafted.getOrDefault(identifier, 0);
  }

  public void incrementCraftCount(Identifier identifier) {
    this.crafted.put(identifier, getTimesCrafted(identifier) + 1);
  }

  public boolean hasUnlockable(Identifier identifier) {
    Unlockable unlockable = Vocation.getUnlockable(identifier);
    return hasUnlockable(unlockable);
  }

  public void addUnlockable(Unlockable unlockable) {
    if (unlockable instanceof Craft || unlockable instanceof Trinket) {
      this.craftUnlocks.add(unlockable.getIdentifier());
    } else if (unlockable instanceof Perk) {
      this.perks.put(unlockable.getIdentifier(),
          this.perks.getOrDefault(unlockable.getIdentifier(),0) + 1);
    } else {
      throw new IllegalArgumentException("Unknown unlockable passed: " + unlockable);
    }
  }

  public boolean hasUnlockable(Unlockable unlockable) {
    if (unlockable instanceof Craft) {
      return hasCraft((Craft) unlockable);
    } else if (unlockable instanceof Perk) {
      return getPerkLevel(unlockable.getIdentifier()) > 0;
    } else if (unlockable instanceof Trinket) {
      return hasTrinket((Trinket) unlockable);
    } else {
      return false;
    }
  }

  public boolean hasCraft(Craft craft) {
    return this.craftUnlocks.contains(craft.getIdentifier());
  }

  public boolean hasTrinket(Trinket trinket) {
    return this.craftUnlocks.contains(trinket.getIdentifier());
  }

  public boolean hasSlot(Vocation vocation, int slot) {
    return this.treeData.getOrDefault(vocation, IntSets.EMPTY_SET).contains(slot);
  }

  public void addTreeData(Vocation vocation, int slot) {
    IntSet set = this.treeData.getOrDefault(vocation, new IntArraySet());
    set.add(slot);
    this.treeData.put(vocation, set);
  }

  /**
   * Gives an amount of coins (amount does not contain boosted amounts)
   *
   * @param amount is the amount of coins to give
   * @return is the amount given with boosters applied
   */
  public int giveCoins(int amount, boolean applyBooster) {
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
