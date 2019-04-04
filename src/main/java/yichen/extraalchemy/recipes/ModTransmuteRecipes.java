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

public static RecipeTransmute clayball;
public static RecipeTransmute coal;

	public static void init() {
		ironIngotRecipe = new ArrayList<>();
		//ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT), "ingotGlod", 200));
		ironIngotRecipe.add(ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT), 100));

		clayball = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.CLAY_BALL), new ItemStack(Blocks.DIRT), 100);
		coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL,1,1), "logWood", 800);
		//coal = ExtraAlchemyAPI.registerTransmuteRecipe(new ItemStack(Items.COAL,1,1), "ingotAlubrass", 200);
		
	}
}
