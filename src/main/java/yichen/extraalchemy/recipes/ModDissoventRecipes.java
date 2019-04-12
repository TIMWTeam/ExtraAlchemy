package yichen.extraalchemy.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;


public class ModDissoventRecipes {

    public static RecipeDissovent goldNuggetRecipe;
    public static RecipeDissovent oreGold;
    public static RecipeDissovent oreIron;
    public static RecipeDissovent oreCopper;
    public static RecipeDissovent oreTin;
    public static RecipeDissovent orePlatinum;
    public static RecipeDissovent oreSilver;
    public static RecipeDissovent oreLead;
    public static RecipeDissovent oreAluminium;
    public static RecipeDissovent oreNickel;

    public static void init() {
    	//工作几率:10tick/次
        //输入-输出-工作几率
        //输入-输出-输出数量-工作几率
        //输入-输出-输出数量-副产物-副产物数量-副产物几率-工作几率
        goldNuggetRecipe = ExtraAlchemyAPI.registerDissoventRecipe(new ItemStack(Blocks.COBBLESTONE), new ItemStack(Items.GOLD_NUGGET), 0.1f);
        oreGold = ExtraAlchemyAPI.registerDissoventRecipe("oreGold", "dustGold",1,"dustGold",1,0.5f, 0.1f);
        oreIron = ExtraAlchemyAPI.registerDissoventRecipe("oreIron", "dustIron",1,"dustIron",1,0.5f, 0.1f);
        oreCopper = ExtraAlchemyAPI.registerDissoventRecipe("oreCopper", "dustCopper",1,"dustCopper",1,0.5f, 0.1f);
        oreTin = ExtraAlchemyAPI.registerDissoventRecipe("oreTin", "dustTin",1,"dustTin",1,0.5f, 0.1f);
        orePlatinum = ExtraAlchemyAPI.registerDissoventRecipe("orePlatinum", "dustPlatinum",1,"dustPlatinum",1,0.5f, 0.1f);
        oreSilver = ExtraAlchemyAPI.registerDissoventRecipe("oreSilver", "dustSilver",1,"dustSilver",1,0.5f, 0.1f);
        oreLead = ExtraAlchemyAPI.registerDissoventRecipe("oreLead", "dustLead",1,"dustLead",1,0.5f, 0.1f);
        oreAluminium = ExtraAlchemyAPI.registerDissoventRecipe("oreAluminium", "dustAluminium",1,"dustAluminium",1,0.5f, 0.1f);
        oreNickel = ExtraAlchemyAPI.registerDissoventRecipe("oreNickel", "dustNickel",1,"dustNickel",1,0.5f, 0.1f);
    }
}
