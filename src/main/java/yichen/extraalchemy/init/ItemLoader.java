package yichen.extraalchemy.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.base.entity.EntityEssenceFire;
import yichen.extraalchemy.base.entity.EntityEssenceWater;
import yichen.extraalchemy.base.entity.EntityEssenceWind;
import yichen.extraalchemy.base.items.*;
import yichen.extraalchemy.base.items.block.ItemAlchemyArrayTransmute;

import java.util.List;

public class ItemLoader {

    public static List<Item> items;

    public static final Item itemCoalDust = new ItemCoalDust();
    public static final Item itemDagger = new ItemDagger();
    public static final Item itembloodbottle = new ItemBloodBottle();
    public static final Item itemAirBottle = new ItemAirBottle();
    public static final Item itemTransmuteDust = new ItemAlchemyArrayTransmute();
    public static final Item itemAlchemicalDissovent = new ItemAlchemicalDissovent();
    public static final Item itemEssenceLife = new ItemEssenceLife();
    public static final Item itemEssenceEarth = new ItemEssenceEarth();
    public static final Item itemEssenceWind = new ItemEssenceWind();
    public static final Item itemEssenceWater = new ItemEssenceWater();
    public static final Item itemEssenceFire = new ItemEssenceFire();

    public ItemLoader(FMLPreInitializationEvent event) {
        register(itemDagger);
        register(itembloodbottle);
        register(itemAirBottle);
        register(itemTransmuteDust);
        register(itemAlchemicalDissovent);
        register(itemEssenceLife);

        register(itemEssenceEarth);
        register(itemEssenceWater);
        register(itemEssenceWind);
        register(itemEssenceFire);

        registerItemAndEntity();
    }

    // 注册贴图
    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerRender(itemAirBottle);
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

    // 注册实体投掷物
    public static void registerItemAndEntity() {
        int i = 0;
        EntityRegistry.registerModEntity(makeName("essence_fire"), EntityEssenceFire.class,
                ExtraAlchemy.MODID + ":essence_fire", i++, ExtraAlchemy.instance, 64, 10, true);
        EntityRegistry.registerModEntity(makeName("essence_wind"), EntityEssenceWind.class,
                ExtraAlchemy.MODID + ":essence_wind", i++, ExtraAlchemy.instance, 64, 10, true);
        EntityRegistry.registerModEntity(makeName("essence_water"), EntityEssenceWater.class,
                ExtraAlchemy.MODID + ":essence_water", i++, ExtraAlchemy.instance, 64, 10, true);
    }

    private static ResourceLocation makeName(String name) {
        return new ResourceLocation(ExtraAlchemy.MODID, name);
    }

    // 注册物品
    private static void register(Item item) {
        ForgeRegistries.ITEMS.register(item);
    }

    // 注册贴图
    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item) {
        registerRender(item, 0);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item, int meta) {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

}
