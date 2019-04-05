package yichen.extraalchemy.compat.jei;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;
import yichen.extraalchemy.api.RecipeTransmute;
import yichen.extraalchemy.compat.jei.dissovent.DissoventRecipeCategory;
import yichen.extraalchemy.compat.jei.dissovent.DissoventRecipeWrapper;
import yichen.extraalchemy.compat.jei.transmute.TransmuteRecipeCategory;
import yichen.extraalchemy.compat.jei.transmute.TransmuteRecipeWrapper;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.init.ItemLoader;

import javax.annotation.Nonnull;


@JEIPlugin
public class ModIntegrationJEI implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new TransmuteRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
                new DissoventRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    public static boolean doesOreExist(String key) {
        return OreDictionary.doesOreNameExist(key) && OreDictionary.getOres(key).stream().anyMatch(s -> s.getItem() instanceof ItemBlock);
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {

        registry.handleRecipes(RecipeTransmute.class, TransmuteRecipeWrapper::new, TransmuteRecipeCategory.UID);
        registry.addRecipes(ExtraAlchemyAPI.transmuteRecipes, TransmuteRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(BlockLoader.blockAlchemyArrayTransmute), TransmuteRecipeCategory.UID);


        registry.handleRecipes(RecipeDissovent.class, DissoventRecipeWrapper::new, DissoventRecipeCategory.UID);
        registry.addRecipes(ExtraAlchemyAPI.dissoventRecipes, DissoventRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ItemLoader.itemAlchemicalDissovent), DissoventRecipeCategory.UID);


    }

}
