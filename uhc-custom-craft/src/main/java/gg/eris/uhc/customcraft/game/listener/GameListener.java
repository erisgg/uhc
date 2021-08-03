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
import gg.eris.uhc.customcraft.craft.menu.CraftingMenuLogic;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
        ((player, viewer) -> player == viewer ? CC.GREEN + player.getName()
            : CC.RED + player.getName());
  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onEntityDamage(EntityDamageEvent event) {
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    CustomCraftUhcPlayer damaged = this.erisPlayerManager.getPlayer((Player) event.getEntity());
    Player damagedHandle = damaged.getHandle();
    Player killerHandle = null;
    CustomCraftUhcPlayer killer = null;

    if (damagedHandle.getHealth() - event.getFinalDamage() <= 0) {
      if (event instanceof EntityDamageByEntityEvent) {
        EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
        if (entityDamageByEntityEvent.getDamager().getType() == EntityType.PLAYER) {
          killerHandle = (Player) entityDamageByEntityEvent.getDamager();
          killer = this.erisPlayerManager.getPlayer(killerHandle);
        } else if (entityDamageByEntityEvent.getDamager() instanceof Projectile) {
          Projectile projectile = (Projectile) entityDamageByEntityEvent.getDamager();
          if (projectile.getShooter() instanceof Player) {
            killerHandle = (Player) projectile.getShooter();
            killer = this.erisPlayerManager.getPlayer(killerHandle);
          }
        } else {
          Pair<UUID, Long> lastAttacker = damaged.getLastAttacker();
          if (lastAttacker != null) {
            CustomCraftUhcPlayer attacker = this.erisPlayerManager.getPlayer(lastAttacker.getKey());
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
        killerHandle = killer.getHandle();
        if (killerHandle == null) {
          killer = null;
        }
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
            coinsPerKill
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
                coins
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

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.hasBlock() && event.getClickedBlock().getType() == Material.WORKBENCH) {
      event.setCancelled(true);
      event.setUseInteractedBlock(Result.DENY);
      this.game.getCraftingMenu().openMenu(event.getPlayer());
    }
  }

  @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
  public void onInventoryClick(InventoryClickEvent event) {
    this.game.getCraftingMenu().handle(event);
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
