package gg.eris.uhc.core.event;

import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class UhcPlayerDeathEvent extends UhcEvent {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter
  private final UhcPlayer killed;

  @Getter
  private final UhcPlayer killer;

  @Getter
  private final int tick;

  @Getter
  private final List<ItemStack> drops;

  public UhcPlayerDeathEvent(UhcGame<?> game, UhcPlayer killed, UhcPlayer killer,
      List<ItemStack> drops) {
    super(game);
    this.killed = killed;
    this.killer = killer;
    this.tick = game.getTick();
    this.drops = new ArrayList<>(drops);
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

}
