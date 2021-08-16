package gg.eris.uhc.customcraft.command;

import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.text.CenteredChatData;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcTiers;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public final class TiersCommand implements CommandProvider {

  public static final String STRIKE =
      CC.GOLD.bold().strikethrough() + StringUtils.repeat("-", 45) + CC.RESET;

  private final CustomCraftUhcGame game;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "tiers",
        "shows tier information",
        "tiers",
        CustomCraftUhcIdentifiers.TIERS_PERMISSION,
        "tier"
    ).noArgsHandler(context -> {
      CustomCraftUhcPlayer player = this.game.getPlayer(context.getSenderAsPlayer().getPlayer());
      int tier = player.getStar();
      int points = player.getPoints();
      context.getSenderAsPlayer().sendMessage(getMessage(tier, points));
    }, true);
  }

  private String getMessage(int tier, int points) {
    String message = STRIKE + "\n"
        + CenteredChatData.getCentredMessage("&e&lYour Tier Info") + "\n" + CC.RESET
        + CenteredChatData.getCentredMessage("&6Points: &e" + Text.formatInt(points)) + "\n"
        + CC.RESET
        + CenteredChatData.getCentredMessage("&6Tier: &e" + Text.formatInt(tier)) + "\n"
        + CC.RESET;

    int pointsForNextTier = CustomCraftUhcTiers.getPointsForTier(tier + 1);
    if (pointsForNextTier > 0) {
      message += CenteredChatData.getCentredMessage("&6You are &e" + pointsForNextTier
          + " &6points away from the next tier.");
    }

    return message + STRIKE;
  }

}
