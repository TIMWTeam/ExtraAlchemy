package yichen.extraalchemy.init;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.base.blocks.BlockBloodstain;
import yichen.extraalchemy.base.blocks.alchemy_array.BlockAlchemyArrayTransmute;
import yichen.extraalchemy.base.blocks.alchemy_array.rander.TESRAlchemyArrayTransmute;
import yichen.extraalchemy.base.blocks.alchemy_array.tile.TileAlchemyArrayTransmute;
import yichen.extraalchemy.base.blocks.fluid.BlockAlchemicalDissovent;
import yichen.extraalchemy.base.blocks.fluid.BlockAlchemicalDissovent.FluidAlchemicalDissovent;
import yichen.extraalchemy.base.items.ItemCoalDust;

@Mod.EventBusSubscriber(modid = ExtraAlchemy.MODID)
@GameRegistry.ObjectHolder(ExtraAlchemy.MODID)
public class BlockLoader {

	public static Block blockBloodstain = new BlockBloodstain().setUnlocalizedName(ExtraAlchemy.MODID + ".bloodstain");
	public static Block blockAlchemyArrayTransmute = new BlockAlchemyArrayTransmute()
			.setUnlocalizedName(ExtraAlchemy.MODID + ".alchemy_array_transmute");

	// 调用注册方法
	public BlockLoader(FMLPreInitializationEvent event) {

		FluidRegistry.registerFluid(BlockAlchemicalDissovent.getAlchemicalDissovent());
		FluidRegistry.addBucketForFluid(BlockAlchemicalDissovent.getAlchemicalDissovent());
		registerFluid(new BlockAlchemicalDissovent(), "alchemical_dissovent");

		register(blockBloodstain, "bloodstain");
		registerItemBlock(blockAlchemyArrayTransmute, "alchemy_array_transmute");
		registerTileEntities();
		registerTESR();
	}

	// 注册渲染贴图
	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerRender(blockBloodstain);
		registerRender(blockAlchemyArrayTransmute);
	}

	// 注册实体
	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileAlchemyArrayTransmute.class,
				ExtraAlchemy.MODID + ":alchemy_array_transmute");
	}

	// 注册特殊渲染
	@SideOnly(Side.CLIENT)
	public static void registerTESR() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyArrayTransmute.class, new TESRAlchemyArrayTransmute());
	}

	// 注册方块
	private static void register(Block block, String name) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
	}

	// 注册方块及物品
	private static void registerItemBlock(Block block, String name) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	// 注册流体方块
	public static void registerFluid(Block block, String name) {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
		registerFluidModels(block, name);
	}

	// 注册流体贴图
	@SideOnly(Side.CLIENT)
	public static void registerFluidModels(Block block, String name) {
		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(ExtraAlchemy.MODID + ":fluid", name);
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
