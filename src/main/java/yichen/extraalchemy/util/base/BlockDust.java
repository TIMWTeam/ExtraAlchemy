package yichen.extraalchemy.util.base;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.blocks.alchemy_circle.tile.TileAlchemyCircle;
import yichen.extraalchemy.init.ItemLoader;

public class BlockDust extends Block{
	public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.<EnumAttachPosition>create("north", EnumAttachPosition.class);
	public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.<EnumAttachPosition>create("east", EnumAttachPosition.class);
	public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.<EnumAttachPosition>create("south", EnumAttachPosition.class);
	public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.<EnumAttachPosition>create("west", EnumAttachPosition.class);	

	protected static final AxisAlignedBB[] WIRE_AABB = new AxisAlignedBB[] {
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), 
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D) };
	public BlockDust() {
		super(Material.CIRCUITS);
		
		setDefaultState(blockState.getBaseState()
				.withProperty(NORTH, EnumAttachPosition.NONE)
				.withProperty(EAST, EnumAttachPosition.NONE)
				.withProperty(SOUTH, EnumAttachPosition.NONE)
				.withProperty(WEST, EnumAttachPosition.NONE));
		setHardness(0.0F);
		setSoundType(SoundType.STONE);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
    }
	
	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return WIRE_AABB[getAABBIndex(state.getActualState(source, pos))];
	}

	private static int getAABBIndex(IBlockState state) {
		int i = 0;
		boolean north = state.getValue(NORTH) != EnumAttachPosition.NONE;
		boolean east = state.getValue(EAST) != EnumAttachPosition.NONE;
		boolean south = state.getValue(SOUTH) != EnumAttachPosition.NONE;
		boolean west = state.getValue(WEST) != EnumAttachPosition.NONE;

		if(north || south && !east && !west)
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();

		if(east || west && !north && !south)
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();

		if(south || north && !east && !west)
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();

		if(west || east && !north && !south)
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();

		return i;
	}

	@Nonnull
	@Override
	public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = state.withProperty(WEST, getAttachPosition(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(EAST, getAttachPosition(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(NORTH, getAttachPosition(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(SOUTH, getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
		return state;
	}

	protected EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));

		if(!canConnectTo(worldIn.getBlockState(blockpos), direction, worldIn, blockpos) && (iblockstate.isNormalCube() || !canConnectUpwardsTo(worldIn, blockpos.down()))) {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.up());

			if(!iblockstate1.isNormalCube()) {
				boolean flag = worldIn.getBlockState(blockpos).isSideSolid(worldIn, blockpos, EnumFacing.UP) || worldIn.getBlockState(blockpos).getBlock() == Blocks.GLOWSTONE;

				if(flag && canConnectUpwardsTo(worldIn, blockpos.up())) {
					if(iblockstate.isBlockNormalCube())
						return EnumAttachPosition.UP;

					return EnumAttachPosition.SIDE;
				}
			}

			return EnumAttachPosition.NONE;
		}
		else
		{
			return EnumAttachPosition.SIDE;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, @Nonnull BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isTopSolid() || worldIn.getBlockState(pos.down()).getBlock() == Blocks.GLOWSTONE;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(!worldIn.isRemote && !canPlaceBlockAt(worldIn, pos)) {
			dropBlockAsItem(worldIn, pos, state, 0);
			worldIn.setBlockToAir(pos);
		}
	}

	protected boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
		return canConnectTo(worldIn.getBlockState(pos), null, worldIn, pos);
	}

	protected boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side, IBlockAccess world, BlockPos pos) {
		Block block = blockState.getBlock();
		return block == this;
	}

	@Nonnull
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, @Nonnull IBlockState state) {
		return new ItemStack(getItemDropped(state, worldIn.rand, 1));
	}

	@Nonnull
	@Override
	public IBlockState getStateFromMeta(int meta)  {
		return getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Nonnull
	@Override
	public IBlockState withRotation(@Nonnull IBlockState state, Rotation rot) {
		switch(rot) {
		case CLOCKWISE_180:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}

	@Nonnull
	@Override
	public IBlockState withMirror(@Nonnull IBlockState state, Mirror mirrorIn) {
		switch(mirrorIn) {
		case LEFT_RIGHT:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
		case FRONT_BACK:
			return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}

	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST);
	}

	@Nonnull
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		return BlockFaceShape.UNDEFINED;
	}

	@Nonnull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}
	static enum EnumAttachPosition implements IStringSerializable
    {
        UP("up"),
        SIDE("side"),
        NONE("none");
        private final String name;
        private EnumAttachPosition(String name)
        {
            this.name = name;
        }
        public String toString()
        {
            return this.getName();
        }
        public String getName()
        {
            return this.name;
        }
    }
}
