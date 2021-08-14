package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class GoldenAppleListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void addCustomEffects(PlayerItemConsumeEvent event) {
    if (event.getItem().getType() != Material.GOLDEN_APPLE) {
      return;
    }

    Player player = event.getPlayer();

    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5 * 20, 2), true);
    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120 * 20, 0), true);
  }
}
