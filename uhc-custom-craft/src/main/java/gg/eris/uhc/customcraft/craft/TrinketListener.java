package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public final class TrinketListener extends GameStateListener {

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
    }
  }

}
