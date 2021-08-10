package gg.eris.uhc.customcraft;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.command.GiveCoinsCommand;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;

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
    this.plugin.getCommons().getCommandManager()
        .registerCommands(new GiveCoinsCommand(this.plugin.getCommons()
            .getErisPlayerManager()));
  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected CustomCraftUhcGame createGame() {
    return new CustomCraftUhcGame(this.plugin, this);
  }

}
