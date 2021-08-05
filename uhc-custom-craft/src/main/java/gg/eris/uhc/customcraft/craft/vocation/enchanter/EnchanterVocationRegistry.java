package gg.eris.uhc.customcraft.craft.vocation.enchanter;

import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.craft.ExperienceBottleCraft;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.craft.LightEnchantTableCraft;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.craft.ModularWand;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.craft.VoidBagCraft;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.perk.EnchanterPerk;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.trinket.AresSiphon;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.trinket.IrisEnchantment;

public final class EnchanterVocationRegistry extends VocationRegistry {

  private static final EnchanterVocationRegistry REGISTRY = new EnchanterVocationRegistry();

  private final EnchanterPerk perk;

  private final IrisEnchantment firstTrinket;
  private final AresSiphon secondTrinket;

  private final ExperienceBottleCraft firstCraft;
  private final LightEnchantTableCraft secondCraft;
  private final VoidBagCraft thirdCraft;
  private final ModularWand fourthCraft;

  private EnchanterVocationRegistry() {
    this.perk =
        register(new EnchanterPerk(UhcPlugin.getPlugin().getCommons().getErisPlayerManager()));
    this.firstTrinket = register(new IrisEnchantment());
    this.secondTrinket = register(new AresSiphon());
    this.firstCraft = register(new ExperienceBottleCraft());
    this.secondCraft = register(new LightEnchantTableCraft());
    this.thirdCraft = register(new VoidBagCraft());
    this.fourthCraft = register(new ModularWand());
  }

  @Override
  public String getIdentifierValue() {
    return "enchanter";
  }

  @Override
  public EnchanterPerk getPerk() {
    return this.perk;
  }

  @Override
  public IrisEnchantment getFirstTrinket() {
    return this.firstTrinket;
  }

  @Override
  public AresSiphon getSecondTrinket() {
    return this.secondTrinket;
  }

  @Override
  public ExperienceBottleCraft getFirstCraft() {
    return this.firstCraft;
  }

  @Override
  public LightEnchantTableCraft getSecondCraft() {
    return this.secondCraft;
  }

  @Override
  public VoidBagCraft getThirdCraft() {
    return this.thirdCraft;
  }

  @Override
  public ModularWand getFourthCraft() {
    return this.fourthCraft;
  }

  public static EnchanterVocationRegistry get() {
    return REGISTRY;
  }

}
