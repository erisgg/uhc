package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.tablist.TablistController;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Pair;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.UUID;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public final class GameDamageListener extends GameStateListener {

  private final CustomCraftUhcGame game;

  public GameDamageListener(CustomCraftUhcGame game) {
    this.game = game;
  }

  @Override
  protected void onEnable(GameState<?, ?> state) {
    TablistController tablistController = this.game.getPlugin().getCommons().getTablistController();
    tablistController.setDisplayNameFunction((player, viewer) -> {
      if (player == viewer) {
        if (this.game.isPlayer(player.getUniqueId())) {
          return CC.GREEN + player.getNicknameProfile().getDisplayName();
        } else {
          return CC.GRAY.italic() + player.getNicknameProfile().getDisplayName();
        }
      } else {
        if (this.game.isPlayer(player.getUniqueId())) {
          return CC.RED + player.getNicknameProfile().getDisplayName();
        } else {
          return null;
        }
      }
    });

    tablistController.setOrderingComparator((o1, o2) -> {
      if (GameDamageListener.this.game.isPlayer(o1.getUniqueId())) {
        if (GameDamageListener.this.game.isPlayer(o2.getUniqueId())) {
          return o1.getName().compareTo(o2.getName());
        } else {
          return -1;
        }
      } else {
        if (GameDamageListener.this.game.isPlayer(o2.getUniqueId())) {
          return 1;
        } else {
          return o1.getName().compareTo(o2.getName());
        }
      }
    });
  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    CustomCraftUhcPlayer damaged = this.game.getPlayer((Player) event.getEntity());
    if (damaged == null) {
      event.setCancelled(true);
      return;
    }

    Player damagedHandle = damaged.getHandle();
    Player killerHandle = null;
    CustomCraftUhcPlayer killer = null;

    if (damagedHandle.getHealth() - event.getFinalDamage() <= 0) {
      if (event instanceof EntityDamageByEntityEvent) {
        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
        if (entityDamageByEntityEvent.getDamager().getType() == EntityType.PLAYER) {
          killerHandle = (Player) entityDamageByEntityEvent.getDamager();
          killer = this.game.getPlayer(killerHandle);
        } else if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
          Projectile projectile = (Projectile) entityDamageByEntityEvent.getDamager();
          if (projectile.getShooter() instanceof Player) {
            killerHandle = (Player) projectile.getShooter();
            killer = this.game.getPlayer(killerHandle);
          }
        } else {
          Pair<UUID, Long> lastAttacker = damaged.getLastAttacker();
          if (lastAttacker != null) {
            CustomCraftUhcPlayer attacker = this.game.getPlayer(lastAttacker.getKey());
            if (attacker != null) {
              if (lastAttacker.getValue()
                  + this.game.getSettings().getAttackCreditDuration() * 1000L
                  < System.currentTimeMillis()) {
                killer = attacker;
                killerHandle = killer.getHandle();
              }
            }
          }
        }
      }

      // Killer must be online to get the rewards (maybe change? but cba! LOL!)
      if (killerHandle == null && killer != null) {
        killer = null;
      }

      event.setCancelled(true);
      this.game.killPlayer(damaged, killer);

      // Giving kill coins
      if (killer != null) {
        int coinsPerKill = killer.giveCoins(this.game.getSettings().getCoinsPerKill());
        TextController.send(
            killerHandle,
            TextType.INFORMATION,
            "You have killed <h>{0}</h> (+<h>{1}</h> coins)",
            damaged.getName(),
            Text.formatInt(coinsPerKill)
        );
      }

      // Giving survival coins
      int playerSize = this.game.getPlayers().size();
      int allocate = this.game.getSettings().getCoinsPerSurvive().getOrDefault(playerSize, 0);
      if (allocate > 0) {
        for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
          if (player.getHandle() != null) {
            int coins = player.giveCoins(allocate);
            TextController.send(
                player.getHandle(),
                TextType.INFORMATION,
                "You have survived to be in the top <h>{0}</h> players (+<h>{1}</h> coins)",
                playerSize,
                Text.formatInt(coins)
            );
          }
        }
      }
    } else {
      if (event instanceof EntityDamageByEntityEvent) {
        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
        if (entityDamageByEntityEvent.getDamager().getType() == EntityType.PLAYER) {
          Player damager = (Player) entityDamageByEntityEvent.getDamager();
          if (this.game.isPlayer(damager)) {
            damaged.setLastAttacker((Player) entityDamageByEntityEvent.getDamager());
          } else {
            event.setCancelled(true);
          }
        }
      }
    }
  }

}
