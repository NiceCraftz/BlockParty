package net.coralmc.blockparty.game;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.enums.BlockPartyStatus;
import net.coralmc.blockparty.objects.CoralUser;

import java.util.Map;
import java.util.UUID;

@Getter
public class BlockPartyGame {

    private final BlockPartyData data;
    private final Map<UUID, CoralUser> userMap = Maps.newHashMap();
    private final BlockParty blockParty;

    public BlockPartyGame(BlockParty blockParty, BlockPartyData data) {
        this.blockParty = blockParty;
        this.data = data;
        data.setStatus(BlockPartyStatus.LOBBY);
    }



}
