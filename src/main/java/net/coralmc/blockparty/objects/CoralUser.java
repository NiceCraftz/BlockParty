package net.coralmc.blockparty.objects;

import lombok.Data;
import me.imbuzz.dev.gamemaker.api.GameUser;
import org.bukkit.entity.Player;

@Data
public class CoralUser extends GameUser {
    private boolean isAlive = true;

    public CoralUser(Player player) {
        super(player);
    }
}
