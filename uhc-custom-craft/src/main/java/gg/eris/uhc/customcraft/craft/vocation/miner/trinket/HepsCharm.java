package gg.eris.uhc.customcraft.craft.vocation.miner.trinket;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class HepsCharm extends Trinket {

  private static final Set<Material> APPLICABLE = Set.of(
      Material.WOOD_PICKAXE,
      Material.STONE_PICKAXE,
      Material.IRON_PICKAXE,
      Material.GOLD_PICKAXE,
      Material.DIAMOND_PICKAXE,
      Material.WOOD_AXE,
      Material.STONE_AXE,
      Material.IRON_AXE,
      Material.GOLD_AXE,
      Material.DIAMOND_AXE,
      Material.WOOD_SPADE,
      Material.STONE_SPADE,
      Material.IRON_SPADE,
      Material.GOLD_SPADE,
      Material.DIAMOND_SPADE,
      Material.WOOD_SWORD,
      Material.STONE_SWORD,
      Material.IRON_SWORD,
      Material.GOLD_SWORD,
      Material.DIAMOND_SWORD
  );

  public HepsCharm() {
    super("heps_charm", CraftableInfo.builder()
        .material(Material.ACTIVATOR_RAIL)
        .name("Hep's Charm")
        .color(CC.GOLD)
        .quote("Can we fix it?")
        .quoteGiver("Bob, a builder")
        .effects("Tools and swords take 25% less damage")
        .nonTransformable()
        .build());
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape("iri",
            "ror",
            "iri").setIngredient('i', Material.IRON_INGOT)
        .setIngredient('r', Material.REDSTONE)
        .setIngredient('o', Material.OBSIDIAN);
  }

  @Override
  public String getName() {
    return "Hep's Charm";
  }

  @Override
  public Vocation getVocation() {
    return Vocation.MINER;
  }

  @EventHandler
  public void onDamageItem(PlayerItemDamageEvent event) {
    Player handle = event.getPlayer();
    CustomCraftUhcPlayer player = (CustomCraftUhcPlayer) UhcPlugin.getPlugin().getUhc().getGame()
        .getPlayer(handle);
    if (player == null) {
      return;
    }

    ItemStack item = handle.getItemInHand();
    if (!APPLICABLE.contains(item.getType())) {
      return;
    }

    if (player.getTrinketBagItem().hasTrinket(this) && RandomUtil.percentChance(25)) {
      if (!Vocation.MINER.getRegistry().getFourthCraft().isItem(item)) { // luck pick
        event.setCancelled(true);
      }
    }
  }


}
