package gg.eris.uhc.core.event;

import gg.eris.commons.bukkit.util.ExpUtil;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public final class UhcPlayerDeathEvent extends UhcEvent {

  private static final HandlerList HANDLERS = new HandlerList();

  @Getter
  private final EntityDamageEvent handle;

  @Getter
  private final UhcPlayer killed;

  @Getter
  private final UhcPlayer killer;

  @Getter
  private final int tick;

  @Getter
  private final List<ItemStack> drops;

  @Getter
  @Setter
  private int exp;

  public UhcPlayerDeathEvent(UhcGame<?> game, EntityDamageEvent handle, UhcPlayer killed,
      UhcPlayer killer, List<ItemStack> drops) {
    super(game);
    this.killed = killed;
    this.killer = killer;
    this.handle = handle;
    this.tick = game.getTick();
    this.drops = new ArrayList<>(drops);
    this.exp = (int) (ExpUtil.getExp(killed.getHandle()) / 2.5);
  }

  @Override
  public HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

}
