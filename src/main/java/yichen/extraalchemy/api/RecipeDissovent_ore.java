package yichen.extraalchemy.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.List;

import static yichen.extraalchemy.ExtraAlchemy.log;

public class RecipeDissovent_ore {

    private final String output;
    private final String input;
    private final float chance;

    private ItemStack outitem;

    public RecipeDissovent_ore(String out_ore, String in_ore, float chance) {
        this.output = out_ore;
        this.input = in_ore;
        this.chance = chance;
    }

    /**
     * 咱不可用
     * @param stack
     * @return
     */
    public boolean matches(ItemStack stack) {
        if(OreDictionary.getOreID(input) == 0 && OreDictionary.getOreID(output) == 0)
        {
            log.error("错误的矿物词典，请检查");
            return false;
        }
        else
        {
            for(ItemStack outitem : OreDictionary.getOres(output, false)) {
                if(OreDictionary.itemMatches(outitem, stack, false))
                    return true;
            }
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

    public float getChance() {
        return chance;
    }



}