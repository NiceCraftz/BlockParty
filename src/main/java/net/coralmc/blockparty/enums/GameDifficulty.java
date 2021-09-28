package net.coralmc.blockparty.enums;

import lombok.Getter;

@Getter
public enum GameDifficulty {
    EASY("&aFACILE", 10, 4),
    MEDIUM("&eMEDIO", 5, 9),
    HARD("&cDIFFICILE", 1, Integer.MAX_VALUE);

    private final String name;
    private final int triggerTime;
    private final int round;

    GameDifficulty(String name, int triggerTime, int round) {
        this.name = name;
        this.triggerTime = triggerTime;
        this.round = round;
    }

    private static final GameDifficulty[] VALUES = values();

    public GameDifficulty next() {
        return VALUES[(this.ordinal() + 1) % VALUES.length];
    }

    @Override
    public String toString() {
        return name;
    }
}
