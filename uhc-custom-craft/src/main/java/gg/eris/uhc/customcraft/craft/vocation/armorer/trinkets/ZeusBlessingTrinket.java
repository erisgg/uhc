package gg.eris.uhc.customcraft.craft.vocation.armorer.trinkets;

import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.bukkit.util.DataUtil;
import gg.eris.uhc.customcraft.craft.CraftableInfo;
import gg.eris.uhc.customcraft.craft.Trinket;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import org.bukkit.Material;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

public class ZeusBlessingTrinket extends Trinket {
    public ZeusBlessingTrinket() {
        super("zeus_blessing", CraftableInfo.builder()
                .material(Material.WEB)
                .color(CC.GOLD)
                .name("Zeus' Blessing")
                .quote("You have been blessed!")
                .quoteGiver("Zeus")
                .nonTransformable()
                .build()
        );
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem())
                .shape(
                        "flf",
                        "lwl",
                        "flf"
                ).setIngredient('f', Material.FEATHER)
                .setIngredient('l', new MaterialData(Material.INK_SACK, DataUtil.LAPIS_LAZULI))
                .setIngredient('w', Material.WATER_BUCKET);
    }

    @Override
    public String getName() {
        return "Zeus' Blessing";
    }

    @Override
    public Vocation getVocation() {
        return Vocation.ARMORER;
    }
}
