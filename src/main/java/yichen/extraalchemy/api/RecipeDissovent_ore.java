package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static yichen.extraalchemy.ExtraAlchemy.log;

public class RecipeDissovent_ore {

    private final String output;
    private final String input;
    private final float chance;

    private ItemStack outitem;

    public RecipeDissovent_ore(String out_ore, String in_ore, float chance) {
        this.output = out_ore;
        this.input = in_ore;
        this.chance = chance;
    }

    public boolean matches(ItemStack stack) {
        return matches.matches(stack, input);
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public float getChance() {
        return chance;
    }


}