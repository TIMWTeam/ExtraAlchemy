package yichen.extraalchemy.recipes;

import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute_ore;

import java.util.ArrayList;
import java.util.List;

import static yichen.extraalchemy.ExtraAlchemy.log;


public class ModTransmuteRecipes_ore {

    public static List<RecipeTransmute_ore> Recipe;

    public static void init() {
        Recipe = new ArrayList<>();
        RecipeTransmute_ore test;
        test = ExtraAlchemyAPI.registerTransmuteRecipe_ore("ingotGold", "ingotIron", 100);

        if (test != null) {
            log.info("注册矿词"+test.getInput() +"->"+test.getOutput());
            Recipe.add(test);
        }
    }
}
