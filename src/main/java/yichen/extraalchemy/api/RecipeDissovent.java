package yichen.extraalchemy.api;

import net.minecraft.item.ItemStack;
import yichen.extraalchemy.util.Matches;

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
       return Matches.itemStackMatches(stack,input);
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