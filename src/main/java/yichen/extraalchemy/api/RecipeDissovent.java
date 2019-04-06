package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeDissovent {

    private final Object input;
    private final ItemStack output;
    private final ItemStack secondary;
    private final float secChance;
    private final float chance;


    public RecipeDissovent(Object input,ItemStack output,int outNum,ItemStack secondary,int secNum,float secChance,  float chance) {
        this.input = input;
        this.output = output;
        this.secondary = secondary;
        this.output.setCount(outNum);
        if(secondary!=null) {
        	this.secondary.setCount(secNum);
        }
        this.secChance = secChance;
        this.chance = chance;
    }

    public boolean matches(ItemStack stack) {
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

    public Object getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getSecondary() {
        return secondary;
    }

    public float getSecChance() {
        return secChance;
    }
    
    public float getChance() {
        return chance;
    }


}