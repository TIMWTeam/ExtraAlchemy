package yichen.extraalchemy.base.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.base.entity.EntityEssenceWater;
import yichen.extraalchemy.base.entity.EntityEssenceWind;
import yichen.extraalchemy.util.ItemHelper;

public class ItemEssenceWind extends ItemDefault {
	private String states = null;
	public ItemEssenceWind() {
		super("essence wind");
	}

	@SideOnly(Side.CLIENT)
	@Override

	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flags) {
		tooltip.add(I18n.format("tooltip.extraalchemy.essence.mode"));
		if (stack.hasTagCompound()) {
			states = ItemHelper.getOrCreateCompound(stack).getString("states");
			tooltip.add(I18n.format("tooltip.extraalchemy.essence_wind." + states));
		} else {
			tooltip.add(I18n.format("tooltip.extraalchemy.essence_wind.wind_blade"));
		}
	}


	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		states = ItemHelper.getOrCreateCompound(player.getHeldItem(hand)).getString("states");
		if(!player.isSneaking()) {
			if (states.equals("wind_blade") || states.isEmpty()) {
				//风刃
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).shrink(1);
				world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
						SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				if (!world.isRemote) {
					EntityEssenceWind wind = new EntityEssenceWind(player);
					wind.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
					world.spawnEntity(wind);
				}
			} else if (states.equals("wind_soar")) {
				//自身漂浮
				if (!player.capabilities.isCreativeMode)
					player.getHeldItem(hand).shrink(1);
				player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 160, 0, true, true));
			}
		} else if (player.isSneaking()) {
			switch (states) {
			case "wind_soar":
				states = "wind_blade";
				break;
			case "wind_blade":
				states = "wind_soar";
				break;
			default:
				states = "wind_soar";
				break;
			}
			NBTTagCompound compound = ItemHelper.getOrCreateCompound(player.getHeldItem(hand));
			compound.setString("states", states);
			if (!world.isRemote) 
				player.sendMessage(new TextComponentTranslation(I18n.format("tooltip.extraalchemy.essence_wind." + states)));
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
}
