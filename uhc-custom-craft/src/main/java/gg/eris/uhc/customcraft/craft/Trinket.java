package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.commons.core.util.Text;
import java.util.List;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Trinket extends Unlockable implements Craftable, Listener {

  private final ItemStack item;
  @Getter
  private final CraftableInfo info;

  public Trinket(String identifierValue, CraftableInfo info) {
    super(identifierValue);
    this.info = info;
    this.item = NBTUtil.setNbtData(info.buildTrinket(), NBT_KEY, this.getIdentifier().toString());
  }

  public final ItemStack getItem() {
    return this.item;
  }

}
