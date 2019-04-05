package yichen.extraalchemy.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

import static yichen.extraalchemy.ExtraAlchemy.log;

public class RecipeTransmute_ore {

    private final String output;
    private final String input;
    private final int time;

    public RecipeTransmute_ore(String output_ore, String input_ore, int time) {
        this.output = output_ore;
        this.input = input_ore;
        this.time = time;
    }

    public boolean matches(ItemStack stack) {
        if(OreDictionary.getOreID(output) == 0 && OreDictionary.getOreID(input) == 0)
        {
            log.error("错误的矿物词典，请检查");
        }
        else
            {
            for(ItemStack ostack : OreDictionary.getOres(input, false)) {
                if(OreDictionary.itemMatches(ostack, stack, false))
                    return true;
            }
        }
        return false;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int getTime() {
        return time;
    }

}