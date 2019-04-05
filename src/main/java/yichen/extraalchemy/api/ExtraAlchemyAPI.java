package yichen.extraalchemy.api;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

import static yichen.extraalchemy.ExtraAlchemy.log;

public final class ExtraAlchemyAPI {

    public static final List<RecipeTransmute> transmuteRecipes = new ArrayList<>();
    public static final List<RecipeDissovent> dissoventRecipes = new ArrayList<>();

    private static boolean haveore(String a) {
        if (!OreDictionary.doesOreNameExist(a)) {
            log.error("没有矿物词典" + a);
            return false;
        } else
            return true;
    }

    /**
     * Registers a Transmute Recipe
     *
     * @param output The ItemStack to craft
     * @param input  The input item, be it an ItemStack or an ore dictionary entry String.
     * @param time   The amount of time required. Don't go over 100000!
     * @return The recipe created.
     */
    public static RecipeTransmute registerTransmuteRecipe(ItemStack output, Object input, int time) {
        Preconditions.checkArgument(time <= 10000);
        ItemStack in = null;
        if(input instanceof String) {
        	if (OreDictionary.doesOreNameExist((String) input)) {
        		ItemStack a = OreDictionary.getOres((String) input).get(0);
            }else {
				in = (ItemStack) input;
			}
        }
        RecipeTransmute recipe = new RecipeTransmute(output, in, time);
        transmuteRecipes.add(recipe);
        return recipe;
    }

    /**
     * Registers a Transmute Recipe
     *
     * @param out_ore The Output dictionary name
     * @param in_ore  The Input ore dictionary entry String.
     * @param time    The amount of time required. Don't go over 100000!
     * @return The recipe created.
     */
    public static RecipeTransmute registerTransmuteRecipe_ore(String out_ore, String in_ore, int time) {
        if (haveore(out_ore) == true && haveore(in_ore) == true) {
            return registerTransmuteRecipe(OreDictionary.getOres(out_ore).get(0),in_ore,time);
        }else return null;
    }

    /**
     * Registers a Dissovent Recipe
     *
     * @param output The ItemStack to craft
     * @param input  The input item, be it an ItemStack or an ore dictionary entry String.
     * @param chance The chance of obtaining a output. Don't go over 1!
     * @return The recipe created.
     */
    public static RecipeDissovent registerDissoventRecipe(ItemStack output, Object input, float chance) {
        Preconditions.checkArgument(chance <= 1);
        RecipeDissovent recipe = new RecipeDissovent(output, input, chance);
        dissoventRecipes.add(recipe);
        return recipe;
    }

    /**
     * Registers a Dissovent Recipe
     *
     * @param out_ore The Output dictionary name
     * @param in_ore  The Input ore dictionary entry String.
     * @param chance  The chance of obtaining a output. Don't go over 1!
     * @return The recipe created.
     *
    public static RecipeDissovent_ore registerDissoventRecipe_ore(String out_ore, String in_ore, float chance) {
        if (haveore(out_ore) == true && haveore(in_ore) == true) {
            Preconditions.checkArgument(chance <= 1);
            RecipeDissovent_ore recipe = new RecipeDissovent_ore(out_ore, in_ore, chance);
            dissoventRecipes_ore.add(recipe);
            return recipe;
        } else return null;
    }
    */
}
