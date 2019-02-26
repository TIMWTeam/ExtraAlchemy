package yichen.extraalchemy.compat.jei;


import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;


import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.IRecipesGui;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.compat.jei.transmute.TransmuteRecipeCategory;
import yichen.extraalchemy.compat.jei.transmute.TransmuteRecipeWrapper;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.recipes.transmute.RecipeTransmute;


@JEIPlugin
public class ModIntegrationJEI implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(
				//new XXXX(registry.getJeiHelpers().getGuiHelper()),
				new TransmuteRecipeCategory(registry.getJeiHelpers().getGuiHelper())
		);
	}
	
	public static boolean doesOreExist(String key) {
		return OreDictionary.doesOreNameExist(key)&& OreDictionary.getOres(key).stream().anyMatch(s -> s.getItem() instanceof ItemBlock);
	}
	
	@Override
	public void register(@Nonnull IModRegistry registry) {
		
		registry.handleRecipes(RecipeTransmute.class, TransmuteRecipeWrapper::new, TransmuteRecipeCategory.UID);
		
		registry.addRecipes(ExtraAlchemyAPI.transmuteRecipes, TransmuteRecipeCategory.UID);

		registry.addRecipeCatalyst(new ItemStack(BlockLoader.blockAlchemyArrayTransmute), TransmuteRecipeCategory.UID);

	}
	
	
	

}
