package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class StrengthNerfListener extends GameStateListener {

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.HIGH)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() != EntityType.PLAYER) {
      return;
    }

    // 25% increased damage per level of strength
    Player attacker = (Player) event.getDamager();
    for (PotionEffect effect : attacker.getActivePotionEffects()) {
      if (effect.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
        double addedDamage = (effect.getAmplifier() + 1) * 3.0;
        double base = event.getDamage() - addedDamage;
        double increased = base + (base * (effect.getAmplifier() + 1) * 0.05);
        event.setDamage(increased);
        return;
      }
    }
  }

}
