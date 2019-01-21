package yichen.extraalchemy.init;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.blocks.BlockBloodstain;
import yichen.extraalchemy.blocks.alchemy_circle.BlockAlchemyCircle;
import yichen.extraalchemy.blocks.alchemy_circle.rander.TESRAlchemyCircle;
import yichen.extraalchemy.blocks.alchemy_circle.tile.TileAlchemyCircle;
import yichen.extraalchemy.items.ItemCoalDust;


@Mod.EventBusSubscriber(modid = ExtraAlchemy.MODID)
@GameRegistry.ObjectHolder(ExtraAlchemy.MODID)
public class BlockLoader {

	//public static final Block ALCHEMY_ARRAY = Blocks.AIR;

	public static Block blockAlchemyCircle = new BlockAlchemyCircle().setUnlocalizedName(ExtraAlchemy.MODID +".alchemy_circle");
	public static Block blockBloodstain = new BlockBloodstain().setUnlocalizedName(ExtraAlchemy.MODID +".bloodstain");
	
	//调用注册方法
	public BlockLoader(FMLPreInitializationEvent event)
    {
		register(blockBloodstain,"bloodstain");
		registerItemBlock(blockAlchemyCircle, "alchemy_circle");
        registerTileEntities();
        registerTESR();
    }
	//注册渲染贴图
	@SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
		registerRender(blockAlchemyCircle);
		registerRender(blockBloodstain);
    }
	//注册实体
	private static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileAlchemyCircle.class, ExtraAlchemy.MODID + ":alchemy_circle");
    }
	//注册特殊渲染
	@SideOnly(Side.CLIENT)
    public static void registerTESR() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyCircle.class, new TESRAlchemyCircle());
    }
	//注册方块
	private static void register(Block block, String name)
    {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
    }
	//注册方块及物品
	private static void registerItemBlock(Block block, String name)
    {
		ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
		ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    @SideOnly(Side.CLIENT)
    private static void registerRender(Block block)
    {
        registerRender(block, 0);
    }
	@SideOnly(Side.CLIENT)
    private static void registerRender(Block block, int meta)
    {
        ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, model);
    }
	
	
}
