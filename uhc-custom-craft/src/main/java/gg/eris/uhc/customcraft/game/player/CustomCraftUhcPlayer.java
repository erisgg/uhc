package gg.eris.uhc.customcraft.game.player;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.rank.RankRegistry;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.customcraft.craft.kit.Kit;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
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
  @Getter
  private final Object2IntMap<Identifier> kits;
  @Getter
  private final Map<Vocation, IntSet> treeData;
  @Getter
  private final Object2IntMap<Vocation> prestigeData;

  @Getter
  private final TrinketBagItem trinketBagItem;

  @Getter
  private final int star;

  @Getter
  private int coins;

  @Getter
  private Identifier activeKit;

  public CustomCraftUhcPlayer(DefaultData data, int gamesPlayed, int wins, int kills, int deaths,
      int coins, Map<Vocation, IntSet> treeData, Object2IntMap<Vocation> prestigeData,
      Object2IntMap<Identifier> kits, Identifier activeKit) {
    super(data, gamesPlayed, wins, kills, deaths);
    this.treeData = treeData;
    this.trinketBagItem = new TrinketBagItem(this);
    this.coins = coins;
    this.star = CustomCraftUhcTiers.getTier(CustomCraftUhcTiers.getPoints(kills, wins));
    this.prestigeData = prestigeData;
    this.kits = kits;
    this.activeKit = activeKit;

    this.perks = new Object2IntArrayMap<>();
    this.craftUnlocks = Sets.newHashSet();
    this.crafted = new Object2IntArrayMap<>();
    loadCraftsFromData();
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
    VocationUnlockable unlockable = Vocation.getUnlockable(identifier);
    return hasUnlockable(unlockable);
  }

  public int getPrestigeLevel(Vocation vocation) {
    return this.prestigeData.getOrDefault(vocation, 0);
  }

  public int getKitLevel(Kit kit) {
    return getKitLevel(kit.getIdentifier());
  }

  public int getKitLevel(Identifier identifier) {
    return this.kits.getOrDefault(identifier, 0);
  }

  public void setActiveKit(Kit kit) {
    this.activeKit = kit.getIdentifier();
  }

  public void addUnlockable(VocationUnlockable unlockable) {
    if (unlockable instanceof Craft || unlockable instanceof Trinket) {
      this.craftUnlocks.add(unlockable.getIdentifier());
    } else if (unlockable instanceof Perk) {
      this.perks.put(unlockable.getIdentifier(),
          this.perks.getOrDefault(unlockable.getIdentifier(), 0) + 1);
    } else {
      throw new IllegalArgumentException("Unknown unlockable passed: " + unlockable);
    }
  }

  public boolean hasUnlockable(VocationUnlockable unlockable) {
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

  public void levelUpKit(Kit kit) {
    this.kits.put(kit.getIdentifier(), this.kits.getOrDefault(kit.getIdentifier(), 0) + 1);
  }

  public void addTreeData(Vocation vocation, int slot) {
    IntSet set = this.treeData.getOrDefault(vocation, new IntArraySet());
    set.add(slot);
    this.treeData.put(vocation, set);
  }

  public void addPrestige(Vocation vocation) {
    this.prestigeData.put(vocation, this.prestigeData.getOrDefault(vocation, 0) + 1);
  }

  /**
   * Gives a coin and applies a booster
   *
   * @param amount is the amount of coins to give
   * @return the coins given
   */
  public int giveCoins(int amount) {
    return giveCoins(amount, true);
  }

  /**
   * Gives an amount of coins (amount does not contain boosted amounts)
   *
   * @param amount       is the amount of coins to give
   * @param applyBooster is whether to apply a booster
   * @return the coins given
   */
  public int giveCoins(int amount, boolean applyBooster) {
    Validate.isTrue(amount >= 0, "cannot give negative coins");

    if (applyBooster) {
      if (this.ranks.contains(RankRegistry.get().DEMIGOD) || this.ranks
          .contains(RankRegistry.get().PARTNER)) {
        amount *= 1.75;
      } else if (this.ranks.contains(RankRegistry.get().ELITE)) {
        amount *= 1.5;
      } else if (this.ranks.contains(RankRegistry.get().PRO) || this.ranks
          .contains(RankRegistry.get().CREATOR)) {
        amount *= 1.25;
      }
    }

    this.coins += amount;
    return amount;
  }

  public void spendCoins(int amount) {
    Validate.isTrue(amount >= 0, "cannot take negative coins");
    this.coins -= amount;
  }

  public void setCoins(int amount) {
    Validate.isTrue(amount >= 0, "cannot set negative coins");
    this.coins = amount;
  }

  public void loadCraftsFromData() {
    for (Map.Entry<Vocation, IntSet> entry : this.treeData.entrySet()) {
      Vocation vocation = entry.getKey();
      IntSet set = entry.getValue();
      if (set == null) {
        continue;
      }

      for (int slot : set) {
        addUnlockable(vocation.getUnlockableFromMenuSlot(slot));
      }
    }
  }


}
