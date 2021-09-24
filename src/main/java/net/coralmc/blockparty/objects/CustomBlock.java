package net.coralmc.blockparty.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@RequiredArgsConstructor
@Getter
public class CustomBlock {
    private final Material mat;
    private final byte data;
}
