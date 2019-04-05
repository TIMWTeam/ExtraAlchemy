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

	public Object getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public int getTime() {
		return time;
	}

}