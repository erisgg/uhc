package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.game.Scatterer;
import gg.eris.uhc.core.game.state.AbstractStartingGameState;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
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
    this.game.setPlayers();
    this.scatterer.scatter();
    for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
      Player handle = player.getHandle();
      handle.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(Integer.MAX_VALUE, 9));
      handle.addPotionEffect(PotionEffectType.SLOW.createEffect(Integer.MAX_VALUE, 9));
      handle.setGameMode(GameMode.SURVIVAL);
    }
  }

  @Override
  public void onEnd() {
    for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
      PlayerUtil.resetPlayer(player.getHandle());
    }
  }

  @Override
  public void onTick(int tick) {

  }


}
