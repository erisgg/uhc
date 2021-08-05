package gg.eris.uhc.customcraft.craft.vocation.miner;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.miner.craft.LuckPick;
import gg.eris.uhc.customcraft.craft.vocation.miner.craft.LumberAxe;
import gg.eris.uhc.customcraft.craft.vocation.miner.craft.SmeltersPickaxe;
import gg.eris.uhc.customcraft.craft.vocation.miner.craft.SmeltersShovel;
import gg.eris.uhc.customcraft.craft.vocation.miner.perk.MinerPerk;
import gg.eris.uhc.customcraft.craft.vocation.miner.trinket.HepsCharm;
import gg.eris.uhc.customcraft.craft.vocation.miner.trinket.PlutusFoot;

public final class MinerVocationRegistry extends VocationRegistry {

  private static final MinerVocationRegistry REGISTRY = new MinerVocationRegistry();

  private final MinerPerk perk;

  private final PlutusFoot firstTrinket;
  private final HepsCharm secondTrinket;

  private final SmeltersPickaxe firstCraft;
  private final SmeltersShovel secondCraft;
  private final LumberAxe thirdCraft;
  private final LuckPick fourthCraft;

  private MinerVocationRegistry() {
    this.perk = register(new MinerPerk(UhcPlugin.getPlugin().getCommons().getErisPlayerManager()));
    this.firstTrinket = register(new PlutusFoot());
    this.secondTrinket = register(new HepsCharm());
    this.firstCraft = register(new SmeltersPickaxe());
    this.secondCraft = register(new SmeltersShovel());
    this.thirdCraft = register(new LumberAxe());
    this.fourthCraft = register(new LuckPick());
  }

  @Override
  public String getIdentifierValue() {
    return "miner";
  }

  @Override
  public MinerPerk getPerk() {
    return this.perk;
  }

  @Override
  public PlutusFoot getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public HepsCharm getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public SmeltersPickaxe getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public SmeltersShovel getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public LumberAxe getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public LuckPick getFourthCraft() {
    return this.fourthCraft;
  }

  public static MinerVocationRegistry get() {
    return REGISTRY;
  }

}
