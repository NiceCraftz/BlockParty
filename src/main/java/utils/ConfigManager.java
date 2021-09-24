package utils;

import lombok.Getter;
import lombok.SneakyThrows;
import net.coralmc.blockparty.BlockParty;
import net.coralmc.blockparty.files.CoralFile;

@Getter
public class ConfigManager {
    private static ConfigManager configManager;
    private final CoralFile config;
    private final CoralFile messages;

    public ConfigManager(BlockParty blockParty) {
        configManager = this;
        this.config = new CoralFile(blockParty, "config.yml");
        this.messages = new CoralFile(blockParty, "messages.yml");
    }

    @SneakyThrows(Exception.class)
    public static void reloadAll() {
        configManager.getConfig().getConfiguration().load(configManager.getConfig().getFile());
        configManager.getMessages().getConfiguration().load(configManager.getMessages().getFile());
    }
}
