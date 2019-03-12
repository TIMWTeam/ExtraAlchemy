package yichen.extraalchemy.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;


public class ModDissoventRecipes {

public static List<RecipeDissovent> goldNuggetRecipe;

	public static void init() {
		goldNuggetRecipe = new ArrayList<>();
		//ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT, 1), "ingotGlod", 200));
		goldNuggetRecipe.add(ExtraAlchemyAPI.registerDissoventRecipe(new ItemStack(Items.GOLD_NUGGET, 1), new ItemStack(Blocks.COBBLESTONE, 1), 0.1f));
		
	}
}
