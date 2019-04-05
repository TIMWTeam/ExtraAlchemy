package yichen.extraalchemy.recipes;

import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute_ore;

import java.util.ArrayList;
import java.util.List;


public class ModTransmuteRecipes_ore {

    public static List<RecipeTransmute_ore> Recipe;

    public static RecipeTransmute_ore clayball;
    public static RecipeTransmute_ore coal;

    public static void init() {
        Recipe = new ArrayList<>();
        //ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT), "ingotGlod", 200));
        Recipe.add(ExtraAlchemyAPI.registerTransmuteRecipe_ore("ingotGlod", "ingotIron", 100));

        //clayball = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.CLAY_BALL), new ItemStack(Blocks.DIRT), 100);
        //coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL,1,1), "logWood", 800);
        //coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL,1,1), "ingotAlubrass", 200);

    }
}
