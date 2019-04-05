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

public static RecipeDissovent goldNuggetRecipe;
public static RecipeDissovent oreGold;
public static RecipeDissovent oreIron;
public static RecipeDissovent oreCopper;
public static RecipeDissovent oreTin;
public static RecipeDissovent orePlatinum;
public static RecipeDissovent oreSilver;
public static RecipeDissovent oreLead;
public static RecipeDissovent oreAluminium;
public static RecipeDissovent oreNickel;

	public static void init() {
		//输出-输入-几率(每秒2次机会，有20tick间隔)
		goldNuggetRecipe = ExtraAlchemyAPI.registerDissoventRecipe(new ItemStack(Items.GOLD_NUGGET), new ItemStack(Blocks.COBBLESTONE), 0.1f);
		oreGold = ExtraAlchemyAPI.registerDissoventRecipe("dustGold", "oreGold", 0.1f);
		oreIron = ExtraAlchemyAPI.registerDissoventRecipe("dustIron", "oreIron", 0.1f);
		oreCopper = ExtraAlchemyAPI.registerDissoventRecipe("dustCopper", "oreCopper", 0.1f);
		oreTin = ExtraAlchemyAPI.registerDissoventRecipe("dustTin", "oreTin", 0.1f);
		orePlatinum = ExtraAlchemyAPI.registerDissoventRecipe("dustPlatinum", "orePlatinum", 0.1f);
		oreSilver = ExtraAlchemyAPI.registerDissoventRecipe("dustSilver", "oreSilver", 0.1f);
		oreLead = ExtraAlchemyAPI.registerDissoventRecipe("dustLead", "oreLead", 0.1f);
		oreAluminium = ExtraAlchemyAPI.registerDissoventRecipe("dustAluminium", "oreAluminium", 0.1f);
		oreNickel = ExtraAlchemyAPI.registerDissoventRecipe("dustNickel", "oreNickel", 0.1f);
	}
}
