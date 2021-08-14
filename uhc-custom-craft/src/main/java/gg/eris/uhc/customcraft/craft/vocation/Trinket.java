package gg.eris.uhc.customcraft.craft.vocation;

import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Locale;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Trinket extends VocationUnlockable implements Craftable, Listener {

  private final ItemStack item;
  @Getter
  private final CraftableInfo info;

  public Trinket(String identifierValue, CraftableInfo info) {
    super(identifierValue);
    this.info = info;
    this.item = NBTUtil
        .setNbtData(info.buildTrinket(), CustomCraftUhcIdentifiers.VOCATION_CRAFT_NBT_KEY,
            this.getIdentifier().getValue());
  }

  public final ItemStack getItem() {
    return this.item;
  }

  public void onAdd(CustomCraftUhcPlayer player) {

  }

  public void onRemove(CustomCraftUhcPlayer player) {

  }

  public boolean canRemove(CustomCraftUhcPlayer player) {
    return true;
  }
}
