package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Time;
import gg.eris.uhc.core.game.state.AbstractPvpGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcPlayer;
import java.util.concurrent.TimeUnit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

public final class CustomCraftUhcPvpState extends
    AbstractPvpGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private static final int DEATHMATCH_PLAYER_REQUIREMENT = 12;
  private static final int DEATHMATCH_TIME_REQUIREMENT = 30 * 60;
  private static final int DEATHMATCH_COUNTDOWN_TIME = 10 * 60;

  private int pvpStateTime;
  private int deathmatchCountdown;

  public CustomCraftUhcPvpState(CustomCraftUhcGame game) {
    super(game);
  }

  @Override
  public void onStart() {
    this.pvpStateTime = 0;
    this.deathmatchCountdown = -1;
  }

  @Override
  public void onEnd() {

  }

  @Override
  public void onTick(int tick) {
    if (tick % 20 == 0) {
      this.pvpStateTime++;

      if (this.deathmatchCountdown != -1) {
        boolean broadcast = false;
        if (this.deathmatchCountdown > 0 && this.deathmatchCountdown % 60 == 0) {
          broadcast = true;
        } else {
          switch (this.deathmatchCountdown) {
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
                  "Deathmatch is $$starting$$.",
                  TextType.INFORMATION
              ));
              this.game.setGameState(TypeRegistry.DEATHMATCH);
              return;
          }
        }

        if (broadcast) {
          TextController.broadcast(TextController.builder(
              "Deathmatch is $$starting$$ in $${0}$$",
              TextType.INFORMATION,
              Time.toLongDisplayTime(this.deathmatchCountdown, TimeUnit.SECONDS)
          ));
        }
      }

      checkDeathmatch();
    }

  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDeath(EntityDeathEvent event) {
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    Player killed = (Player) event.getEntity();
    Player killer = event.getEntity().getKiller();
    if (killer == null) {
      TextController.broadcast(TextController.builder(
          "$${0}$$ has died.",
          TextType.INFORMATION,
          killed.getName()
      ));
    } else {
      TextController.broadcast(TextController.builder(
          "$${0} has been killed by {1}.",
          TextType.INFORMATION,
          killed.getName(), killer.getName()
      ));
    }

    this.game.removePlayer(killed);

    checkDeathmatch();
  }

  private void checkDeathmatch() {
    if (this.deathmatchCountdown != -1) {
      return;
    }

    if (this.game.getPlayers().size() <= DEATHMATCH_PLAYER_REQUIREMENT) {
      this.deathmatchCountdown = DEATHMATCH_COUNTDOWN_TIME;
      TextController.broadcast(
          TextController.builder(
              "Deathmatch will start in $${0}$$ as there is only $${1}$$ players remaining.",
              TextType.INFORMATION,
              Time.toLongDisplayTime(this.deathmatchCountdown),
              this.game.getPlayers().size()
          )
      );
    } else if (this.pvpStateTime >= DEATHMATCH_TIME_REQUIREMENT) {
      this.deathmatchCountdown = DEATHMATCH_COUNTDOWN_TIME;
      TextController.broadcast(
          TextController.builder(
              "Deathmatch will start in $${0}$$.",
              TextType.INFORMATION,
              Time.toLongDisplayTime(this.deathmatchCountdown)
          )
      );
    }
  }
}
