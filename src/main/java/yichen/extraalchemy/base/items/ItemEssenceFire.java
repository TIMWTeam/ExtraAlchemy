package yichen.extraalchemy.base.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import yichen.extraalchemy.base.entity.EntityEssenceFire;

public class ItemEssenceFire extends ItemDefault {
	public ItemEssenceFire() {
		super("essence fire");
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flags) {
		tooltip.add(I18n.format("tooltip.extraalchemy.essence_fire"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		BlockPos newPos = blockPos.offset(side);
		if (player.isSneaking() && player.getHeldItem(hand).getCount() >= 8) {
			if (world.isAirBlock(newPos)) {
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).shrink(8);
				if (!world.isRemote)
					world.setBlockState(newPos, Blocks.LAVA.getDefaultState());
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.FAIL;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {

		if (!player.capabilities.isCreativeMode)
			player.getHeldItem(hand).shrink(1);
		world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
				SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		if (!world.isRemote) {
			EntityEssenceFire fire = new EntityEssenceFire(player);
			fire.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.spawnEntity(fire);
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
