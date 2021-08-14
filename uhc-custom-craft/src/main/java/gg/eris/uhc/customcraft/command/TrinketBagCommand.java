package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.bag.TrinketBag;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TrinketBagCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  private static final Set<Type> TYPES = Set.of(
      TypeRegistry.GRACE_PERIOD,
      TypeRegistry.PVP,
      TypeRegistry.DEATHMATCH
  );

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "trinketbag",
        "opens trinket bag",
        "trinketbag",
        CustomCraftUhcIdentifiers.RECIPE_PERMISSION,
        "bag"
    ).noArgsHandler(context -> {
      CustomCraftUhcPlayer player = this.game.getPlayer(context.getSenderAsPlayer().getPlayer());
      if (player == null || !TYPES.contains(game.getGameState().getType())) {
        TextController.send(
            context.getSenderAsPlayer(),
            TextType.ERROR,
            "You cannot open your trinket bag right now."
        );
        return;
      }

      TrinketBag.openBag(player);
    }, true);
  }

}
