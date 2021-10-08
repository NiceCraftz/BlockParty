package net.coralmc.blockparty.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.coralmc.blockparty.utils.ConfigHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
@Getter
public class CustomBlock {
    private final String name;
    private final Material mat;
    private final byte data;

    public String getName() {
        return ConfigHelper.color(name);
    }

    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(mat, 1, data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(getName());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
