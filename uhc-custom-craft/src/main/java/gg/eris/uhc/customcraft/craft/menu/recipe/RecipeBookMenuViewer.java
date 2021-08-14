package gg.eris.uhc.customcraft.craft.menu.recipe;

import gg.eris.commons.bukkit.menu.MenuViewer;
import gg.eris.uhc.customcraft.craft.Craftable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public final class RecipeBookMenuViewer extends MenuViewer {

  @Getter
  @Setter
  private int page;

  @Getter
  @Setter
  private Craftable craftable;

  public RecipeBookMenuViewer(Player player) {
    super(player);
    this.page = 0;
  }

}
