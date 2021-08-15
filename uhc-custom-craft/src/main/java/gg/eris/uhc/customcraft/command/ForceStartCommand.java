package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.bag.TrinketBag;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ForceStartCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  private static final Set<Type> TYPES = Set.of(
      TypeRegistry.WAITING,
      TypeRegistry.COUNTDOWN
  );

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "forcestart",
        "force starts the game",
        "forcestart",
        CustomCraftUhcIdentifiers.FORCE_START_PERMISSION,
        "fs"
    ).noArgsHandler(context -> {
      if (!TYPES.contains(this.game.getGameState().getType())) {
        TextController.send(
            context.getSenderAsPlayer(),
            TextType.ERROR,
            "You cannot use force start right now."
        );
        return;
      }

      this.game.setGameState(TypeRegistry.STARTING);
      TextController.broadcastToServer(
          TextType.INFORMATION,
          "The game has been force started."
      );
    });
  }

}