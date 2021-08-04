package gg.eris.uhc.customcraft.craft.vocation.armorer;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.event.UhcPlayerDeathEvent;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.Perk;
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
          handle.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(level * 20, 0));
      if (added) {
        TextController.send(
            handle,
            TextType.INFORMATION,
            "Your duelist perk has activated, giving you <h>Resistance I</h> for <h>{0}<h>.",
            Time.toShortDisplayTime(level, TimeUnit.SECONDS)
        );
      }
    }
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ARMORER;
  }

}
