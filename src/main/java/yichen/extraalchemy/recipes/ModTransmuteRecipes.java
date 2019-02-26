package yichen.extraalchemy.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute;;


public class ModTransmuteRecipes {

public static List<RecipeTransmute> ironIngotRecipe;

	public static void init() {
		ironIngotRecipe = new ArrayList<>();
		ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT, 1), "ingotGlod", 200));
		ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT, 1), new ItemStack(Items.GOLD_INGOT, 1), 200));
		
	}
}
