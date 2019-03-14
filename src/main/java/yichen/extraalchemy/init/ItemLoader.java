package yichen.extraalchemy.init;


import java.util.List;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.base.entity.EntityEssenceFire;
import yichen.extraalchemy.base.entity.EntityEssenceWater;
import yichen.extraalchemy.base.entity.EntityEssenceWind;
import yichen.extraalchemy.base.items.ItemAlchemicalDissovent;
import yichen.extraalchemy.base.items.ItemBloodBottle;
import yichen.extraalchemy.base.items.ItemCoalDust;
import yichen.extraalchemy.base.items.ItemDagger;
import yichen.extraalchemy.base.items.ItemEssenceEarth;
import yichen.extraalchemy.base.items.ItemEssenceFire;
import yichen.extraalchemy.base.items.ItemEssenceLife;
import yichen.extraalchemy.base.items.ItemEssenceWater;
import yichen.extraalchemy.base.items.ItemEssenceWind;
import yichen.extraalchemy.base.items.block.ItemAlchemyArrayTransmute;

public class ItemLoader {

	public static List<Item> items;
	
	public static Item itemCoalDust = new ItemCoalDust().setUnlocalizedName(ExtraAlchemy.MODID +".coal_dust");
	public static Item itemDagger = new ItemDagger().setUnlocalizedName(ExtraAlchemy.MODID +".dagger");
	public static Item itembloodbottle = new ItemBloodBottle().setUnlocalizedName(ExtraAlchemy.MODID +".blood_bottle");
	public static Item itemTransmuteDust = new ItemAlchemyArrayTransmute().setUnlocalizedName(ExtraAlchemy.MODID +".transmute_dust");
	public static Item itemAlchemicalDissovent = new ItemAlchemicalDissovent().setUnlocalizedName(ExtraAlchemy.MODID +".item_alchemical_dissovent");
	public static Item itemEssenceLife = new ItemEssenceLife().setUnlocalizedName(ExtraAlchemy.MODID +".essence_life");
	public static Item itemEssenceEarth = new ItemEssenceEarth().setUnlocalizedName(ExtraAlchemy.MODID +".essence_earth");
	public static Item itemEssenceWind = new ItemEssenceWind().setUnlocalizedName(ExtraAlchemy.MODID +".essence_wind");
	public static Item itemEssenceWater = new ItemEssenceWater().setUnlocalizedName(ExtraAlchemy.MODID +".essence_water");
	public static Item itemEssenceFire = new ItemEssenceFire().setUnlocalizedName(ExtraAlchemy.MODID +".essence_fire");


	public ItemLoader(FMLPreInitializationEvent event)
    {
		register(itemCoalDust, "coal_dust");
		register(itemDagger, "dagger");
		register(itembloodbottle, "blood_bottle");
		register(itemTransmuteDust, "transmute_dust");
		register(itemAlchemicalDissovent, "item_alchemical_dissovent");
		register(itemEssenceLife, "essence_life");
		register(itemEssenceEarth, "essence_earth");
		register(itemEssenceWater, "essence_water");
		
		register(itemEssenceWind, "essence_wind");
		register(itemEssenceFire, "essence_fire");
		
		registerItemAndEntity();
    }
    //注册贴图
	@SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerRender(itemCoalDust);
        registerRender(itemDagger);
        registerRender(itembloodbottle);
        registerRender(itemTransmuteDust);

        registerRender(itemAlchemicalDissovent);
        registerRender(itemEssenceLife);
        registerRender(itemEssenceEarth);
        registerRender(itemEssenceWind);
        registerRender(itemEssenceWater);
        registerRender(itemEssenceFire);
    }
	//注册实体投掷物贴图
	public static void registerItemAndEntity()
    {
		int i = 0;
		EntityRegistry.registerModEntity(makeName("essence_fire"), EntityEssenceFire.class, ExtraAlchemy.MODID+":essence_fire", i++, ExtraAlchemy.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("essence_wind"), EntityEssenceWind.class, ExtraAlchemy.MODID+":essence_wind", i++, ExtraAlchemy.instance, 64, 10, true);
		EntityRegistry.registerModEntity(makeName("essence_water"), EntityEssenceWater.class, ExtraAlchemy.MODID+":essence_water", i++, ExtraAlchemy.instance, 64, 10, true);
	}
	private static ResourceLocation makeName(String name) {
		return new ResourceLocation(ExtraAlchemy.MODID, name);
	}

	//注册物品
    private static void register(Item item, String name)
    {
    	ForgeRegistries.ITEMS.register(item.setRegistryName(name));
    }

    //注册贴图
	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item){
        registerRender(item, 0);
	}
	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item,int meta){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
	}
	
}
