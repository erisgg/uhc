package gg.eris.uhc.customcraft.craft.vocation.specialist.perk;

import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.craft.vocation.Perk;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Doubles ore drops (2.5% per level, starting at 2.5%)
 */
public final class SpecialistPerk extends Perk {

  // Lapis handled separately because 1.8 :D
  private static final Set<Material> APPLICABLE = Set.of(
      Material.COAL,
      Material.IRON_ORE,
      Material.GOLD_ORE,
      Material.REDSTONE,
      Material.GOLD_INGOT,
      Material.IRON_INGOT
  );

  private final ErisPlayerManager erisPlayerManager;

  public SpecialistPerk(ErisPlayerManager erisPlayerManager) {
    super("specialist_perk");
    this.erisPlayerManager = erisPlayerManager;
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onBlockBreak(BlockBreakEvent event) {
    Block block = event.getBlock();
    CustomCraftUhcPlayer player = this.erisPlayerManager.getPlayer(event.getPlayer());
    Collection<ItemStack> drops = handle(
        player.getHandle(),
        block.getDrops(event.getPlayer().getItemInHand()),
        getLevel(player)
    );
    event.setCancelled(true);
    event.getBlock().setType(Material.AIR);
    for (ItemStack drop : drops) {
      event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
    }
  }

  public static Collection<ItemStack> handle(Player handle,Collection<ItemStack> drops,
      int level) {
    List<ItemStack> newDrops = Lists.newArrayList();
    boolean lucky = false;
    for (ItemStack drop : drops) {
      if (isApplicable(drop) && roll(level)) {
        drop.setAmount(drop.getAmount() * 2);
        lucky = true;
      }
      newDrops.add(drop);
    }

    if (lucky) {
      TextController.send(
          handle,
          TextType.INFORMATION,
          "Your Specialist Perk has <h>increased</h> block drops."
      );
    }

    return newDrops;
  }

  private static boolean isApplicable(ItemStack item) {
    return APPLICABLE.contains(item.getType()) || (item.getType() == Material.INK_SACK
        && item.getDurability() == 4);
  }

  private static boolean roll(int level) {
    return RandomUtil.randomInt(0, 1000) < 25 * level;
  }

  @Override
  public String getName() {
    return "Specialist Perk";
  }

  @Override
  public String getDescription(int level) {
    return Text.replaceVariables("Has a {0}% chance to double ore drops", 2.5 * level);
  }

  @Override
  public Vocation getVocation() {
    return Vocation.SPECIALIST;
  }

}
