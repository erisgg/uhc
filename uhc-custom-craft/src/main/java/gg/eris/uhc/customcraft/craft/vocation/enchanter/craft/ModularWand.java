package gg.eris.uhc.customcraft.craft.vocation.enchanter.craft;

import gg.eris.commons.bukkit.text.TextColor;
import gg.eris.commons.bukkit.text.TextComponent;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.NBTUtil;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.CraftableInfo;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public final class ModularWand extends Craft {

  public ModularWand() {
    super("modular_wand", CraftableInfo.builder()
        .material(Material.BLAZE_ROD)
        .color(CC.RED)
        .name("Modular Wand")
        .quote("Expelliarmus!")
        .quoteGiver("Harry Potter")
        .effects(
            "Lightning mode: Hit an opponent to strike them with lightning",
            "Fire mode: Hit an opponent to set them on fire",
            "Right click to swap",
            "10s cooldown"
        )
        .build());
  }

  @Override
  public Vocation getVocation() {
    return Vocation.ENCHANTER;
  }

  @Override
  public int getCraftableAmount() {
    return 1;
  }

  @Override
  public int getPrestigeCraftableAmount() {
    return 2;
  }

  @Override
  public Recipe getRecipe() {
    return new ShapedRecipe(getItem())
        .shape(
            " t ",
            " s ",
            "pbp"
        )
        .setIngredient('t', Material.TNT)
        .setIngredient('s', Material.SKULL_ITEM)
        .setIngredient('p', Material.BLAZE_POWDER)
        .setIngredient('b', Material.BLAZE_ROD);
  }

  @Override
  public String getName() {
    return "Modular Wand";
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ItemStack item = player.getItemInHand();

    if (!(this.isItem(item))) {
      return;
    }

    if (event.getAction() == Action.RIGHT_CLICK_AIR
        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      String action = NBTUtil.getStringNbtData(item, "action");
      if (action.equals("lightning")) {
        item = NBTUtil.setNbtData(item, "action", "fire");

        TextMessage message = TextMessage
            .of(TextComponent.builder("Wand mode set to ").color(TextColor.RED).build(),
                TextComponent.builder("FIRE").color(TextColor.GOLD).bold().build());
        TextController.send(player, message.getJsonMessage());

      } else if (action.equals("fire")) {
        item = NBTUtil.setNbtData(item, "action", "lightning");

        TextMessage message = TextMessage
            .of(TextComponent.builder("Wand mode set to ").color(TextColor.RED).build(),
                TextComponent.builder("LIGHTNING").color(TextColor.GOLD).bold().build());

        TextController.send(player, message.getJsonMessage());
      }
      player.setItemInHand(item);
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onAttack(EntityDamageByEntityEvent event) {
    if ((event.getEntityType() != EntityType.PLAYER) || (event.getDamager().getType()
        != EntityType.PLAYER)) {
      return;
    }

    Player player = (Player) event.getDamager();
    ItemStack item = player.getItemInHand();
    Player target = (Player) event.getEntity();

    if (!this.isItem(item)) {
      return;
    }

    int cooldown = NBTUtil.getIntNbtData(item, "cooldown");
    if (!(cooldown > 0)) {
      event.setCancelled(true);

      String action = NBTUtil.getStringNbtData(item, "action");

      if (action.equals("fire")) {
        target.setFireTicks(5);
        EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.FIRE;

        EntityDamageByEntityEvent event1 = new EntityDamageByEntityEvent(target, player, cause, 0);
        target.setLastDamageCause(event1);
        Bukkit.getServer().getPluginManager().callEvent(event1);


      } else {
        World world = player.getWorld();
        Location location = target.getLocation();
        world.strikeLightning(location);

        EntityDamageEvent.DamageCause cause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;

        EntityDamageByEntityEvent event1 = new EntityDamageByEntityEvent(target, player, cause, 3);
        target.setLastDamageCause(event1);
        Bukkit.getServer().getPluginManager().callEvent(event1);
      }

      item = NBTUtil.setNbtData(item, "cooldown", 10);
    }
  }
}
