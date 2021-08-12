package gg.eris.uhc.customcraft.craft.vocation.duelist.perk;

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
 * Gives Strength I for 0.5s per level
 */
public final class DuelistPerk extends Perk {

  public DuelistPerk() {
    super("duelist_perk");
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
          handle.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(level * 10, 0));
      if (added) {
        TextController.send(
            handle,
            TextType.INFORMATION,
            "Your duelist perk has activated, giving you <h>Strength I</h> for <h>{0}<h>.",
            Time.toShortDisplayTime(level / 2, TimeUnit.SECONDS)
        );
      }
    }
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Grants Strength I for {0} seconds when you kill a player",
        level * 0.5);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.DUELIST;
  }

  @Override
  public String getName() {
    return "Duelist Perk";
  }
}
