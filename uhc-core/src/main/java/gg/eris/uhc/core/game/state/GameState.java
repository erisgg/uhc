package gg.eris.uhc.core.game.state;

import gg.eris.commons.core.identifier.Identifiable;
import gg.eris.commons.core.identifier.Identifier;
import gg.eris.commons.core.identifier.IdentifierProvider;
import gg.eris.commons.core.registry.Registry;
import gg.eris.uhc.core.game.UhcGame;
import gg.eris.uhc.core.game.player.UhcPlayer;
import net.minecraft.server.v1_8_R3.ItemNetherStar;
import org.bukkit.event.Listener;

public interface GameState<S extends UhcPlayer, T extends UhcGame<S>> extends Listener {

  class TypeRegistry extends Registry<Type> {

    private static final TypeRegistry REGISTRY = new TypeRegistry();

    private TypeRegistry() {
    }

    public static final Type WAITING;
    public static final Type COUNTDOWN;
    public static final Type STARTING;
    public static final Type GRACE_PERIOD;
    public static final Type PVP;
    public static final Type DEATHMATCH;
    public static final Type ENDED;


    static {
      IdentifierProvider provider = new IdentifierProvider("eris");
      WAITING = REGISTRY.register(new Type(provider.id("waiting")));
      COUNTDOWN = REGISTRY.register(new Type(provider.id("countdown")));
      STARTING = REGISTRY.register(new Type(provider.id("starting")));
      GRACE_PERIOD = REGISTRY.register(new Type(provider.id("grace_period")));
      PVP = REGISTRY.register(new Type(provider.id("pvp")));
      DEATHMATCH = REGISTRY.register(new Type(provider.id("deathmatch")));
      ENDED = REGISTRY.register(new Type(provider.id("ended")));
    }

    public static Type getType(Identifier identifier) {
      return REGISTRY.get(identifier);
    }

  }

  class Type implements Identifiable {

    private final Identifier identifier;

    public Type(Identifier identifier) {
      this.identifier = identifier;
    }

    @Override
    public Identifier getIdentifier() {
      return this.identifier;
    }
  }

  Type getType();

  void start();

  void end();

  void tick();

  boolean canStart();

}
