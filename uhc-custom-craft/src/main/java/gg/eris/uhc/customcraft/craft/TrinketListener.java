package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

@RequiredArgsConstructor
public final class TrinketListener extends GameStateListener {

  private final CustomCraftUhcGame game;

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    Identifier identifier = Trinket.getIdentifierFromItemStack(event.getItem());
    VocationUnlockable newUnlockable = Vocation.getUnlockable(identifier);
    if (newUnlockable instanceof Trinket) {
      event.setCancelled(true);
      Bukkit.getScheduler().runTaskLater(this.game.getPlugin(),
          () -> event.getPlayer().updateInventory(), 2L);
    }
  }

  @EventHandler
  public void onInteract(PlayerItemConsumeEvent event) {
    Identifier identifier = Trinket.getIdentifierFromItemStack(event.getItem());
    VocationUnlockable newUnlockable = Vocation.getUnlockable(identifier);
    if (newUnlockable instanceof Trinket) {
      event.setCancelled(true);
      Bukkit.getScheduler().runTaskLater(this.game.getPlugin(),
          () -> event.getPlayer().updateInventory(), 2L);
    }
  }

}
