package gg.eris.uhc.customcraft.craft;

import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CraftUtil;
import gg.eris.commons.bukkit.util.StackUtil;
import gg.eris.uhc.core.game.state.GameState;
import gg.eris.uhc.core.game.state.listener.type.GameStateListener;
import gg.eris.uhc.customcraft.craft.vocation.Craft;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.craft.vocation.VocationUnlockable;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

@RequiredArgsConstructor
public final class CraftListener extends GameStateListener {

  private final ErisPlayerManager erisPlayerManager;

  @Override
  protected void onEnable(GameState<?, ?> state) {

  }

  @Override
  protected void onDisable(GameState<?, ?> state) {

  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onItemCraft(CraftItemEvent event) {
    Player handle = (Player) event.getWhoClicked();
    CustomCraftUhcPlayer player = this.erisPlayerManager.getPlayer(handle);

    if (player == null) {
      return;
    }

    Recipe recipe = event.getRecipe();
    ItemStack result = recipe.getResult();
    if (result == null) {
      return;
    }

    VocationUnlockable unlockable = Vocation.getUnlockable(result);
    if (unlockable == null) {
      return;
    }

    CraftingInventory inventory = event.getInventory();

    // No item in the "completed" box
    if (inventory.getItem(0) == null) {
      return;
    }

    if (!player.hasUnlockable(unlockable)) {
      event.setResult(Result.DENY);
      event.setCancelled(true);
      TextController.send(
          player,
          TextType.ERROR,
          "You need the <h>{0}</h> craft to craft this.",
          unlockable.getName()
      );
      return;
    }

    int amountCrafted = getRecipeAmount(event);

    if (amountCrafted <= 0) {
      event.setResult(Result.DENY);
      event.setCancelled(true);
      return;
    }

    boolean prestige = player.getPrestigeLevel(unlockable.getVocation()) > 0;

    int maxAmount = unlockable instanceof Craft ?
        prestige ? ((Craft) unlockable).getPrestigeCraftableAmount()
            : ((Craft) unlockable).getCraftableAmount()
        : 1;

    int alreadyCrafted = player.getTimesCrafted(unlockable);

    int remainingCrafts = maxAmount - alreadyCrafted;

    if (remainingCrafts == 0) {
      event.setResult(Result.DENY);
      event.setCancelled(true);
      TextController.send(
          player,
          TextType.ERROR,
          "You cannot craft any more of <h>{0}</h>.",
          unlockable.getName()
      );
      return;
    }

    if (amountCrafted > remainingCrafts) {
      event.setResult(Result.DENY);
      event.setCancelled(true);
      TextController.send(
          player,
          TextType.ERROR,
          "You cannot craft that many. You can only craft <h>{0}</h> more.",
          remainingCrafts
      );
    } else {
      player.incrementCraftCount(unlockable.getIdentifier(), amountCrafted);
      TextController.send(
          player,
          TextType.SUCCESS,
          "You have crafted <h>{0}</h> (<h>{1}/{2}</h>).",
          unlockable.getName(),
          amountCrafted + alreadyCrafted,
          maxAmount
      );
    }
  }


  private int getRecipeAmount(CraftItemEvent event) {
    ItemStack test = event.getRecipe().getResult().clone();
    ClickType click = event.getClick();

    int recipeAmount = test.getAmount();

    switch (click) {
      case NUMBER_KEY:
        // If hotbar slot selected is full, crafting fails (vanilla behavior, even when
        // items match)
        if (event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) != null) {
          recipeAmount = 0;
        }
        break;

      case DROP:
      case CONTROL_DROP:
        // If we are holding items, craft-via-drop fails (vanilla behavior)
        ItemStack cursor = event.getCursor();
        // Apparently, rather than null, an empty cursor is AIR. I don't think that's
        // intended.
        if (!StackUtil.isNullOrAir(cursor)) {
          recipeAmount = 0;
        }
        break;

      case SHIFT_RIGHT:
      case SHIFT_LEFT:
        if (recipeAmount == 0) {
          break;
        }

        int maxCraftable =
            CraftUtil.getMaxCraftAmount(event.getInventory()) / event.getRecipe().getResult().getAmount();
        int capacity = CraftUtil.fits(test, event.getView().getBottomInventory());

        if (capacity < maxCraftable) {
          maxCraftable = ((capacity + recipeAmount - 1) / recipeAmount) * recipeAmount;
        }

        recipeAmount = maxCraftable;
        break;
    }

    return recipeAmount;
  }

}