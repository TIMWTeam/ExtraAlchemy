package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static yichen.extraalchemy.ExtraAlchemy.log;

public class RecipeTransmute_ore {

    private final String output;
    private final String input;
    private final int time;

    public RecipeTransmute_ore(String output_ore, String input_ore, int time) {
        this.output = output_ore;
        this.input = input_ore;
        this.time = time;
    }

    public boolean matches(ItemStack stack) {
        return matches.matches_ore(stack, input);
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }

}