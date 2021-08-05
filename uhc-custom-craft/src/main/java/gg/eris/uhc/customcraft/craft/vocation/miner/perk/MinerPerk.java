package gg.eris.uhc.customcraft.craft.vocation.miner.perk;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

/**
 * Haste for 7 seconds + 1s per level
 */
public final class MinerPerk extends Perk {

  private static final Set<Material> APPLICABLE = Set.of(
      Material.COAL_ORE,
      Material.GLOWING_REDSTONE_ORE,
      Material.REDSTONE_ORE,
      Material.LAPIS_ORE,
      Material.GOLD_ORE,
      Material.DIAMOND_ORE,
      Material.QUARTZ_ORE,
      Material.EMERALD_ORE,
      Material.IRON_ORE
  );

  private final ErisPlayerManager erisPlayerManager;

  public MinerPerk(ErisPlayerManager erisPlayerManager) {
    super("miner_perk");
    this.erisPlayerManager = erisPlayerManager;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    CustomCraftUhcPlayer player = this.erisPlayerManager.getPlayer(event.getPlayer());
    int level = getLevel(player);
    if (APPLICABLE.contains(event.getBlock().getType()) && level > 0) {
      event.getPlayer()
          .addPotionEffect(PotionEffectType.FAST_DIGGING.createEffect(140 + 20 * (level - 1), 0));
    }
  }

  @Override
  public String getName() {
    return "Miner Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Gives you haste for {0} seconds when you break an ore",
        (level - 1) + 7);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }


}
