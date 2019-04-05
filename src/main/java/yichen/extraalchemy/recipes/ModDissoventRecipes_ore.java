package yichen.extraalchemy.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent_ore;


public class ModDissoventRecipes_ore {

    public static List<RecipeDissovent_ore> Recipe;

    public static void init() {
        Recipe = new ArrayList<>();
        //ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT, 1), "ingotGlod", 200));
        Recipe.add(ExtraAlchemyAPI.registerDissoventRecipe_ore("","", 0.1f));

    }
}
