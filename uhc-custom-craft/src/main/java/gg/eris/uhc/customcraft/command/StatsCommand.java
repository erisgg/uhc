package gg.eris.uhc.customcraft.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import gg.eris.commons.bukkit.command.Command.Builder;
import gg.eris.commons.bukkit.command.CommandManager;
import gg.eris.commons.bukkit.command.CommandProvider;
import gg.eris.commons.bukkit.command.argument.StringArgument;
import gg.eris.commons.bukkit.player.ErisPlayerManager;
import gg.eris.commons.bukkit.text.CenteredChatData;
import gg.eris.commons.bukkit.text.TextController;
import gg.eris.commons.bukkit.text.TextMessage;
import gg.eris.commons.bukkit.text.TextType;
import gg.eris.commons.bukkit.util.CC;
import gg.eris.commons.core.util.Text;
import gg.eris.uhc.core.UhcPlugin;
import gg.eris.uhc.core.game.player.UhcPlayer;
import gg.eris.uhc.customcraft.CustomCraftUhcIdentifiers;
import gg.eris.uhc.customcraft.craft.vocation.Vocation;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public final class StatsCommand implements CommandProvider {

  public static final String STRIKE =
      CC.GOLD.bold().strikethrough() + StringUtils.repeat("-", 45) + CC.RESET;

  private final UhcPlugin plugin;
  private final ErisPlayerManager erisPlayerManager;

  @Override
  public Builder getCommand(CommandManager manager) {
    return manager.newCommandBuilder(
        "statistics",
        "shows stats",
        "stats [player]",
        CustomCraftUhcIdentifiers.STATS_PERMISSION,
        "stats"
        ).noArgsHandler(context -> {
      CustomCraftUhcPlayer player = this.erisPlayerManager.getPlayer(context.getSenderAsPlayer());

      int kills = player.getKills();
      int deaths = player.getDeaths();
      int wins = player.getWins();
      int gamesPlayed = player.getGamesPlayed();
      int coins = player.getCoins();

      double kd;
      if (kills == 0) {
        kd = 0;
      } else {
        kd = kills / Math.max(1.0, deaths);
      }

      double winLoss = wins / Math.max(1.0, gamesPlayed);

      player.getHandle().sendMessage(getMessage(
          player.getName(),
          kills,
          deaths,
          wins,
          gamesPlayed,
          coins,
          kd,
          winLoss)
      );
    }, true).withSubCommand()
        .asPlayerOnly()
        .argument(StringArgument.of("target"))
        .handler(context -> {
          String target = context.getArgument("target");
          TextController.send(
              context.getSenderAsPlayer(),
              TextType.INFORMATION,
              "Looking up stats for <h>{0}</h>.",
              target
          );
          Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            UUID uuid = this.erisPlayerManager.getOfflineDataManager().getUuid(target);

            if (uuid == null) {
              TextController.send(
                  context.getSenderAsPlayer(),
                  TextType.ERROR,
                  "Could not find player <h>{0}</h>.",
                  target
              );
              return;
            }

            // TODO: Have a custom craft data class and move all data into there, so can serialize
            // into a node without an ErisPlayer reference
            JsonNode node = this.erisPlayerManager.getOfflineDataManager().getRaw(uuid);
            String name = node.get("name").asText();
            int coins = 0;
            int gamesPlayed = 0;
            int wins = 0;
            int kills = 0;
            int deaths = 0;

            if (node.has("games")) {
              JsonNode games = node.get("games");
              if (games.has(CustomCraftUhcIdentifiers.JSON_KEY)) {
                JsonNode customCraft = games.get(CustomCraftUhcIdentifiers.JSON_KEY);

                if (customCraft.has("coins")) {
                  coins = customCraft.get("coins").asInt();
                }

                if (customCraft.has("games_played")) {
                  gamesPlayed = customCraft.get("games_played").asInt();
                }

                if (customCraft.has("wins")) {
                  wins = customCraft.get("wins").asInt();
                }

                if (customCraft.has("kills")) {
                  kills = customCraft.get("kills").asInt();
                }

                if (customCraft.has("deaths")) {
                  deaths = customCraft.get("deaths").asInt();
                }
              }
            }

            double kd;
            if (kills == 0) {
              kd = 0;
            } else {
              kd = kills / Math.max(1.0, deaths);
            }

            double winLoss = wins / Math.max(1.0, gamesPlayed);


            String message = getMessage(
                name,
                kills,
                deaths,
                wins,
                gamesPlayed,
                coins,
                kd,
                winLoss
            );

            Bukkit.getScheduler().runTask(this.plugin, () -> context.getCommandSender().sendMessage(message));
          });
        }).finished();

  }

  private String getMessage(String name, int kills, int deaths, int wins, int gamesPlayed,
      int coins, double kd, double winLoss) {
    return STRIKE + "\n"
            + CenteredChatData.getCentredMessage("&e&l" + name + "'s Stats") + "\n" + CC.RESET
            + CenteredChatData.getCentredMessage("&6Kills: &e" + Text.formatInt(kills)) + "\n" + CC.RESET
            + CenteredChatData.getCentredMessage("&6Deaths: &e" + Text.formatInt(deaths)) + "\n" + CC.RESET
            + CenteredChatData.getCentredMessage("&6K/D Ratio: &e" + Text.formatDouble(kd)) + "\n" + CC.RESET
            + CenteredChatData.getCentredMessage("&6Games Played: &e" + Text.formatInt(gamesPlayed)) + "\n" + CC.RESET
        + CenteredChatData.getCentredMessage("&6Wins: &e" + Text.formatInt(wins)) + "\n" + CC.RESET
        + CenteredChatData.getCentredMessage("&6Win/Loss Ratio: &e" + Text.formatDouble(winLoss)) + "\n" + CC.RESET
        + CenteredChatData.getCentredMessage("&6Coins: &e" + Text.formatInt(coins)) + CC.RESET +
        "\n" + STRIKE;

  }
}
