package yichen.extraalchemy.api;

import com.google.common.base.Preconditions;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ExtraAlchemyAPI {

    public static final List<RecipeTransmute> transmuteRecipes = new ArrayList<>();
    public static final List<RecipeTransmute_ore> transmuteRecipes_ore = new ArrayList<>();
    public static final List<RecipeDissovent> dissoventRecipes = new ArrayList<>();
    public static final List<RecipeDissovent_ore> RecipeDissovent_ore = new ArrayList<>();

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
        RecipeTransmute recipe = new RecipeTransmute(output, input, time);
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
    public static RecipeTransmute_ore registerTransmuteRecipe_ore(String out_ore, String in_ore, int time) {
        Preconditions.checkArgument(time <= 10000);
        RecipeTransmute_ore recipe = new RecipeTransmute_ore(out_ore, in_ore, time);
        transmuteRecipes_ore.add(recipe);
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
     */
    public static RecipeDissovent_ore registerDissoventRecipe_ore(String out_ore, String in_ore, float chance) {
        Preconditions.checkArgument(chance <= 1);
        RecipeDissovent_ore recipe = new RecipeDissovent_ore(out_ore, in_ore, chance);
        RecipeDissovent_ore.add(recipe);
        return recipe;
    }


}
