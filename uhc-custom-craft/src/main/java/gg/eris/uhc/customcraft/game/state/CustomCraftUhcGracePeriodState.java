package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractGracePeriodGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class CustomCraftUhcGracePeriodState extends
    AbstractGracePeriodGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final int GRACE_PERIOD_DURATION = 60 * 10; // 10 minutes

  private int duration;

  public CustomCraftUhcGracePeriodState(CustomCraftUhcGame game) {
    super(game);
  }

  @Override
  public void onStart() {
    this.game.getModule().disableLobby();
    this.duration = GRACE_PERIOD_DURATION;
  }

  @Override
  public void onEnd() {

  }

  @Override
  public void onTick(int tick) {
    if (tick % 20 != 0) {
      return;
    }

    boolean broadcast = false;
    switch (--this.duration) {
      case 300:
      case 180:
      case 60:
      case 30:
      case 10:
      case 5:
      case 4:
      case 3:
      case 2:
      case 1:
        broadcast = true;
        break;
      case 0:
        TextController.broadcast(TextController.builder(
            "The grace period has $$ended$$! PvP is now $$enabled$$.",
            TextType.INFORMATION
        ));
        this.game.setGameState(TypeRegistry.PVP);
        return;
    }

    if (broadcast) {
      TextController.broadcast(TextController.builder(
          "The grace period will $$end$$ in $${0}$$.",
          TextType.INFORMATION,
          Time.toLongDisplayTime(this.duration, TimeUnit.SECONDS)
      ));
    }
  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager().getType() == EntityType.PLAYER
        && event.getEntityType() == EntityType.PLAYER) {
      event.setCancelled(true);
      TextController.send(TextController.builder(
          "You cannot attack players until the grace period is $$over$$ in $${0}$$.",
          TextType.ERROR,
          Time.toShortDisplayTime(this.duration, TimeUnit.SECONDS)
      ), (Player) event.getDamager());
    }
  }
}
