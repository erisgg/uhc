package gg.eris.uhc.core;

import gg.eris.commons.bukkit.permission.Permission;
import gg.eris.commons.bukkit.permission.PermissionGroup;
import gg.eris.commons.bukkit.permission.PermissionRegistry;
import gg.eris.commons.core.identifier.Identifier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UhcIdentifiers {

  public static final Identifier VIEWSPECTATORS_PERMISSION = permission(
      "viewspectators",
      PermissionGroup.STAFF
  );

  private static Identifier permission(String name, PermissionGroup group) {
    return Permission.ofDefault(PermissionRegistry.get(), name, group).getIdentifier();
  }

}
