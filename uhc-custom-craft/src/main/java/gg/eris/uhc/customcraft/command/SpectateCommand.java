package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.core.game.state.GameState.Type;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class SpectateCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "spectate",
        "makes you a spectator",
        "spec",
        CustomCraftUhcIdentifiers.SPECTATE_PERMISSION
    ).noArgsHandler(context -> {
      Type type = this.game.getGameState().getType();

      if (!this.game.isPlayer(context.getSenderAsPlayer())) {
        TextController.send(
            context.getSenderAsPlayer(),
            TextType.ERROR,
            "You are already a spectator."
        );
        return;
      } else if (type != TypeRegistry.GRACE_PERIOD && type != TypeRegistry.PVP
          && type != TypeRegistry.DEATHMATCH) {
        TextController.send(
            context.getSenderAsPlayer(),
            TextType.ERROR,
            "You cannot do that right now."
        );
        return;
      }

      this.game.killPlayer(this.game.getPlayer(context.getSenderAsPlayer()), null, null);

      TextController.send(
          context.getSenderAsPlayer(),
          TextType.SUCCESS,
          "You are now a <h>spectator</h>."
      );
    });
  }

}
