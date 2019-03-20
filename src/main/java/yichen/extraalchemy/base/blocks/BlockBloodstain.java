package yichen.extraalchemy.base.blocks;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import yichen.extraalchemy.util.base.BlockDust;

public class BlockBloodstain extends BlockDust {

	public BlockBloodstain() {
		this.setRegistryName("bloodstain");
		this.setUnlocalizedName("Bloodstain");
		setHardness(0.0F);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, @Nonnull IBlockState state) {
		return new ItemStack(getItemDropped(state, worldIn.rand, 0));
	}

}