package yichen.extraalchemy.base.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemEssenceLife extends ItemDefault {
    public ItemEssenceLife() {
        super("essence_life");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        player.setActiveHand(hand);
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase living) {
        living.heal(20);
        if (!((EntityPlayer) living).capabilities.isCreativeMode) stack.shrink(1);
        return stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 20;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.EAT;
    }
}
