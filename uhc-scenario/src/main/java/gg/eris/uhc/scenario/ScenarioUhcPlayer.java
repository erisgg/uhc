package gg.eris.uhc.scenario;

import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.Set;
import java.util.UUID;

public class ScenarioUhcPlayer extends UhcPlayer {

  public ScenarioUhcPlayer(UUID uuid, String name,
      Set<String> knownAliases, long firstLogin, long lastLogin, long lastLogout) {
    super(uuid, name, knownAliases, firstLogin, lastLogin, lastLogout);
  }

}
