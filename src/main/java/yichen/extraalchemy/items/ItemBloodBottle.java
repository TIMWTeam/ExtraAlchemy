package yichen.extraalchemy.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.util.TextHelper;

public class ItemBloodBottle extends Item{
	public ItemBloodBottle() {
		this.setMaxStackSize(1);
		this.setMaxDamage(3); 
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextHelper.localizeEffect("tooltip.extraalchemy.blood_bottle"));
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
                	if(stack.isEmpty()) {
                		ItemStack glass_bottle = new ItemStack(Items.GLASS_BOTTLE);
        				if(!player.inventory.addItemStackToInventory(glass_bottle))
        	    		{
        	    			player.dropItem(glass_bottle, true, false);
        	    		}
                	}
                
            }
            return EnumActionResult.SUCCESS;
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
		living.setHealth(Math.max(living.getHealth() + (4-stack.getItemDamage()), 0));
		return new ItemStack(Items.GLASS_BOTTLE);
	}
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 20;
	}
	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.DRINK;
	}
}
