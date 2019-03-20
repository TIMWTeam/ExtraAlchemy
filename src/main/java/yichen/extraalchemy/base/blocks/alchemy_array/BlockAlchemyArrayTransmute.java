package yichen.extraalchemy.base.blocks.alchemy_array;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.base.blocks.alchemy_array.tile.TileAlchemyArrayTransmute;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.init.ItemLoader;

public class BlockAlchemyArrayTransmute extends Block {
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.1, 1);

	public BlockAlchemyArrayTransmute() {
		super(Material.CLOTH);
		this.setRegistryName("alchemy_array_transmute");
		this.setUnlocalizedName("AlchemyArrayTransmute");
		this.setHardness(0);
		this.setLightOpacity(3);
		this.setLightLevel(0.5F);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		// No-op
	}

	// 方块大小
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	// 是普通立方体
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	// 是完整立方体
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	// 是否导致窒息
	@Override
	public boolean causesSuffocation(IBlockState state) {
		return false;
	}

	// 是不透明方块
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	// 获取渲染类型
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}

	// 是TileEntity
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	// 中键选取获得方块
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(BlockLoader.blockAlchemyArrayTransmute);
	}

	// 破坏掉落
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}

	// 破坏方块
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState blockState) {
		TileAlchemyArrayTransmute transmute = (TileAlchemyArrayTransmute) world.getTileEntity(pos);
		super.breakBlock(world, pos, blockState);
	}

	// 创建TileEntity
	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileAlchemyArrayTransmute();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState blockState, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		return super.onBlockActivated(world, pos, blockState, playerIn, hand, facing, hitX, hitY, hitZ);
	}

}
