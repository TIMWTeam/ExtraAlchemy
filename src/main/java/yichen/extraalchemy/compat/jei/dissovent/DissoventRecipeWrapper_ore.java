package yichen.extraalchemy.compat.jei.dissovent;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import yichen.extraalchemy.api.RecipeDissovent;
import yichen.extraalchemy.api.RecipeDissovent_ore;

import javax.annotation.Nonnull;
import java.util.List;

public class DissoventRecipeWrapper_ore implements IRecipeWrapper {

    private final List<List<ItemStack>> input;
    private final ItemStack output;
    private final float chance;

    public DissoventRecipeWrapper_ore(RecipeDissovent_ore recipe) {

        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();

        builder.add(OreDictionary.getOres(recipe.getInput()));

        input = builder.build();
        output = OreDictionary.getOres(recipe.getOutput()).get(0);
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
