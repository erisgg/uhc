package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;

public abstract class Unlockable implements Identifiable {

  private final Identifier identifier;

  public Unlockable(String identifierValue) {
    this.identifier = CustomCraftUhcIdentifiers.UNLOCKABLE.id(identifierValue);
  }

  public abstract Vocation getVocation();

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

}
