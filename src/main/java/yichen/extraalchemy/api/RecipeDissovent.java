package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeDissovent {

    private final ItemStack output;
    private final Object input;
    private final float chance;


    public RecipeDissovent(ItemStack output, Object input, float chance) {
        this.output = output;
        this.input = input;
        this.chance = chance;
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

    public float getChance() {
        return chance;
    }


}