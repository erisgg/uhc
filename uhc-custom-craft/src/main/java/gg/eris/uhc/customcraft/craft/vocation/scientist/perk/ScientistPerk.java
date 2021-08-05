package gg.eris.uhc.customcraft.craft.vocation.scientist.perk;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.event.UhcChangeStateEvent;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

/**
 * Gives absorption 2 on game start for 1 minute per level
 */
public final class ScientistPerk extends Perk {

  public ScientistPerk() {
    super("scientist_perk");
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
  public String getName() {
    return "Scientist Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Gives absorption for {0} minutes when the game starts", level);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SCIENTIST;
  }

}
