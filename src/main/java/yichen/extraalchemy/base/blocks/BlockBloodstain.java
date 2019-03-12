package yichen.extraalchemy.base.blocks;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.util.base.BlockDust;

public class BlockBloodstain extends BlockDust{

	public BlockBloodstain() {
		setHardness(0.0F);
		setLightLevel(0.0F);
    }
	@Nonnull
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, @Nonnull IBlockState state) {
		return new ItemStack(getItemDropped(state, worldIn.rand, 0));
	}
	
}