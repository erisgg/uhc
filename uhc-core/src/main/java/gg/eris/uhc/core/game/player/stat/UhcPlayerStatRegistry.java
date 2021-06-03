package gg.eris.uhc.core.game.player.stat;

import gg.eris.commons.core.registry.Registry;
import gg.eris.commons.core.util.Identifier;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.game.player.stat.type.KillsPlayerStat;
import gg.eris.uhc.core.game.player.stat.type.WinsPlayerStat;

public final class UhcPlayerStatRegistry extends Registry<UhcPlayerStat<?>> {

  private UhcPlayerStatRegistry(UhcModule<?> plugin) {
    super(Identifier.of(plugin.getClass(), "playerstats"));

    register(Identifier.of(plugin.getClass(), "kills"), new KillsPlayerStat());
    register(Identifier.of(plugin.getClass(), "wins"), new WinsPlayerStat());
  }

  public static UhcPlayerStatRegistry newRegistry(UhcModule<?> module) {
    return new UhcPlayerStatRegistry(module);
  }

}
