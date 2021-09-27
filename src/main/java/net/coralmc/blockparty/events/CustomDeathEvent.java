package net.coralmc.blockparty.events;

import net.coralmc.blockparty.objects.CoralUser;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class CustomDeathEvent extends PlayerEvent {
    private static final HandlerList handlerList = new HandlerList();
    private final CoralUser coralUser;

    public CustomDeathEvent(Player who, CoralUser coralUser) {
        super(who);
        this.coralUser = coralUser;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public CoralUser getCoralUser() {
        return coralUser;
    }
}
