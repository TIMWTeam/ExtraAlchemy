package yichen.extraalchemy.compat.jei.dissovent;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import yichen.extraalchemy.compat.jei.ModIntegrationJEI;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.api.RecipeDissovent;

public class DissoventRecipeWrapper implements IRecipeWrapper {

	private final List<List<ItemStack>> input;
	private final ItemStack output;
	private final float chance;

    public DissoventRecipeWrapper(RecipeDissovent  recipe) {
        
    	ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();

		if(recipe.getInput() instanceof ItemStack) {
			builder.add(ImmutableList.of((ItemStack) recipe.getInput()));
		} else if(recipe.getInput() instanceof String) {
			builder.add(OreDictionary.getOres((String) recipe.getInput()));
		}

		input = builder.build();
		output = recipe.getOutput();
		chance = recipe.getChance();
    }

    @Override
	public void getIngredients(@Nonnull IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}

	@Nonnull
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return ImmutableList.of();
	}

	@Override
	public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		return false;
	}
}