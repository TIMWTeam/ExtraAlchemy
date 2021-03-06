package yichen.extraalchemy.base.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class ItemAirBottle extends ItemDefault {
    public ItemAirBottle() {
        super("air_bottle");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(I18n.format("tooltip.extraalchemy.air_bottle"));
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        boolean correctStack = !stack.isEmpty() && stack.getItem() == Items.GLASS_BOTTLE;
        boolean overworld = event.getWorld().provider.getDimension() == 0;

        if (correctStack && overworld) {
            if (event.getWorld().isRemote) {
                event.getEntityPlayer().swingArm(event.getHand());
            } else {
                ItemStack stack1 = new ItemStack(this);

                ItemHandlerHelper.giveItemToPlayer(event.getEntityPlayer(), stack1);

                stack.shrink(1);

                event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5F, 1F);
            }

            event.setCanceled(true);
            event.setCancellationResult(EnumActionResult.SUCCESS);
        }
    }

}
