package yichen.extraalchemy.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yichen.extraalchemy.config.ConfigLoader;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.init.CraftingLoader;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.recipes.ModDissoventRecipes;
import yichen.extraalchemy.recipes.ModTransmuteRecipes;

public class CommonProxy {


    public void preInit(FMLPreInitializationEvent event) {
        //注册物品，方块等
        new ConfigLoader(event);
        new ItemLoader(event);
        new BlockLoader(event);
    }

    public void init(FMLInitializationEvent event) {
        new CraftingLoader();
        //注册合成表
        ModTransmuteRecipes.init();
        ModDissoventRecipes.init();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

    public void registerRenderers() {

    }


}
