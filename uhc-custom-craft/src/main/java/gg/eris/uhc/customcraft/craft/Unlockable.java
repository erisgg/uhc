package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;

public abstract class Unlockable implements Identifiable {

  public static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("unlockable");

  private final Identifier identifier;

  public Unlockable(String identifierValue) {
    this.identifier = IDENTIFIER_PROVIDER.id(identifierValue);
  }

  public abstract Vocation getVocation();

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

}
