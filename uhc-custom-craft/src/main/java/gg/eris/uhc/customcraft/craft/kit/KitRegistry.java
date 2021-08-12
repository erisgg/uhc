package gg.eris.uhc.customcraft.craft.kit;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;
import gg.eris.commons.core.util.RandomUtil;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

@Getter
public final class KitRegistry extends Registry<Kit> {

  private static final KitRegistry REGISTRY = new KitRegistry();

  private final Kit excavator;
  private final Kit fisher;
  private final Kit forester;
  private final Kit warrior;

  public KitRegistry() {
    IdentifierProvider provider = CustomCraftUhcIdentifiers.KIT;

    this.excavator = register(Kit.builder(
        provider.id("excavator"),
        "Excavator",
        new ItemBuilder(Material.STONE_PICKAXE)
            .withName(CC.GRAY + "Excavator")
            .withLore(
                "",
                CC.GREEN.bold().underline() + "KIT",
                "",
                CC.WHITE + " - Stone Pickaxe",
                CC.WHITE + " - Stone Shovel",
                "",
                CC.BLUE.bold().underline() + "UPGRADES",
                "",
                CC.WHITE + " - Tools gain efficiency and unbreaking",
                CC.WHITE + " - Gain coal and iron",
                CC.WHITE + " - Tools become iron at Level V"
            )
            .build()
    ).addItem(0, new ItemStack(Material.STONE_PICKAXE))
        .addItem(0, new ItemStack(Material.STONE_SPADE))
        .addItem(1, new ItemBuilder(Material.STONE_PICKAXE)
            .withEnchantment(Enchantment.DIG_SPEED, 1)
            .build()
        ).addItem(1, new ItemBuilder(Material.STONE_SPADE)
            .withEnchantment(Enchantment.DIG_SPEED, 1)
            .build()
        ).addItem(1, new ItemStack(Material.COAL, 5))
        .addItem(2, new ItemBuilder(Material.STONE_PICKAXE)
            .withEnchantment(Enchantment.DIG_SPEED, 2)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).addItem(2, new ItemBuilder(Material.STONE_SPADE)
            .withEnchantment(Enchantment.DIG_SPEED, 2)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).addItem(2, new ItemStack(Material.COAL, 5))
        .addItem(2, new ItemStack(Material.IRON_INGOT, 3))
        .addItem(3, new ItemBuilder(Material.STONE_PICKAXE)
            .withEnchantment(Enchantment.DIG_SPEED, 3)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).addItem(3, new ItemBuilder(Material.STONE_SPADE)
            .withEnchantment(Enchantment.DIG_SPEED, 3)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).addItem(3, new ItemStack(Material.COAL, 10))
        .addItem(3, new ItemStack(Material.IRON_INGOT, 3))
        .addItem(4, new ItemBuilder(Material.IRON_PICKAXE)
            .withEnchantment(Enchantment.DIG_SPEED, 3)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).addItem(4, new ItemBuilder(Material.IRON_SPADE)
            .withEnchantment(Enchantment.DIG_SPEED, 3)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .build()
        ).addItem(4, new ItemStack(Material.COAL, 10))
        .addItem(4, new ItemStack(Material.IRON_INGOT, 5))
        .build());

    this.fisher = register(Kit.builder(
        provider.id("fisher"),
        "Fisher",
        new ItemBuilder(Material.FISHING_ROD)
            .withName(CC.GRAY + "Fisher")
            .withLore(
                "",
                CC.GREEN.bold().underline() + "KIT",
                "",
                CC.WHITE + " - Fishing Rod (Lure 1, Luck 1)",
                CC.WHITE + " - Sugar Cane",
                CC.WHITE + " - Sand",
                "",
                CC.BLUE.bold().underline() + "UPGRADES",
                "",
                CC.WHITE + " - Gain higher enchants on fishing rod",
                CC.WHITE + " - Gain more sand and sugar cane",
                CC.WHITE + " - Gain lilypads (and a pufferfish!)"
            ).build()
    ).addItem(0, new ItemBuilder(Material.FISHING_ROD).withEnchantment(Enchantment.LURE, 1)
        .withEnchantment(Enchantment.LUCK, 1).build())
        .addItem(0, new ItemStack(Material.SUGAR_CANE, 3))
        .addItem(0, new ItemStack(Material.SAND, 3))
        .addItem(1,
            new ItemBuilder(Material.FISHING_ROD).withEnchantment(Enchantment.LURE, 2)
                .withEnchantment(Enchantment.LUCK, 2).build())
        .addItem(1, new ItemStack(Material.SUGAR_CANE, 6))
        .addItem(1, new ItemStack(Material.SAND, 6))
        .addItem(1, new ItemStack(Material.WATER_LILY, 16))
        .addItem(2,
            new ItemBuilder(Material.FISHING_ROD).withEnchantment(Enchantment.LURE, 3)
                .withEnchantment(Enchantment.LUCK, 3).withEnchantment(Enchantment.DURABILITY, 1)
                .build())
        .addItem(2, new ItemStack(Material.SUGAR_CANE, 9))
        .addItem(2, new ItemStack(Material.SAND, 9))
        .addItem(2, new ItemStack(Material.WATER_LILY, 32))
        .addItem(3,
            new ItemBuilder(Material.FISHING_ROD).withEnchantment(Enchantment.LURE, 4)
                .withEnchantment(Enchantment.LUCK, 4).withEnchantment(Enchantment.DURABILITY, 1)
                .build())
        .addItem(3, new ItemStack(Material.SUGAR_CANE, 12))
        .addItem(3, new ItemStack(Material.SAND, 12))
        .addItem(3, new ItemStack(Material.WATER_LILY, 48))
        .addItem(4,
            new ItemBuilder(Material.FISHING_ROD).withEnchantment(Enchantment.LURE, 5)
                .withEnchantment(Enchantment.LUCK, 5).withEnchantment(Enchantment.DURABILITY, 1)
                .build())
        .addItem(4, new ItemStack(Material.SUGAR_CANE, 15))
        .addItem(4, new ItemStack(Material.SAND, 15))
        .addItem(4, new ItemStack(Material.WATER_LILY, 64))
        .build());

    this.forester = register(Kit.builder(
        provider.id("forester"),
        "Forester",
        new ItemBuilder(Material.STONE_AXE)
            .withName(CC.GRAY + "Forester")
            .withLore(
                "",
                CC.GREEN.bold().underline() + "KIT",
                "",
                CC.WHITE + " - Stone Axe",
                CC.WHITE + " - Logs",
                "",
                CC.BLUE.bold().underline() + "UPGRADES",
                "",
                CC.WHITE + " - Gain more logs",
                CC.WHITE + " - Gain enchantments on the stone axe",
                CC.WHITE + " - Gain apples",
                CC.WHITE + " - Gain vines"
            )
            .build()
    ).addItem(0, new ItemStack(Material.STONE_AXE))
        .addItem(0, new ItemStack(Material.LOG, 8))
        .addItem(1,
            new ItemBuilder(Material.STONE_AXE).withEnchantment(Enchantment.DIG_SPEED, 1).build())
        .addItem(1, new ItemStack(Material.LOG, 10))
        .addItem(1, new ItemStack(Material.APPLE))
        .addItem(1, new ItemStack(Material.VINE, 16))
        .addItem(2, new ItemBuilder(Material.STONE_AXE)
            .withEnchantment(Enchantment.DIG_SPEED, 2)
            .withEnchantment(Enchantment.DURABILITY, 1).build())
        .addItem(2, new ItemStack(Material.LOG, 12))
        .addItem(2, new ItemStack(Material.APPLE))
        .addItem(2, new ItemStack(Material.VINE, 32))
        .addItem(3, new ItemBuilder(Material.STONE_AXE)
            .withEnchantment(Enchantment.DIG_SPEED, 3)
            .withEnchantment(Enchantment.DURABILITY, 1).build())
        .addItem(3, new ItemStack(Material.LOG, 14))
        .addItem(3, new ItemStack(Material.APPLE, 2))
        .addItem(3, new ItemStack(Material.VINE, 48))
        .addItem(4, new ItemBuilder(Material.IRON_AXE)
            .withEnchantment(Enchantment.DIG_SPEED, 3)
            .withEnchantment(Enchantment.DURABILITY, 1).build())
        .addItem(4, new ItemStack(Material.LOG, 16))
        .addItem(4, new ItemStack(Material.APPLE, 3))
        .addItem(4, new ItemStack(Material.VINE, 64))
        .build());

    this.warrior = register(Kit.builder(
        provider.id("warrior"),
        "Warrior",
        new ItemBuilder(Material.STONE_SWORD)
            .withName(CC.GRAY + "Warrior")
            .withLore(
                "",
                CC.GREEN.bold().underline() + "KIT",
                "",
                CC.WHITE + " - Stone Sword",
                CC.WHITE + " - Rotten Flesh",
                "",
                CC.BLUE.bold().underline() + "UPGRADES",
                "",
                CC.WHITE + " - Upgrade the sword",
                CC.WHITE + " - Gain bones",
                CC.WHITE + " - Gain spider eyes",
                CC.WHITE + " - Gain gunpowder",
                CC.WHITE + " - Gain slimeball OR enderpearl (random)"
            ).build()
    )
        .addItem(0, new ItemStack(Material.STONE_SWORD))
        .addItem(0, new ItemStack(Material.ROTTEN_FLESH))

        .addItem(1, new ItemBuilder(Material.STONE_SWORD)
            .withEnchantment(Enchantment.DURABILITY, 1).build())
        .addItem(1, new ItemStack(Material.ROTTEN_FLESH, 2))
        .addItem(1, new ItemStack(Material.BONE))

        .addItem(2, new ItemBuilder(Material.STONE_SWORD)
            .withEnchantment(Enchantment.DURABILITY, 1)
            .withEnchantment(Enchantment.LOOT_BONUS_MOBS, 1).build())
        .addItem(2, new ItemStack(Material.ROTTEN_FLESH, 2))
        .addItem(2, new ItemStack(Material.BONE))
        .addItem(2, new ItemStack(Material.SPIDER_EYE))

        .addItem(3, new ItemBuilder(Material.STONE_SWORD)
            .withEnchantment(Enchantment.DURABILITY, 2)
            .withEnchantment(Enchantment.LOOT_BONUS_MOBS, 1).build())
        .addItem(3, new ItemStack(Material.ROTTEN_FLESH, 2))
        .addItem(3, new ItemStack(Material.BONE, 2))
        .addItem(3, new ItemStack(Material.SPIDER_EYE))
        .addItem(3, new ItemStack(Material.SULPHUR))

        .addItem(4, new ItemBuilder(Material.IRON_SWORD)
            .withEnchantment(Enchantment.DURABILITY, 2)
            .withEnchantment(Enchantment.LOOT_BONUS_MOBS, 1).build())
        .addItem(4, new ItemStack(Material.ROTTEN_FLESH, 2))
        .addItem(4, new ItemStack(Material.BONE, 2))
        .addItem(4, new ItemStack(Material.SPIDER_EYE))
        .addItem(4, new ItemStack(Material.SULPHUR))
        .addItem(4, new ItemStack(RandomUtil.randomBoolean() ? Material.SLIME_BALL :
            Material.ENDER_PEARL)) // TODO: Change to be individual
        .build());
  }

  public Kit getKit(CustomCraftUhcPlayer player) {
    Identifier active = player.getActiveKit();
    if (active == null) {
      switch (RandomUtil.randomInt(4)) {
        case 0:
          return this.excavator;
        case 1:
          return this.fisher;
        case 2:
          return this.forester;
        case 3:
        default:
          return this.warrior;
      }
    } else {
      return get(active);
    }
  }

  public static KitRegistry get() {
    return REGISTRY;
  }

}
