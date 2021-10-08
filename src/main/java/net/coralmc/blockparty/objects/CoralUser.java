package net.coralmc.blockparty.objects;

import lombok.Data;
import me.imbuzz.dev.gamemaker.api.GameUser;
import me.imbuzz.dev.gamemaker.api.chat.ChatChannel;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Data
public class CoralUser extends GameUser {
    private boolean isSpectator = false;

    public CoralUser(Player player) {
        super(player);
    }

    public void setSpectator() {
        isSpectator = true;
        getPlayer().setGameMode(GameMode.SPECTATOR);
        setChatChannel(ChatChannel.SPECTATOR);
    }
}
