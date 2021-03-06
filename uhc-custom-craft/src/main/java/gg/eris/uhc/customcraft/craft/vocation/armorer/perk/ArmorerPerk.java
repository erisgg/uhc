package gg.eris.uhc.customcraft.craft.vocation.armorer.perk;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Text;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.event.UhcPlayerDeathEvent;
import gg.eris.uhc.customcraft.craft.vocation.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

/**
 * Gives Resistance 1 for 1s per level
 */
public final class ArmorerPerk extends Perk {

  public ArmorerPerk() {
    super("armorer_perk");
  }

  @EventHandler
  public void onUhcPlayerDeath(UhcPlayerDeathEvent event) {
    if (event.getKiller() == null) {
      return;
    }

    CustomCraftUhcPlayer killer = (CustomCraftUhcPlayer) event.getKiller();
    Player handle = killer.getHandle();
    if (handle == null) {
      return;
    }

    int level = getLevel(killer);
    if (level > 0) {
      boolean added =
          handle.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(level * 20, 0),
              true);
      if (added) {
        TextController.send(
            handle,
            TextType.INFORMATION,
            "Your armorer perk has activated, giving you <h>Resistance I</h> for <h>{0}<h>.",
            Time.toShortDisplayTime(level, TimeUnit.SECONDS)
        );
      }
    }
  }

  @Override
  public String getName() {
    return "Armorer Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Grants resistance for {0} seconds when you kill a player.",
        level);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
  }

}
