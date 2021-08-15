package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.command.argument.StringArgument;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.core.game.state.GameState.TypeRegistry;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import java.util.Locale;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class SetStateCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "setstate",
        "sets game state",
        "setstate",
        CustomCraftUhcIdentifiers.SET_STATE_PERMISSION
    ).withSubCommand()
        .argument(StringArgument.of("state"))
        .handler(context -> {
          String state = context.getArgument("state");
          state = state.toLowerCase(Locale.ROOT);
          switch (state) {
            case "pvp":
              this.game.setGameState(TypeRegistry.PVP);
              break;
            case "deathmatch":
            case "dm":
              this.game.setGameState(TypeRegistry.DEATHMATCH);
              break;
            case "ended":
              this.game.setGameState(TypeRegistry.ENDED);
              break;
            case "waiting":
              this.game.setGameState(TypeRegistry.WAITING);
              break;
            case "countdown":
              this.game.setGameState(TypeRegistry.COUNTDOWN);
              break;
            case "grace":
            case "grace_period":
              this.game.setGameState(TypeRegistry.GRACE_PERIOD);
              break;
            default:
              TextController.send(
                  context.getCommandSender(),
                  TextType.ERROR,
                  "Could not set state to '<h>{0}</h>'.",
                  state
              );
              return;
          }

          TextController.send(
              context.getCommandSender(),
              TextType.SUCCESS,
              "State set to '<h>{0}</h>'.",
              state
          );
        }).finished();
  }
}
