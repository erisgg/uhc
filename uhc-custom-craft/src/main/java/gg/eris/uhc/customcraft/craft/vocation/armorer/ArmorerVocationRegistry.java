package gg.eris.uhc.customcraft.craft.vocation.armorer;

import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.DemigodBootsCraft;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.DemigodHelmetCraft;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.FusionArmorCraft;
import gg.eris.uhc.customcraft.craft.vocation.armorer.craft.ProtectionBookCraft;
import gg.eris.uhc.customcraft.craft.vocation.armorer.perk.ArmorerPerk;
import gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets.DefendersMedallionTrinket;
import gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets.ZeusBlessingTrinket;

public final class ArmorerVocationRegistry extends VocationRegistry {

  private static final ArmorerVocationRegistry REGISTRY = new ArmorerVocationRegistry();

  private final ArmorerPerk perk;
  private final DefendersMedallionTrinket firstTrinket;
  private final ZeusBlessingTrinket secondTrinket;
  private final DemigodHelmetCraft firstCraft;
  private final DemigodBootsCraft secondCraft;
  private final ProtectionBookCraft thirdCraft;
  private final FusionArmorCraft fourthCraft;

  private ArmorerVocationRegistry() {
    this.perk = register(new ArmorerPerk());
    this.firstTrinket = register(new DefendersMedallionTrinket());
    this.secondTrinket = register(new ZeusBlessingTrinket());
    this.firstCraft = register(new DemigodHelmetCraft());
    this.secondCraft = register(new DemigodBootsCraft());
    this.thirdCraft = register(new ProtectionBookCraft());
    this.fourthCraft = register(new FusionArmorCraft());
  }

  @Override
  public String getIdentifierValue() {
    return "armorer";
  }

  @Override
  public ArmorerPerk getPerk() {
    return this.perk;
  }

  @Override
  public DefendersMedallionTrinket getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public ZeusBlessingTrinket getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public DemigodHelmetCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public DemigodBootsCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public ProtectionBookCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public FusionArmorCraft getFourthCraft() {
    return this.fourthCraft;
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
  }

  public static ArmorerVocationRegistry get() {
    return REGISTRY;
  }
}
