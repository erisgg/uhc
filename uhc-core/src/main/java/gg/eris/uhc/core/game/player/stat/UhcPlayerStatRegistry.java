package gg.eris.uhc.core.game.player.stat;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.game.player.stat.type.KillsPlayerStat;
import gg.eris.uhc.core.game.player.stat.type.WinsPlayerStat;

public final class UhcPlayerStatRegistry extends Registry<UhcPlayerStat<?>> {

  private final IdentifierProvider identifierProvider;

  private UhcPlayerStatRegistry(IdentifierProvider identifierProvider) {
    super(identifierProvider.id("playerstats"));

    this.identifierProvider = identifierProvider;

    register(new KillsPlayerStat());
    register(new WinsPlayerStat());
  }

  public void register(UhcPlayerStat<?> stat) {
    this.register(this.identifierProvider.id(stat.getName()), stat);
  }

  public static UhcPlayerStatRegistry newRegistry(IdentifierProvider identifierProvider) {
    return new UhcPlayerStatRegistry(identifierProvider);
  }

}
