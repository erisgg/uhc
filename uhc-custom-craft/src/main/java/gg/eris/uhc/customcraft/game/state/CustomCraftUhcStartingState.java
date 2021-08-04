package gg.eris.uhc.customcraft.game.state;

import gg.eris.commons.bukkit.util.PlayerUtil;
import gg.eris.uhc.core.game.Scatterer;
import gg.eris.uhc.core.game.state.AbstractStartingGameState;
import gg.eris.uhc.customcraft.craft.bag.TrinketBagItem;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationRegistry;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import org.bukkit.GameMode;
import org.bukkit.WorldBorder;
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
      PlayerUtil.resetPlayer(handle);
      handle.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(Integer.MAX_VALUE, 9));
      handle.addPotionEffect(PotionEffectType.SLOW.createEffect(Integer.MAX_VALUE, 9));
    }
  }

  @Override
  public void onEnd() {
    for (CustomCraftUhcPlayer player : this.game.getPlayers()) {
      PlayerUtil.resetPlayer(player.getHandle());
      player.getHandle().setGameMode(GameMode.SURVIVAL);
      player.getHandle().setMaxHealth(this.game.getSettings().getMaxHealth());
      player.getHandle().setHealth(player.getHandle().getMaxHealth());
      TrinketBagItem.giveBag(player.getHandle());
    }
  }

  @Override
  public void onTick(int tick) {

  }


}
