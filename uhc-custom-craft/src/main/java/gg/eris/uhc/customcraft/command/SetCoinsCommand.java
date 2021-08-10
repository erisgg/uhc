package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.command.argument.IntegerArgument;
import gg.eris.commons.bukkit.command.argument.PlayerArgument;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class SetCoinsCommand implements CommandProvider {

  private final ErisPlayerManager erisPlayerManager;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "setcoins",
        "sets coins of a player",
        "givecoins <player> <amount>",
        CustomCraftUhcIdentifiers.GIVECOINS_PERMISSION
    ).withSubCommand()
        .argument(PlayerArgument.of("player"))
        .argument(IntegerArgument.of("amount"))
        .handler(context -> {
          Player player = context.getArgument("player");
          if (player == null) {
            TextController.send(
                context.getCommandSender(),
                TextType.ERROR,
                "Player <h>{0}</h> is not online.",
                context.getRawArgs()[0]
            );
            return;
          }

          int amount = context.getArgument("amount");
          if (amount <= 0) {
            TextController.send(
                context.getCommandSender(),
                TextType.ERROR,
                "Amount must be <h>greater than zero</h>."
            );
            return;
          }

          CustomCraftUhcPlayer customCraftUhcPlayer = this.erisPlayerManager.getPlayer(player);
          customCraftUhcPlayer.setCoins(amount);
          TextController.send(
              context.getCommandSender(),
              TextType.SUCCESS,
              "Given <h>{0}</h> +<h>{1}</h> coins.",
              player.getName(),
              Text.formatInt(amount)
          );
        }).finished();
  }
}
