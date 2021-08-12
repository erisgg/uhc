package gg.eris.uhc.customcraft.craft.kit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.ItemBuilder;
import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class Kit implements Identifiable {

  private final Identifier identifier;

  @Getter
  private final String name;
  @Getter
  private final ItemStack display;
  @Getter
  private final String itemDisplayName;

  private final Int2ObjectMap<List<ItemStack>> items;

  private Kit(Identifier identifier, String name, ItemStack display,
      Int2ObjectMap<List<ItemStack>> items) {
    this.identifier = identifier;
    this.name = name;
    this.display = new ItemBuilder(display)
        .addLore(
            "",
            CC.GREEN + "Left click to select"
        ).build();
    this.itemDisplayName = display.getItemMeta().getDisplayName();

    Int2ObjectMap<List<ItemStack>> newItems = new Int2ObjectArrayMap<>();
    for (Int2ObjectMap.Entry<List<ItemStack>> entry : items.int2ObjectEntrySet()) {
      newItems.put(entry.getIntKey(), ImmutableList.copyOf(entry.getValue()));
    }
    this.items = newItems;
  }

  @Override
  public Identifier getIdentifier() {
    return this.identifier;
  }

  public String getStorageKey() {
    return this.identifier.getValue();
  }

  public List<ItemStack> getItems(int level) {
    return this.items.getOrDefault(level, List.of());
  }

  public void give(CustomCraftUhcPlayer player) {
    Player handle = player.getHandle();
    if (handle != null) {
      int level = player.getKitLevel(this);
      getItems(level).forEach(handle.getInventory()::addItem);
    }
  }

  public static KitBuilder builder(Identifier identifier, String name, ItemStack display) {
    return new KitBuilder(identifier, name, display);
  }

  public static final class KitBuilder {

    private final Identifier identifier;
    private final String name;
    private final ItemStack display;
    private final Int2ObjectMap<List<ItemStack>> items;

    public KitBuilder(Identifier identifier, String name, ItemStack display) {
      this.identifier = identifier;
      this.name = name;
      this.display = display;
      this.items = new Int2ObjectArrayMap<>();
    }

    public KitBuilder addItem(int level, ItemStack item) {
      List<ItemStack> list = this.items.getOrDefault(level, Lists.newArrayList());
      list.add(item);
      this.items.put(level, list);
      return this;
    }

    public Kit build() {
      return new Kit(this.identifier, this.name, this.display, this.items);
    }

  }

}
