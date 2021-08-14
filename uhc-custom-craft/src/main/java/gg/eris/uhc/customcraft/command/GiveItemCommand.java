package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.command.argument.StringArgument;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.Craftable;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;

public final class GiveItemCommand implements CommandProvider {


  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "giveitem",
        "gives player an item",
        "giveitem <item>",
        CustomCraftUhcIdentifiers.GIVEITEM_PERMISSION
    ).withSubCommand()
        .asPlayerOnly()
        .argument(StringArgument.of("item"))
        .handler(context -> {
          String item = context.getArgument("item");

          for (Vocation vocation : Vocation.values()) {
            for (VocationUnlockable unlockable : vocation.getRegistry().values()) {
              Identifier id = CustomCraftUhcIdentifiers.UNLOCKABLE.id(item);
              if (id.equals(unlockable.getIdentifier()) && unlockable instanceof Craftable) {
                context.getSenderAsPlayer().getInventory()
                    .addItem(((Craftable) unlockable).getRecipe().getResult());
                TextController.send(
                    context.getSenderAsPlayer(),
                    TextType.SUCCESS,
                    "Given 1x the output of the <h>{0}</h> craft.",
                    unlockable.getName()
                );
                return;
              }
            }
          }

          TextController.send(
              context.getSenderAsPlayer(),
              TextType.ERROR,
              "Could not find item with name <h>{0}</h>.",
              item
          );
        }).finished();
  }

}
