package gg.eris.uhc.customcraft.craft.vocation.scientist;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.uhc.core.event.UhcChangeStateEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

/**
 * Gives absorption 2 on game start for 1 minute per level
 */
public final class ScientistPerk extends Perk {

  private final ErisPlayerManager erisPlayerManager;

  public ScientistPerk(ErisPlayerManager erisPlayerManager) {
    super("scientist_perk");
    this.erisPlayerManager = erisPlayerManager;
  }

  @EventHandler
  public void onChangeState(UhcChangeStateEvent event) {
    if (event.getState().getType() == TypeRegistry.GRACE_PERIOD) {
      for (UhcPlayer player : event.getGame().getPlayers()) {
        CustomCraftUhcPlayer craftPlayer = (CustomCraftUhcPlayer) player;
        int level = getLevel(craftPlayer);
        if (level > 0) {
          craftPlayer.getHandle()
              .addPotionEffect(PotionEffectType.ABSORPTION.createEffect(20 * 60 * level, 1));
        }
      }
    }
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }

}
