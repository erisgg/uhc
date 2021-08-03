package gg.eris.uhc.customcraft.game;

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

  public static final String JSON_KEY = "custom_craft_uhc";

  public static final String DEATHMATCH_WORLD = "deathmatch_world";
  public static final String PREGAME_WORLD = "pregame_world";
  public static final String GAME_WORLD = "world";
  public static final String GAME_NETHER = "world_nether";

}
