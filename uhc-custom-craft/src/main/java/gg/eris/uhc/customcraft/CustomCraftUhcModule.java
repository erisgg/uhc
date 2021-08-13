package gg.eris.uhc.customcraft;

import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.command.GiveCoinsCommand;
import gg.eris.uhc.customcraft.command.GiveItemCommand;
import gg.eris.uhc.customcraft.command.SetCoinsCommand;
import gg.eris.uhc.customcraft.command.StatsCommand;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import lombok.Getter;
import org.bukkit.Bukkit;

public final class CustomCraftUhcModule extends UhcModule<CustomCraftUhcGame> {

  @Getter
  private final int port;

  public CustomCraftUhcModule(UhcPlugin plugin) throws IOException {
    super(plugin);

    Properties props = new Properties();
    props.load(new FileInputStream("server.properties"));
    this.port = Integer.parseInt(props.getProperty("server-port"));
  }

  @Override
  public void onEnable() {
    this.plugin.getCommons().getChatController()
        .setFormat("<col=gold>[{0}" + CustomCraftUhcIdentifiers.STAR
                + "]</col> {1}[{2}]</col> {3}{4}: <raw>{5}</raw></col>",
            (player, chatMessage) -> player.getNicknameProfile().isNicked() ?
                "" + 0 : ("" + ((CustomCraftUhcPlayer) player).getStar()),
            (player, chatMessage) -> "<col="
                + player.getNicknameProfile().getPriorityDisplayRank().getColor().getId() + ">",
            (player, chatMessage) -> player.getNicknameProfile().getPriorityDisplayRank()
                .getRawDisplay(),
            (player, chatMessage) ->
                player.getNicknameProfile().getPriorityDisplayRank().isWhiteChat() ?
                    "<col=white>" : "<col=gray>",
            (player, chatMessage) -> player.getDisplayName(),
            (player, chatMessage) -> chatMessage);

    this.plugin.getCommons().getRedisWrapper()
        .removeFromSet(CustomCraftUhcIdentifiers.LIVE_GAME_SET,
            "" + ((CustomCraftUhcModule) this.game.getModule()).getPort());

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

  @Override
  protected Set<CommandProvider> getCommands() {
    return Set.of(
        new SetCoinsCommand(this.plugin.getCommons().getErisPlayerManager()),
        new GiveCoinsCommand(this.plugin.getCommons().getErisPlayerManager()),
        new StatsCommand(this.plugin, this.plugin.getCommons().getErisPlayerManager()),
        new GiveItemCommand()
    );
  }
}
