package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class SpectatorChatCommand implements CommandProvider {

  private final CustomCraftUhcGame game;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "spectatorchat",
        "toggles spectator chat",
        "spectatorchat",
        CustomCraftUhcIdentifiers.SPECTATOR_CHAT_PERMISSION,
        "specchat"
    ).noArgsHandler(context -> {
      Player player = context.getSenderAsPlayer();

      if (!this.game.isPlayer(player)) {
        if (this.game.getSpectatorChatManager().isDeadNotInSpectatorChat(player)) {
          this.game.getSpectatorChatManager().removeDeadNotInSpectatorChat(player);
          TextController.send(
              player,
              TextType.SUCCESS,
              "You are <h>no longer</h> in spectator chat."
          );
        } else {
          this.game.getSpectatorChatManager().addDeadNotInSpectatorChat(player);
          TextController.send(
              player,
              TextType.SUCCESS,
              "You are <h>now</h> in spectator chat."
          );
        }
      } else {
        if (this.game.getSpectatorChatManager().isAliveInSpectatorChat(player)) {
          this.game.getSpectatorChatManager().removeAliveInSpectatorChat(player); // removing
          // from normal
          TextController.send(
              player,
              TextType.SUCCESS,
              "You are <h>no longer</h> in spectator chat."
          );
        } else {
          this.game.getSpectatorChatManager().addAliveInSpectatorChat(player); // adding into normal chat
          TextController.send(
              player,
              TextType.SUCCESS,
              "You are <h>now</h> in spectator chat."
          );
        }
      }


    }, true);
  }
}
