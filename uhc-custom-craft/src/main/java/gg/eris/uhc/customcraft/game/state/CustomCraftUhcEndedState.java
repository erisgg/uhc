package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.scoreboard.CommonsScoreboard;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.game.state.AbstractEndedGameState;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.github.paperspigot.Title;

public final class CustomCraftUhcEndedState extends AbstractEndedGameState<CustomCraftUhcPlayer,
    CustomCraftUhcGame> {

  private static final Identifier SCOREBOARD_IDENTIFIER =
      CustomCraftUhcIdentifiers.SCOREBOARD_ID.id("ended");

  private final CommonsScoreboard scoreboard;

  private CustomCraftUhcPlayer winner;

  public CustomCraftUhcEndedState(CustomCraftUhcGame game) {
    super(game);

    this.scoreboard =
        game.getPlugin().getCommons().getScoreboardController()
            .newScoreboard(SCOREBOARD_IDENTIFIER);
    this.scoreboard
        .setTitle((player, ticks) -> CC.YELLOW.bold() + "Eris " + CC.GOLD.bold() + "UHC");
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Winner: " + CC.YELLOW + ((this.winner == player) ? "You!" :
            (this.winner == null) ? "No one?" : this.winner.getName()),
        1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(
        (player, ticks) -> CC.GRAY + "Kills: " + CC.YELLOW
            + ((CustomCraftUhcPlayer) player).getGameKills(), 1);
    this.scoreboard.addLine("");
    this.scoreboard.addLine(CC.YELLOW + "Play @ eris.gg");
  }

  @Override
  public void onStart() {
    this.winner = this.game.getWinner();
    this.winner.won();

    Title title = new Title(
        CC.DARK_GREEN + this.winner.getName() + CC.GREEN.bold() + " HAS WON!",
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
            CC.GREEN + "(+" + Text.formatInt(coins) + " coins)",
            20,
            40,
            20
        ));
        TextController.send(
            player,
            TextType.INFORMATION,
            "You have <h>won</h>! (+<h>{0}</h> coins)",
            Text.formatInt(coins)
        );
        PlayerUtil.playSound(player, Sound.LEVEL_UP);
      }
    }

    this.scoreboard.addAllPlayers();
  }

  @Override
  public void onEnd() {
    this.scoreboard.removeAllPlayers();
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

  @EventHandler
  public void onEntitySpawn(EntitySpawnEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onItemPickup(PlayerPickupItemEvent event) {
    event.setCancelled(true);
  }

}
