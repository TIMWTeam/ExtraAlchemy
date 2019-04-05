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
        ItemStack out = null;
        if (output instanceof ItemStack) {
            out = (ItemStack) output;
        } else if (output instanceof String) {
            if (OreDictionary.doesOreNameExist((String) output)) {
                out = OreDictionary.getOres((String) output).get(0);
            } else {
                return null;
            }
        }
        if (!(input instanceof ItemStack)) {
            if (!OreDictionary.doesOreNameExist((String) input)) {
                return null;
            }
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
    public static RecipeDissovent registerDissoventRecipe(Object output, Object input, float chance) {
        Preconditions.checkArgument(chance <= 1);
        ItemStack out = null;
        if (output instanceof ItemStack) {
            out = (ItemStack) output;
        } else if (output instanceof String) {
            if (OreDictionary.doesOreNameExist((String) output)) {
                NonNullList<ItemStack> a = OreDictionary.getOres((String) output);
                if (a.isEmpty()) {
                    return null;
                }
                out = a.get(0);
            } else {
                return null;
            }
        }
        if (!(input instanceof ItemStack)) {
            if (!OreDictionary.doesOreNameExist((String) input)) {
                return null;
            }
        }
        RecipeDissovent recipe = new RecipeDissovent(out, input, chance);
        dissoventRecipes.add(recipe);
        return recipe;
    }


}
