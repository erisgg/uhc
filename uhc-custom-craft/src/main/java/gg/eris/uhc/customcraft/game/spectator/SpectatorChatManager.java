package gg.eris.uhc.customcraft.game.spectator;

import com.google.common.collect.Sets;
import gg.eris.uhc.customcraft.game.CustomCraftUhcGame;
import gg.eris.uhc.customcraft.game.player.CustomCraftUhcPlayer;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public final class SpectatorChatManager {

  private final CustomCraftUhcGame game;
  private final Set<UUID> aliveInSpectatorChat;
  private final Set<UUID> deadNotInSpectatorChat;

  public SpectatorChatManager(CustomCraftUhcGame game) {
    this.game = game;
    this.aliveInSpectatorChat = Sets.newHashSet();
    this.deadNotInSpectatorChat = Sets.newHashSet();
  }

  public boolean isInSpectatorChat(Player player) {
    if (this.game.isPlayer(player.getUniqueId())) {
      return this.aliveInSpectatorChat.contains(player.getUniqueId());
    } else {
      return this.deadNotInSpectatorChat.contains(player.getUniqueId());
    }
  }

  public void addAliveInSpectatorChat(Player player) {
    this.aliveInSpectatorChat.add(player.getUniqueId());
  }

  public void removeAliveInSpectatorChat(Player player) {
    this.aliveInSpectatorChat.remove(player.getUniqueId());
  }

  public boolean isAliveInSpectatorChat(Player player) {
    return this.aliveInSpectatorChat.contains(player.getUniqueId());
  }

  public boolean isDeadNotInSpectatorChat(Player player) {
    return this.deadNotInSpectatorChat.contains(player.getUniqueId());
  }

  public void addDeadNotInSpectatorChat(Player player) {
    this.deadNotInSpectatorChat.add(player.getUniqueId());
  }

  public void removeDeadNotInSpectatorChat(Player player) {
    this.deadNotInSpectatorChat.remove(player.getUniqueId());
  }



}
