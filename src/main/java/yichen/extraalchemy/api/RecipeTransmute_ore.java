package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static yichen.extraalchemy.ExtraAlchemy.log;

public class RecipeTransmute_ore {

    private final Object output;
    private final Object input;
    private final int time;

    public RecipeTransmute_ore(Object output_ore, Object input_ore, int time) {
        this.output = output_ore;
        this.input = input_ore;
        this.time = time;
    }

    public boolean matches(ItemStack stack) {
        return matches.matches(stack, input);
    }

    public Object getInput() {
        return input;
    }

    public Object getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }

}