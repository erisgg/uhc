package gg.eris.uhc.customcraft;

import gg.eris.uhc.core.UhcModule;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.DemigodSwordCraft;
import gg.eris.uhc.customcraft.craft.vocation.duelist.craft.SoulThirsterCraft;
import gg.eris.uhc.customcraft.craft.vocation.enchanter.craft.ModularWand;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
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
    Bukkit.getPluginManager().registerEvents(new DemigodSwordCraft(), this.plugin);
    Bukkit.getPluginManager().registerEvents(new SoulThirsterCraft(), this.plugin);
    Bukkit.getPluginManager().registerEvents(new ModularWand(), this.plugin);
  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected CustomCraftUhcGame createGame() {
    return new CustomCraftUhcGame(this.plugin, this);
  }

}
