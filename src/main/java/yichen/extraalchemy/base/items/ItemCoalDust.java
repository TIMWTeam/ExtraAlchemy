package yichen.extraalchemy.base.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCoalDust extends ItemDefault {
	public ItemCoalDust() {
		super("coal dust");
		this.setMaxDamage(4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.extraalchemy.coal_dust"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		BlockPos newPos = blockPos.offset(side);

		if (world.isAirBlock(newPos)) {
			if (!world.isRemote) {
				EnumFacing rotation = EnumFacing.fromAngle(player.getRotationYawHead());
				// world.setBlockState(newPos,
				// BlockLoader.blockAlchemyCircle.getDefaultState());
				stack.damageItem(1, player);
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

}
