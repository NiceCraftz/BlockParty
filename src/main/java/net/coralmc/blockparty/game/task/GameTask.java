package net.coralmc.blockparty.game.task;

import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.game.BlockPartyGame;
import net.coralmc.blockparty.utils.ConfigurationHelper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static net.coralmc.blockparty.utils.ConfigurationHelper.*;

public class GameTask extends BukkitRunnable {
    private final BlockPartyGame blockPartyGame;
    private final BlockParty blockParty;

    private int time;
    private int roundTime;
    private int round;

    public GameTask(BlockPartyGame blockPartyGame) {
        this.blockPartyGame = blockPartyGame;
        blockParty = blockPartyGame.getBlockParty();

        roundTime = getInt(blockParty, "round-time");
        time = roundTime;
    }


    @Override
    public void run() {
        if (blockPartyGame.getData().getStatus() == BlockPartyStatus.END) {
            this.cancel();
            return;
        }

        if (Bukkit.getOnlinePlayers().size() <= 1 || blockPartyGame.getUserMap().size() <= 1) {
            blockPartyGame.getData().setStatus(BlockPartyStatus.END);
            cancel();
            return;
        }

        time--;
        if (blockPartyGame.getGameDifficulty().getTriggerTime() == time) {
            blockPartyGame.blockTime();
        }

        if (time == 10 || (time <= 5 && time >= 1)) {
            blockPartyGame.getUserMap().values().forEach(coralUser -> {
                Player player = coralUser.getPlayer();
                player.playSound(player.getLocation(), Sound.ZOMBIE_WOOD, 1, 0);
                player.sendMessage(getFormattedString(blockParty, "floor-announce", time));
            });
        }

        if (time == 0) {
            blockPartyGame.getUserMap().values().forEach(coralUser -> {
                Player player = coralUser.getPlayer();
                player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1, 0);
            });

            blockPartyGame.delete();
            Bukkit.getScheduler().runTaskLater(blockParty, () -> {
                round++;

                if (blockPartyGame.getGameDifficulty().getRound() < round) {
                    blockPartyGame.setGameDifficulty(blockPartyGame.getGameDifficulty().next());
                }

                roundTime = Math.max(2, --roundTime);
                time = roundTime;
            }, 20L * ConfigurationHelper.getInt(blockParty, "refill-time"));

        }
    }
}
