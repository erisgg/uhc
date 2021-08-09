package gg.eris.uhc.customcraft.game.state;

import com.google.common.collect.Sets;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.game.Scatterer;
import gg.eris.uhc.core.game.state.AbstractStartingGameState;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagItem;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import org.bukkit.GameMode;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.potion.PotionEffectType;

public final class CustomCraftUhcStartingState extends
    AbstractStartingGameState<CustomCraftUhcPlayer, CustomCraftUhcGame> {

  private final Scatterer scatterer;

  public CustomCraftUhcStartingState(CustomCraftUhcGame game) {
    super(game);
    this.scatterer = new Scatterer(game, 8,
        () -> this.game.setGameState(TypeRegistry.GRACE_PERIOD));
  }

  @Override
  public void onStart() {
    // Setting players
    this.game.setPlayers();

    // Setting borders for world and nether
    WorldBorder border = this.game.getWorld().getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(this.game.getSettings().getBorderRadius() * 2);
    border = this.game.getNether().getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(this.game.getSettings().getBorderRadius() * 2);
    border = this.game.getDeathmatch().getWorldBorder();
    border.setCenter(0.0, 0.0);
    border.setSize(this.game.getSettings().getDeathmatchBorderRadius() * 2);

    this.scatterer.scatter();

    for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
      Player handle = player.getHandle();
      if (handle != null) {
        PlayerUtil.resetPlayer(handle);
        handle.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(Integer.MAX_VALUE, 9));
        handle.addPotionEffect(PotionEffectType.SLOW.createEffect(Integer.MAX_VALUE, 9));
      }
    }
  }

  @Override
  public void onEnd() {
    // Players that log off in the pregame
    Set<CustomCraftUhcPlayer> logged = Sets.newHashSet();

    for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
      Player handle = player.getHandle();
      if (handle == null) {
        logged.add(player);
      } else {
        player.playedGame();
        PlayerUtil.resetPlayer(player.getHandle());
        player.getHandle().setGameMode(GameMode.SURVIVAL);
        player.getHandle().setMaxHealth(this.game.getSettings().getMaxHealth());
        player.getHandle().setHealth(player.getHandle().getMaxHealth());
        TrinketBagItem.giveBag(player.getHandle());
      }
    }

    for (CustomCraftUhcPlayer logger : logged) {
      this.game.removePlayer(logger);
    }

    this.game.getPlugin().getCommons().getChatController()
        .setFormat("<col=gold>[{0}" + CustomCraftUhcIdentifiers.STAR
                + "]</col> {1}[{2}]</col> {3}{4}<col=red>[{5}]</col>: <raw>{6}</raw></col>",
            (player, chatMessage) -> ("" + ((CustomCraftUhcPlayer) player).getStar()),
            (player, chatMessage) -> "<col=" + player.getPriorityRank().getColor().getId() + ">",
            (player, chatMessage) -> player.getPriorityRank().getRawDisplay(),
            (player, chatMessage) -> player.getPriorityRank().isWhiteChat() ?
                "<col=white>" : "<col=gray>",
            (player, chatMessage) -> player.getName(),
            (player, chatMessage) -> "" + ((CustomCraftUhcPlayer) player).getGameKills(),
            (player, chatMessage) -> chatMessage);
  }

  @Override
  public void onTick(int tick) {

  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    event.setResult(Result.KICK_FULL);
    event.setKickMessage(CC.RED + "The game has started!");
  }


}
