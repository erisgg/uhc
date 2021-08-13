package gg.eris.uhc.customcraft;

import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.permission.PermissionGroup;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class CustomCraftUhcIdentifiers {

  public static final String NAMESPACE = "custom_craft_uhc";
  public static final IdentifierProvider NAMESPACE_ID = new IdentifierProvider(NAMESPACE);

  public static final IdentifierProvider MENU_ID
      = new IdentifierProvider(NAMESPACE + "_menu");

  public static final IdentifierProvider SCOREBOARD_ID
      = new IdentifierProvider(NAMESPACE + "_scoreboard");

  public static final IdentifierProvider UNLOCKABLE
      = new IdentifierProvider(NAMESPACE + "_unlockable");

  public static final IdentifierProvider ULTIMATE_UNLOCKABLE
      = new IdentifierProvider(NAMESPACE + "_ultimate_unlockable");

  public static final IdentifierProvider KIT
      = new IdentifierProvider(NAMESPACE + "kits");

  public static final String LIVE_GAME_SET = "custom_craft_uhc_games";

  public static final String JSON_KEY = "custom_craft_uhc";
  public static final String JSON_UNLOCKS_KEY = "unlocks";
  public static final String JSON_PRESTIGE_KEY = "prestiges";

  public static final String DEATHMATCH_WORLD = "deathmatch_world";
  public static final String PREGAME_WORLD = "pregame_world";
  public static final String GAME_WORLD = "world";
  public static final String GAME_NETHER = "world_nether";

  public static final String VOCATION_CRAFT_NBT_KEY = "unlockable";


  public static final char STAR = '⭐';


  public static final Identifier VIEWSPECTATORS_PERMISSION = permission(
      "viewspectators",
      PermissionGroup.STAFF
  );

  public static final Identifier GIVECOINS_PERMISSION = permission(
      "givecoins",
      PermissionGroup.OWNER_DEVELOPER
  );

  public static final Identifier GIVEITEM_PERMISSION = permission(
      "giveitem",
      PermissionGroup.OWNER_DEVELOPER
  );

  public static final Identifier STATS_PERMISSION = permission(
      "stats",
      PermissionGroup.ALL
  );

  private static Identifier permission(String name, PermissionGroup group) {
    return Permission.ofDefault(PermissionRegistry.get(), name, group).getIdentifier();
  }

}
