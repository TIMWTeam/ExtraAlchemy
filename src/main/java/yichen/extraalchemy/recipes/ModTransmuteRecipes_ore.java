package yichen.extraalchemy.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute;
import yichen.extraalchemy.api.RecipeTransmute_ore;

import java.util.ArrayList;
import java.util.List;

import static yichen.extraalchemy.ExtraAlchemy.log;


public class ModTransmuteRecipes_ore {

    public static List<RecipeTransmute> Recipe;

    public static void init() {
        Recipe = new ArrayList<>();
        RecipeTransmute test;
        test = ExtraAlchemyAPI.registerTransmuteRecipe("ingotGold", "ingotIron", 100);

        if (test != null) {
            log.info("注册矿词"+test.getInput() +"->"+test.getOutput());
            Recipe.add(test);
        }
    }
}
