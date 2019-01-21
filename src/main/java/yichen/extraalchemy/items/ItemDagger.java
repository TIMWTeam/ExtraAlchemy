package yichen.extraalchemy.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.init.BlockLoader;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.util.DamageSource;
import yichen.extraalchemy.util.TextHelper;

public class ItemDagger extends Item{
	
	public ItemDagger() {
		this.setMaxStackSize(1);
		this.setMaxDamage(64); 
		this.setFull3D();
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextHelper.localizeEffect("tooltip.extraalchemy.dagger1"));
        tooltip.add(TextHelper.localizeEffect("tooltip.extraalchemy.dagger2"));
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		new TextComponentString("����");
		//��Ϊ�����
		if (!(player instanceof FakePlayer)) {
			//��Ϊ����ģʽ
			if (!player.capabilities.isCreativeMode) {
				stack.damageItem(1, player);
				player.setActiveHand(hand);
				player.attackEntityFrom(DamageSource.INSTANCE, 0.001F);
                player.setHealth(Math.max(player.getHealth() - 4, 0.0001f));
                if (player.getHealth() <= 0.001f) {
                    player.onDeath(DamageSource.INSTANCE);
                    player.setHealth(0);
                }
			}
			//��ⱳ���Ƿ��в���ƿ
			ItemStack itemstack = this.findAmmo(player);
			//�в���ƿ����Ѫƿ
			if(!itemstack.isEmpty()) {
				itemstack.shrink(1);
				ItemStack bloodbottle = new ItemStack(ItemLoader.itembloodbottle);
				if(!player.inventory.addItemStackToInventory(bloodbottle))
	    		{
	    			player.dropItem(bloodbottle, true, false);
	    		}
			}
			//û����ƿ���ڵ�������Ѫ��
			else {
				player.world.setBlockState(new BlockPos(player), BlockLoader.blockBloodstain.getDefaultState());
			}
		}
		return super.onItemRightClick(world, player, hand);
        
	}
	private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isGlassBottle(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isGlassBottle(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isGlassBottle(itemstack))
                {
                    return itemstack;
                }
            }
            return ItemStack.EMPTY;
        }
    }
	protected boolean isGlassBottle(ItemStack stack)
    {
        return stack.getItem() instanceof ItemGlassBottle;
    }
}
