package yichen.extraalchemy.base.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.util.DamageSourceExtraAlchemy;

public class ItemDagger extends ItemDefault {

	public ItemDagger() {
		super("dagger");
		this.setMaxStackSize(1);
		this.setFull3D();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.extraalchemy.dagger.1"));
		tooltip.add(I18n.format("tooltip.extraalchemy.dagger.2"));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		// 不为假玩家
		if (!(player instanceof FakePlayer)) {
			// 不为创造模式
			if (!player.capabilities.isCreativeMode) {
				stack.damageItem(1, player);
				player.setActiveHand(hand);
				player.attackEntityFrom(DamageSourceExtraAlchemy.SELF_HARM, 4F);
			}
			// 获取玩家背包
			ItemStack itemstack = this.findAmmo(player);
			// 背包内有空瓶
			if (!itemstack.isEmpty()) {
				itemstack.shrink(1);
				ItemStack bloodbottle = new ItemStack(ItemLoader.itembloodbottle);
				if (!player.inventory.addItemStackToInventory(bloodbottle)) {
					player.dropItem(bloodbottle, true, false);
				}
			}
			// 创造模式
			else {
				player.world.setBlockState(new BlockPos(player), BlockLoader.blockBloodstain.getDefaultState());
			}
		}
		return super.onItemRightClick(world, player, hand);

	}

	private ItemStack findAmmo(EntityPlayer player) {
		if (this.isGlassBottle(player.getHeldItem(EnumHand.OFF_HAND))) {
			return player.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isGlassBottle(player.getHeldItem(EnumHand.MAIN_HAND))) {
			return player.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isGlassBottle(itemstack)) {
					return itemstack;
				}
			}
			return ItemStack.EMPTY;
		}
	}

	protected boolean isGlassBottle(ItemStack stack) {
		return stack.getItem() instanceof ItemGlassBottle;
	}
}
