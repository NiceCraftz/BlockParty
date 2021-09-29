package net.coralmc.blockparty.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
@Getter
public class CustomBlock {
    private final String name;
    private final Material mat;
    private final byte data;

    public ItemStack getItem() {
        return new ItemStack(mat, 1, data);
    }
}
