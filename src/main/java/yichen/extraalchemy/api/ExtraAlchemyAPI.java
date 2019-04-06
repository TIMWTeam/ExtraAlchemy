package yichen.extraalchemy.api;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public final class ExtraAlchemyAPI {

    public static final List<RecipeTransmute> transmuteRecipes = new ArrayList<>();
    public static final List<RecipeDissovent> dissoventRecipes = new ArrayList<>();

    private static ItemStack outItem(Object output) {
    	if (output instanceof ItemStack) {
            return (ItemStack) output;
        } else if (output instanceof String) {
            if (OreDictionary.doesOreNameExist((String) output)) {
                NonNullList<ItemStack> a = OreDictionary.getOres((String) output);
                if (!a.isEmpty()) {
                    return a.get(0);
                }
            }
        }
		return null;
    }
    
    /**
     * Registers a Transmute Recipe
     *
     * @param output The ItemStack to craft
     * @param input  The input item, be it an ItemStack or an ore dictionary entry String.
     * @param time   The amount of time required. Don't go over 100000!
     * @return The recipe created.
     */
    public static RecipeTransmute registerTransmuteRecipe(Object output, Object input, int time) {
        Preconditions.checkArgument(time <= 10000);
        ItemStack out = outItem(output);
        if(out == null) 
        	return null;
        if (!(input instanceof ItemStack)) {
            if (!OreDictionary.doesOreNameExist((String) input))
                return null;
        }
        RecipeTransmute recipe = new RecipeTransmute(out, input, time);
        transmuteRecipes.add(recipe);
        return recipe;
    }

    /**
     * Registers a Dissovent Recipe
     *
     * @param output The ItemStack to craft
     * @param input  The input item, be it an ItemStack or an ore dictionary entry String.
     * @param chance The chance of obtaining a output. Don't go over 1!
     * @return The recipe created.
     */
    public static RecipeDissovent registerDissoventRecipe(Object input, Object output,int outNum,Object secondary,int secNum,float secChance, float chance) {
        Preconditions.checkArgument(chance <= 1);
        ItemStack out = outItem(output);
        if(out == null) 
        	return null;
        ItemStack sec = outItem(secondary);
        if (!(input instanceof ItemStack)) {
            if (!OreDictionary.doesOreNameExist((String) input))
                return null;
        }
        RecipeDissovent recipe = new RecipeDissovent(input, out, outNum, sec, secNum, secChance, chance);
        dissoventRecipes.add(recipe);
        return recipe;
    }
    public static RecipeDissovent registerDissoventRecipe(Object input, Object output,int outNum, float chance) {
    	return registerDissoventRecipe(input,output,outNum,null,0,0.0f,chance);
    }
    public static RecipeDissovent registerDissoventRecipe(Object input, Object output, float chance) {
    	return registerDissoventRecipe(input,output,1,null,0,0.0f,chance);
    }

}
