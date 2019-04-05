package yichen.extraalchemy.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;

import java.util.ArrayList;
import java.util.List;


public class ModDissoventRecipes {

    public static List<RecipeDissovent> goldNuggetRecipe;

    public static void init() {
        goldNuggetRecipe = new ArrayList<>();
        //ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT, 1), "ingotGlod", 200));
        goldNuggetRecipe.add(ExtraAlchemyAPI.registerDissoventRecipe(new ItemStack(Items.GOLD_NUGGET), new ItemStack(Blocks.COBBLESTONE), 0.1f));

    }
}
