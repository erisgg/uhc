package gg.eris.uhc.customcraft.craft;

import gg.eris.uhc.customcraft.craft.Unlockable;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;

public abstract class Craft extends Unlockable implements Craftable, Listener {

  public Craft(String identifierValue) {
    super(identifierValue);
  }

  public abstract Vocation getVocation();

  public abstract int getCraftableAmount();

  public abstract int getPrestiageCraftableAmount();

}
