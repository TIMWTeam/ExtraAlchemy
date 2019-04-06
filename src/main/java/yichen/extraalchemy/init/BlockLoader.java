package yichen.extraalchemy.init;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.base.blocks.BlockBloodstain;
import yichen.extraalchemy.base.blocks.alchemy_array.BlockAlchemyArrayTransmute;
import yichen.extraalchemy.base.blocks.alchemy_array.TESRAlchemyArrayTransmute;
import yichen.extraalchemy.base.blocks.alchemy_array.TileAlchemyArrayTransmute;
import yichen.extraalchemy.base.blocks.fluid.BlockAlchemicalDissovent;

@Mod.EventBusSubscriber(modid = ExtraAlchemy.MODID)
public class BlockLoader {

    public static final Block blockBloodstain = new BlockBloodstain();
    public static final Block blockAlchemyArrayTransmute = new BlockAlchemyArrayTransmute();
    public static Block dissovent;

    // 调用注册方法
    public BlockLoader(FMLPreInitializationEvent event) {
    	Fluid dissovent_fluid = BlockAlchemicalDissovent.getAlchemicalDissovent();
        FluidRegistry.registerFluid(dissovent_fluid);
        dissovent = new BlockAlchemicalDissovent();
        FluidRegistry.addBucketForFluid(FluidRegistry.getFluid(dissovent_fluid.getName()).setBlock(dissovent));
        
        //注册方块
        register(dissovent);
        register(blockBloodstain);
        //注册方块及物品
        registerItemBlock(blockAlchemyArrayTransmute);
        //注册实体
        registerTileEntities();
    }
    // 注册渲染贴图
    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        registerRender(blockBloodstain);
        registerRender(blockAlchemyArrayTransmute);

        registerTESR();
        registerFluidModels(dissovent);
    }

    // 注册实体
    private static void registerTileEntities() {
        TileEntity.register(ExtraAlchemy.MODID + ":alchemy_array_transmute", TileAlchemyArrayTransmute.class);
    }
    // 注册特殊渲染
    @SideOnly(Side.CLIENT)
    public static void registerTESR() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyArrayTransmute.class, new TESRAlchemyArrayTransmute());
    }

    // 注册方块
    private static void register(Block block) {
        ForgeRegistries.BLOCKS.register(block);
    }

    // 注册方块及物品
    private static void registerItemBlock(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }


    // 注册流体贴图
    @SideOnly(Side.CLIENT)
    public static void registerFluidModels(Block block) {
        final ResourceLocation location = block.getRegistryName();
        ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(ExtraAlchemy.MODID + ":fluid", location.getResourcePath());
            }
        });
    }

    // 注册方块贴图
    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block) {
        registerRender(block, 0);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block, int meta) {
        ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, model);
    }

}
