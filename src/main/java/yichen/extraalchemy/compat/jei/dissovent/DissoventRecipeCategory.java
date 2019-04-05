package yichen.extraalchemy.compat.jei.dissovent;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import yichen.extraalchemy.ExtraAlchemy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DissoventRecipeCategory implements IRecipeCategory<DissoventRecipeWrapper> {

    public static final String UID = "extraalchemy.dissovent";
    private final IDrawable background;
    private final String localizedName;

    public DissoventRecipeCategory(IGuiHelper guiHelper) {
        localizedName = I18n.format("extraalchemy.jei.dissovent");
        background = guiHelper.drawableBuilder(new ResourceLocation(ExtraAlchemy.MODID, "textures/gui/recipeTransmute.png"), 0, 54, 90, 54).build();
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
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull DissoventRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.init(0, true, 25, 5);
        group.init(1, false, 65, 38);
        group.set(ingredients);
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
