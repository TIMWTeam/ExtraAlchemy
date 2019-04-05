package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeTransmute {

    private final ItemStack output;
    private final Object input;
    private final int time;

    public RecipeTransmute(ItemStack output, Object input, int time) {
        this.output = output;
        this.input = input;
        this.time = time;
    }

    public boolean matches(ItemStack stack) {
        return matches.matches(stack, input);
    }

    public Object getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }

}