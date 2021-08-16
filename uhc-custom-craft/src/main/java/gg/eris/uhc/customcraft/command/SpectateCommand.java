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
import java.util.Set;
import javax.naming.CompositeName;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpectateCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  private static final Set<Type> IN_GAME = Set.of(
      TypeRegistry.GRACE_PERIOD,
      TypeRegistry.PVP,
      TypeRegistry.DEATHMATCH
  );

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
      } else if (type == TypeRegistry.ENDED || type == TypeRegistry.STARTING) {
        TextController.send(
            context.getSenderAsPlayer(),
            TextType.ERROR,
            "You cannot do that right now."
        );
        return;
      }



      if (type == TypeRegistry.WAITING || type == TypeRegistry.COUNTDOWN) {
        this.game.removePlayer(this.game.getPlayer(context.getSenderAsPlayer()));
      } else {
        this.game.killPlayer(this.game.getPlayer(context.getSenderAsPlayer()), null, null);
      }

      TextController.send(
          context.getSenderAsPlayer(),
          TextType.SUCCESS,
          "You are now a <h>spectator</h>."
      );
    });
  }

}
