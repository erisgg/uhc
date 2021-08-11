package gg.eris.uhc.customcraft;

import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.player.ErisPlayer;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.command.GiveCoinsCommand;
import gg.eris.uhc.customcraft.command.SetCoinsCommand;
import gg.eris.uhc.customcraft.command.StatsCommand;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Bukkit;

public final class CustomCraftUhcModule extends UhcModule<CustomCraftUhcGame> {

  public CustomCraftUhcModule(UhcPlugin plugin) {
    super(plugin);
  }

  @Override
  public void onEnable() {
    this.plugin.getCommons().getChatController()
        .setFormat("<col=gold>[{0}" + CustomCraftUhcIdentifiers.STAR
                + "]</col> {1}[{2}]</col> {3}{4}: <raw>{5}</raw></col>",
            (player, chatMessage) -> ("" + ((CustomCraftUhcPlayer) player).getStar()),
            (player, chatMessage) -> "<col=" + player.getPriorityRank().getColor().getId() + ">",
            (player, chatMessage) -> player.getPriorityRank().getRawDisplay(),
            (player, chatMessage) -> player.getPriorityRank().isWhiteChat() ?
                "<col=white>" : "<col=gray>",
            (player, chatMessage) -> player.getName(),
            (player, chatMessage) -> chatMessage);

    CommandManager commandManager = this.plugin.getCommons().getCommandManager();
    ErisPlayerManager erisPlayerManager = this.plugin.getCommons().getErisPlayerManager();
    commandManager.registerCommands(
        new GiveCoinsCommand(erisPlayerManager),
        new SetCoinsCommand(erisPlayerManager),
        new StatsCommand(this.plugin, erisPlayerManager)
    );

    if (!Vocation.validateRegistries()) {
      Bukkit.getServer().shutdown();
    }
  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected CustomCraftUhcGame createGame() {
    return new CustomCraftUhcGame(this.plugin, this);
  }

}
