package yichen.extraalchemy.init;


import java.util.List;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.items.ItemDagger;
import yichen.extraalchemy.items.ItemAlchemyArrayTransmute;
import yichen.extraalchemy.items.ItemBloodBottle;
import yichen.extraalchemy.items.ItemCoalDust;

public class ItemLoader {
	public static List<Item> items;
	
	public static Item itemCoalDust = new ItemCoalDust().setUnlocalizedName(ExtraAlchemy.MODID +".coal_dust");
	public static Item itemDagger = new ItemDagger().setUnlocalizedName(ExtraAlchemy.MODID +".dagger");
	public static Item itembloodbottle = new ItemBloodBottle().setUnlocalizedName(ExtraAlchemy.MODID +".blood_bottle");
	public static Item itemTransmuteDust = new ItemAlchemyArrayTransmute().setUnlocalizedName(ExtraAlchemy.MODID +".transmute_dust");


	public ItemLoader(FMLPreInitializationEvent event)
    {
		register(itemCoalDust, "coal_dust");
		register(itemDagger, "dagger");
		register(itembloodbottle, "blood_bottle");
		register(itemTransmuteDust, "transmute_dust");
    }
    //注册贴图
	@SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(itemCoalDust);
        registerRender(itemDagger);
        registerRender(itembloodbottle);
        registerRender(itemTransmuteDust);
    }

	//注册物品
    private static void register(Item item, String name)
    {
    	ForgeRegistries.ITEMS.register(item.setRegistryName(name));
    }
    
    //注册贴图
	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
	}
	
}
