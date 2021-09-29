package net.coralmc.blockparty.enums.consumer;

import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.utils.ConfigurationHelper;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

public class CooldownConsumer implements Consumer<BlockPartyGame> {


    @Override
    public void accept(BlockPartyGame blockPartyGame) {
        new CountdownTask(blockPartyGame).runTaskTimerAsynchronously(blockPartyGame.getBlockParty(), 0, 20L);
    }

    public class CountdownTask extends BukkitRunnable {
        private int countdown;
        private final BlockPartyGame partyGame;

        public CountdownTask(BlockPartyGame partyGame) {
            this.partyGame = partyGame;
            countdown = ConfigurationHelper.getInt(partyGame.getBlockParty(), "countdown");
        }

        @Override
        public void run() {
            countdown--;
            if (countdown < 0
                    || partyGame.getData().getStatus() == BlockPartyStatus.PLAYING
                    || partyGame.getUserMap().size() <= 1
                    || Bukkit.getOnlinePlayers().size() <= 1) {
                cancel();
                return;
            }

            if(countdown == 10 || countdown <= 5) {
                Bukkit.broadcastMessage(ConfigurationHelper.getFormattedString(partyGame.getBlockParty(), "timer-announce", countdown));
            }

            if(countdown == 0) {
                partyGame.getData().setStatus(BlockPartyStatus.PLAYING);
                cancel();
            }
        }
    }
}
