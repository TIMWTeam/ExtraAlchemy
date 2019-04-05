package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class matches {
    public static boolean matches(ItemStack stack, Object input) {
        if (input instanceof ItemStack) {
            return OreDictionary.itemMatches((ItemStack) input, stack, false);
        }
        if (input instanceof String) {
            for (ItemStack ostack : OreDictionary.getOres((String) input, false)) {
                if (OreDictionary.itemMatches(ostack, stack, false))
                    return true;
            }
        }
        return false;
    }
}
