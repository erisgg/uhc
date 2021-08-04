package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.game.state.AbstractEndedGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.github.paperspigot.Title;

public final class CustomCraftUhcEndedState extends AbstractEndedGameState<CustomCraftUhcPlayer,
    CustomCraftUhcGame> {

  public CustomCraftUhcEndedState(CustomCraftUhcGame game) {
    super(game);
  }

  @Override
  public void onStart() {
    CustomCraftUhcPlayer winner = this.game.getWinner();
    winner.won();

    Title title = new Title(
        CC.DARK_GREEN + winner.getName() + CC.GREEN.bold() + " HAS WON!",
        CC.RED + "Better luck next time :(",
        20,
        40,
        20
    );


    for (Player player : Bukkit.getOnlinePlayers()) {
      if (player != winner.getHandle()) {
        player.sendTitle(title);
      } else {
        // Only give coins if online
        int coins = winner.giveCoins(this.game.getSettings().getCoinsPerWin());
        player.sendTitle(new Title(
            CC.GREEN.bold() + "YOU WIN!",
            CC.GREEN + "(+" + coins + " coins)",
            20,
            40,
            20
        ));
        PlayerUtil.playSound(player, Sound.LEVEL_UP);
      }
    }
  }

  @Override
  public void onEnd() {

  }

  @Override
  public void onTick(int tick) {
    if (tick / 20 >= this.game.getSettings().getPostGameDelay()) {
      this.game.shutdown();
    }
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent event) {
    event.setResult(Result.KICK_OTHER);
    event.setKickMessage(CC.RED.bold() + "The game has finished.");
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    event.setCancelled(true);
  }

}
