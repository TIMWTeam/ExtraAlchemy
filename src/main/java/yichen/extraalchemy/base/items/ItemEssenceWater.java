package yichen.extraalchemy.base.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.util.TextHelper;

public class ItemEssenceWater extends Item{
	public ItemEssenceWater() {
		this.setMaxStackSize(64);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        BlockPos newPos = blockPos.offset(side);
		if(player.isSneaking() && player.getHeldItem(hand).getCount() >= 8) {
			if (world.isAirBlock(newPos)) {
				if(!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).shrink(8);
				if (!world.isRemote) 
	                world.setBlockState(newPos, Blocks.WATER.getDefaultState());
	            return EnumActionResult.SUCCESS;
			}
		}
        return EnumActionResult.FAIL;
    }
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	@Nonnull
	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, EntityLivingBase living) {

		return new ItemStack(Items.AIR);
	}
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 20;
	}
	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.EAT;
	}
}
