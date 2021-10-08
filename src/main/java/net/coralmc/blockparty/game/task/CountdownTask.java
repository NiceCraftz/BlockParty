package net.coralmc.blockparty.game.task;

import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.utils.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownTask extends BukkitRunnable {
    private int countdown;
    private final BlockPartyGame partyGame;
    private final BlockParty blockParty;

    public CountdownTask(BlockPartyGame partyGame) {
        this.partyGame = partyGame;
        blockParty = partyGame.getBlockParty();
        countdown = ConfigHelper.getInt(blockParty, "countdown");
    }

    @Override
    public void run() {
        countdown--;

        if(Bukkit.getOnlinePlayers().size() <= ConfigHelper.getInt(blockParty, "min-players")) {
            partyGame.setStatus(BlockPartyStatus.STARTING);
            Bukkit.broadcastMessage(ConfigHelper.getFormattedString(blockParty, "cancel-announce"));
            cancel();
            return;
        }

        if (countdown < 0
                || partyGame.getStatus() == BlockPartyStatus.PLAYING
                || partyGame.getUserMap().size() <= 1) {
            cancel();
            return;
        }

        if(countdown == 10 || countdown <= 5) {
            Bukkit.broadcastMessage(ConfigHelper.getFormattedString(blockParty, "timer-announce", countdown));
        }

        if(countdown == 0) {
            partyGame.setStatus(BlockPartyStatus.PLAYING);
            cancel();
        }
    }
}