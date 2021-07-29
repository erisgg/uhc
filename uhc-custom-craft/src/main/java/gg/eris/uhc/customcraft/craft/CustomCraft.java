package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class CustomCraft implements Identifiable, Listener {

  public static final IdentifierProvider IDENTIFIER_PROVIDER
      = new IdentifierProvider("custom_craft");

  private final Identifier identifier;

  public CustomCraft(String identifierValue) {
    this.identifier = IDENTIFIER_PROVIDER.id(identifierValue);
  }

  @Override
  public final Identifier getIdentifier() {
    return this.identifier;
  }

  public final int getLevel(CustomCraftUhcPlayer player) {
    return player.getUnlockLevel(getIdentifier());
  }

  public final ItemStack craft(CustomCraftUhcPlayer player) {
    int level = getLevel(player);
    player.incrementCraftCount(getIdentifier());
    return getItem(player, level);
  }

  public final boolean canCraft(CustomCraftUhcPlayer player) {
    int level = getLevel(player);
    return level > 0 && getMaxCrafts(level) > player.getTimesCrafted(getIdentifier());
  }

  protected abstract ItemStack getItem(CustomCraftUhcPlayer player, int level);

  public abstract int getMaxCrafts(int level);

}
