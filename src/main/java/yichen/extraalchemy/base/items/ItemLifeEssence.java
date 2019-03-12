package yichen.extraalchemy.base.items;

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

public class ItemLifeEssence extends Item{
	public ItemLifeEssence() {
		this.setMaxStackSize(64);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	
	
	@Nonnull
	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, EntityLivingBase living) {
		living.setHealth(Math.max(living.getHealth() + 20, 0));
		return new ItemStack(Items.GLASS_BOTTLE);
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
