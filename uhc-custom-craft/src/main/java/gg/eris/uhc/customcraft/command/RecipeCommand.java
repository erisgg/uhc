package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.menu.recipe.RecipeBookMenuViewer;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class RecipeCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "recipe",
        "opens recipe book",
        "recipe",
        CustomCraftUhcIdentifiers.RECIPE_PERMISSION
    ).noArgsHandler(context -> {
      this.game.getRecipeBookMenu().openMenu(new RecipeBookMenuViewer(context.getSenderAsPlayer()));
    }, true);
  }
}
