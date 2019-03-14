package yichen.extraalchemy.base.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.util.ItemHelper;
import yichen.extraalchemy.util.TextHelper;

public class ItemEssenceEarth extends Item{
	private String states = null;
	
	public ItemEssenceEarth() {
		this.setMaxStackSize(64);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flags)
	{
		if(stack.hasTagCompound())
		{
			states = ItemHelper.getOrCreateCompound(stack).getString("states");
			list.add(TextHelper.getFormattedText(I18n.format("tooltip.extraalchemy.essence_earth."+states)));
		}else {
			list.add(TextHelper.getFormattedText(I18n.format("tooltip.extraalchemy.essence_earth.stone")));
		}
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        BlockPos newPos = blockPos.offset(side);
		if(!player.isSneaking() && player.getHeldItem(hand).getCount() >= 8) {
			if (world.isAirBlock(newPos)) {
				if(!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).shrink(8);
				if (!world.isRemote) {
					states = ItemHelper.getOrCreateCompound(stack).getString("states");
					Block block = Blocks.AIR;
					switch (states) {
					case "stone":	
						block = Blocks.STONE;break;
					case "cobblestone":	
						block = Blocks.COBBLESTONE;break;
					case "dirt":	
						block = Blocks.DIRT;break;
					case "sand":	
						block = Blocks.SAND;break;
					default:
						block = Blocks.STONE;break;
					}
                	world.setBlockState(newPos, block.getDefaultState());
	            return EnumActionResult.SUCCESS;
				} 
			}
		}
        return EnumActionResult.FAIL;
    }
	
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		
		if(!world.isRemote && player.isSneaking())
		{
			states = ItemHelper.getOrCreateCompound(player.getHeldItem(hand)).getString("states");
			switch (states) {
			case "stone":
				states = "cobblestone";break;
			case "cobblestone":
				states = "dirt";break;
			case "dirt":
				states = "sand";break;
			case "sand":
				states = "stone";break;
			default:
				states = "cobblestone";break;
			}
			NBTTagCompound compound = ItemHelper.getOrCreateCompound(player.getHeldItem(hand));
			compound.setString("states", states);
		}
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
