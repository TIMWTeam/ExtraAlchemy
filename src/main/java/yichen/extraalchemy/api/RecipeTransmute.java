package yichen.extraalchemy.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeTransmute {

	private final ItemStack output;
	private final Object input;
	private final int time;
	private @Nullable IBlockState catalystState;

	public static IBlockState alchemyState;
	public static IBlockState conjurationState;

	public RecipeTransmute(ItemStack output, Object input, int time) {
		this.output = output;
		this.input = input;
		this.time = time;
	}

	public boolean matches(ItemStack stack) {
		if(input instanceof ItemStack) {
			return OreDictionary.itemMatches((ItemStack) input, stack, false);
		}

		if(input instanceof String) {
			for(ItemStack ostack : OreDictionary.getOres((String) input, false)) {
				if(OreDictionary.itemMatches(ostack, stack, false))
					return true;
			}
		}
		return false;
	}

	public IBlockState getCatalyst() {
		return catalystState;
	}

	public void setCatalyst(IBlockState catalyst) {
		catalystState = catalyst;
	}

	public Object getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public int getTime() {
		return time;
	}

	/**
	 * @deprecated Use {@link RecipeTransmute#setCatalyst(IBlockState)} instead
	 */
	@Deprecated
	public void setAlchemy(boolean alchemy) {
		catalystState = alchemy ? alchemyState : null;
	}

	/**
	 * @deprecated Use {@link RecipeTransmute#getCatalyst()} instead
	 */
	@Deprecated
	public boolean isAlchemy() {
		return catalystState == alchemyState;
	}

	/**
	 * @deprecated Use {@link RecipeTransmute#setCatalyst(IBlockState)} instead
	 */
	@Deprecated
	public void setConjuration(boolean conjuration) {
		catalystState = conjuration ? conjurationState : null;
	}

	/**
	 * @deprecated Use {@link RecipeTransmute#getCatalyst()} instead
	 */
	@Deprecated
	public boolean isConjuration() {
		return catalystState == conjurationState;
	}
}