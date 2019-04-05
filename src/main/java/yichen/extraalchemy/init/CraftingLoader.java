package yichen.extraalchemy.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CraftingLoader {

	public CraftingLoader() {
		registerRecipe();
		registerSmelting();
		registerFuel();
		registerOreDict();
	}

	// 注册合成配方
	private static void registerRecipe() {

	}

	// 注册熔炉配方
	private static void registerSmelting() {

	}

	// 注册燃料
	private static void registerFuel() {

	}

	public static void registerOreDict() {
		// OreDictionary.registerOre("dustIron", ComponentTypes.SAND_IRON.getStack());
	}
}
