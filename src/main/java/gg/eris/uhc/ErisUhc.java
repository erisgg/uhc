package gg.eris.uhc;

import gg.eris.commons.bukkit.ErisBukkitCommons;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.text.TextController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ErisUhc extends JavaPlugin {

  @Override
  public void onEnable() {
    ErisBukkitCommons commons = Bukkit.getServicesManager()
        .getRegistration(ErisBukkitCommons.class).getProvider();

    TextController textController = commons.getTextController();
    CommandManager commandManager = commons.getCommandManager();
    commandManager.registerCommand(
        commandManager.newCommandBuilder("start", "UHC start command", "start", "fs")
            .noArgsHandler(context -> {
              textController.sendMessage(context.getCommandSender(), "hello test yes");
            })
    );
  }

}
