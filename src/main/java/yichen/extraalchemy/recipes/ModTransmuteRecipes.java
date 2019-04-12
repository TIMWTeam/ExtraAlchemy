package yichen.extraalchemy.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute;


public class ModTransmuteRecipes {

    public static RecipeTransmute ironIngotRecipe;
    public static RecipeTransmute clayball;
    public static RecipeTransmute coal;
    public static RecipeTransmute ingotAlubrass;

    public static void init() {
        //输出-输入-时间（tick）
        ironIngotRecipe = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), 100);
        clayball = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.CLAY_BALL), new ItemStack(Blocks.DIRT), 100);
        coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL, 1, 1), "logWood", 800);
        ingotAlubrass = ExtraAlchemyAPI.registerTransmuteRecipe("ingotCopper", "ingotAlubrass", 100);


    }
}
