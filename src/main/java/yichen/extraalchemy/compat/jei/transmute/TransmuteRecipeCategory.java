package yichen.extraalchemy.compat.jei.transmute;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.compat.jei.ModIntegrationJEI;
import yichen.extraalchemy.init.BlockLoader;

public class TransmuteRecipeCategory implements IRecipeCategory<TransmuteRecipeWrapper> {

	public static final String UID = "extraalchemy.transmute";
	private final IDrawable background;
	private final String localizedName;
	private final IDrawable overlay;
	private final ItemStack renderStack = new ItemStack(BlockLoader.blockAlchemyArrayTransmute);

    public TransmuteRecipeCategory(IGuiHelper guiHelper) {

		background = guiHelper.createBlankDrawable(126, 64);
		localizedName = I18n.format("botania.nei.manaPool");
		overlay = guiHelper.createDrawable(new ResourceLocation("extraalchemy", "textures/gui/recipeTransmute.png"), 0, 0, 116, 54);
    }
    @Nonnull
	@Override
	public String getUid() {
		return UID;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Nonnull
	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return null;
	}


	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull TransmuteRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
		int index = 0;

		recipeLayout.getItemStacks().init(index, true, 40, 12);
		recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(0));

		index++;

		if(ingredients.getInputs(VanillaTypes.ITEM).size() > 1) {
			// Has catalyst
			recipeLayout.getItemStacks().init(index, true, 20, 12);
			recipeLayout.getItemStacks().set(index, ingredients.getInputs(VanillaTypes.ITEM).get(1));
			index++;
		}

		recipeLayout.getItemStacks().init(index, true, 70, 12);
		recipeLayout.getItemStacks().set(index, renderStack);
		index++;

		recipeLayout.getItemStacks().init(index, false, 99, 12);
		recipeLayout.getItemStacks().set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	@Nonnull
	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return new ArrayList<>();
	}

	@Nonnull
	@Override
	public String getModName() {
		return ExtraAlchemy.NAME;
	}
}
