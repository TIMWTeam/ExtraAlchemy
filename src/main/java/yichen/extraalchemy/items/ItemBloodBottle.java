package yichen.extraalchemy.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

public class ItemBloodBottle extends Item{
	public ItemBloodBottle() {
		this.setMaxStackSize(1);
		this.setMaxDamage(4); 
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextHelper.localizeEffect("tooltip.extraalchemy.coal_dust"));
    }
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        BlockPos newPos = blockPos.offset(side);
        if (world.isAirBlock(newPos)) {
            if (!world.isRemote) {
                world.setBlockState(newPos, BlockLoader.blockBloodstain.getDefaultState());
                TileEntity tile = world.getTileEntity(newPos);
                stack.damageItem(1, player);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
	
}
