package gg.eris.uhc.core.game.player.stat;

import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.core.game.player.stat.type.GamesPlayedPlayerStat;
import gg.eris.uhc.core.game.player.stat.type.KillsPlayerStat;
import gg.eris.uhc.core.game.player.stat.type.WinsPlayerStat;
import lombok.Getter;

public final class UhcPlayerStatRegistry extends Registry<UhcPlayerStat<?>> {

  @Getter
  private final IdentifierProvider identifierProvider;

  private UhcPlayerStatRegistry(IdentifierProvider identifierProvider) {
    this.identifierProvider = identifierProvider;

    register(new KillsPlayerStat(identifierProvider.id("kills")));
    register(new WinsPlayerStat(identifierProvider.id("wins")));
    register(new GamesPlayedPlayerStat(identifierProvider.id("games_played")));
  }

  public static UhcPlayerStatRegistry newPlayerStatRegistry(String namespace) {
    return new UhcPlayerStatRegistry(new IdentifierProvider(namespace));
  }

}
