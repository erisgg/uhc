package gg.eris.uhc.customcraft.game.listener.game;

import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@RequiredArgsConstructor
public final class ScoreboardHeartsListener extends GameStateListener {

  private final CustomCraftUhcGame game;

  @Override
  protected void onEnable(GameState<?, ?> state) {
    addTask(() -> Bukkit.getScheduler().runTaskTimer(this.game.getPlugin(), () -> {
      for (Player online : Bukkit.getOnlinePlayers()) {
        for (Player other : Bukkit.getOnlinePlayers()) {
          Scoreboard scoreboard = other.getScoreboard();
          Objective objective;
          if (scoreboard.getObjective("health") == null) {
            objective = scoreboard.registerNewObjective("health", "dummy");
            objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
          } else {
            objective = scoreboard.getObjective("health");
          }

          float absorptionHearts = PlayerUtil.getHandle(online).getAbsorptionHearts();
          objective.getScore(online).setScore((int) (online.getHealth() + absorptionHearts));
        }
      }
    }, 0L, 5L).getTaskId());
  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }
}
