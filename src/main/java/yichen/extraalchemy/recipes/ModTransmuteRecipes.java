package yichen.extraalchemy.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute;

import java.util.ArrayList;
import java.util.List;


public class ModTransmuteRecipes {

    public static List<RecipeTransmute> ironIngotRecipe;

    public static RecipeTransmute clayball;
    public static RecipeTransmute coal;

    public static void init() {
        ironIngotRecipe = new ArrayList<>();
        //ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT), "ingotGlod", 200));
        //ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), 100));

        //clayball = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.CLAY_BALL), new ItemStack(Blocks.DIRT), 100);
        //coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL, 1, 1), "logWood", 800);
        //coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL,1,1), "ingotAlubrass", 200);
        init_ore();
    }

    public static void init_ore()
    {
        ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe("ingotGold", "ingotIron", 100));
    }
}
