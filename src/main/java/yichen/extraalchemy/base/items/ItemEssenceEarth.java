package yichen.extraalchemy.base.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yichen.extraalchemy.util.ItemHelper;

public class ItemEssenceEarth extends ItemDefault {
	private String states = null;

	public ItemEssenceEarth() {
		super("essence earth");
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flags) {
		list.add(I18n.format("tooltip.essence.mode"));
		if (stack.hasTagCompound()) {
			states = ItemHelper.getOrCreateCompound(stack).getString("states");
			list.add(I18n.format("tooltip.essence_earth." + (states.isEmpty() ? "stone" : states)));
		}
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		BlockPos newPos = blockPos.offset(side);
		if (!player.isSneaking() && player.getHeldItem(hand).getCount() >= 8) {
			if (world.isAirBlock(newPos)) {
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).shrink(8);
				if (!world.isRemote) {
					states = ItemHelper.getOrCreateCompound(stack).getString("states");
					Block block = Blocks.AIR;
					switch (states) {
					case "stone":
						block = Blocks.STONE;
						break;
					case "cobblestone":
						block = Blocks.COBBLESTONE;
						break;
					case "dirt":
						block = Blocks.DIRT;
						break;
					case "sand":
						block = Blocks.SAND;
						break;
					default:
						block = Blocks.STONE;
						break;
					}
					world.setBlockState(newPos, block.getDefaultState());
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.FAIL;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {

		if (!world.isRemote && player.isSneaking()) {
			states = ItemHelper.getOrCreateCompound(player.getHeldItem(hand)).getString("states");
			switch (states) {
			case "stone":
				states = "cobblestone";
				break;
			case "cobblestone":
				states = "dirt";
				break;
			case "dirt":
				states = "sand";
				break;
			case "sand":
				states = "stone";
				break;
			default:
				states = "cobblestone";
				break;
			}
			player.sendMessage(new TextComponentTranslation(I18n.format("tooltip.essence_earth." + states)));
			NBTTagCompound compound = ItemHelper.getOrCreateCompound(player.getHeldItem(hand));
			compound.setString("states", states);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
