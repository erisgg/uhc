package gg.eris.uhc.customcraft.game.listener;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Pair;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.core.game.state.listener.MultiStateListener;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class GameListener extends MultiStateListener {

  private final CustomCraftUhcGame game;
  private final ErisPlayerManager erisPlayerManager;

  public GameListener(CustomCraftUhcGame game) {
    this.game = game;
    this.erisPlayerManager = game.getPlugin().getCommons().getErisPlayerManager();
  }

  @Override
  protected Set<Type> getApplicableTypes() {
    return Set.of(
        TypeRegistry.GRACE_PERIOD,
        TypeRegistry.PVP,
        TypeRegistry.DEATHMATCH
    );
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {
    this.game.getPlugin().getCommons().getTablistController().setDisplayNameFunction
        ((player, viewer) -> player == viewer ? CC.GREEN + player.getName() : CC.RED + player.getName());
  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    CustomCraftUhcPlayer damaged = this.erisPlayerManager.getPlayer((Player) event.getEntity());
    Player damagedHandle = damaged.getHandle();

    if (damagedHandle.getHealth() - event.getFinalDamage() <= 0) {
      Player killerHandle = damagedHandle.getKiller();
      CustomCraftUhcPlayer killer = null;
      if (killerHandle == null) {
        Pair<UUID, Long> lastAttacker = damaged.getLastAttacker();
        if (lastAttacker != null) {
          CustomCraftUhcPlayer attacker = this.erisPlayerManager.getPlayer(lastAttacker.getKey());
          if (attacker != null) {
            if (System.currentTimeMillis()
                + this.game.getSettings().getAttackCreditDuration() * 1000L < lastAttacker
                .getValue()) {
              killer = attacker;
            }
          }
        }
      } else {
        killer = this.erisPlayerManager.getPlayer(killerHandle);
      }

      // Killer must be online to get the rewards (maybe change? but cba! LOL!)
      if (killerHandle == null && killer != null) {
        killerHandle = killer.getHandle();
        if (killerHandle == null) {
          killer = null;
        }
      }

      event.setCancelled(true);
      this.game.killPlayer(damaged, killer);

      // Giving custom craft UHC coins
      if (killer != null) {
        int coinsPerKill = killer.giveCoins(this.game.getSettings().getCoinsPerKill());
        TextController.send(
            killerHandle,
            TextType.INFORMATION,
            "You have killed <h>{0}</h> (+<h>{1}</h> coins)",
            damaged.getName(),
            coinsPerKill
        );
      }

    } else {
      if (event instanceof EntityDamageByEntityEvent) {
        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
        if (entityDamageByEntityEvent.getDamager().getType() == EntityType.PLAYER) {
          damaged.setLastAttacker((Player) entityDamageByEntityEvent.getDamager());
        }
      }
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    // Handle logger
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    // Handle logger
  }

}
