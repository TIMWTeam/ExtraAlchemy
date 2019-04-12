package yichen.extraalchemy.base.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import yichen.extraalchemy.base.entity.EntityEssenceFire;
import yichen.extraalchemy.util.ItemHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemEssenceFire extends ItemDefault {
    private String states = null;

    public ItemEssenceFire() {
        super("essence_fire");
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flags) {
        tooltip.add(I18n.format("tooltip.extraalchemy.essence.mode"));
        states = ItemHelper.getOrCreateCompound(stack).getString("states");
        tooltip.add(I18n.format("tooltip.extraalchemy.essence_fire." + (states.isEmpty() ? "fire_ball" : states)));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos blockPos, EnumHand hand,
                                      EnumFacing side, float hitX, float hitY, float hitZ) {
        states = ItemHelper.getOrCreateCompound(player.getHeldItem(hand)).getString("states");
        ItemStack stack = player.getHeldItem(hand);
        BlockPos newPos = blockPos.offset(side);
        if (states == "lava") {
            if (!player.isSneaking() && player.getHeldItem(hand).getCount() >= 8) {
                if (world.isAirBlock(newPos)) {
                    if (!player.capabilities.isCreativeMode)
                        player.getHeldItem(hand).shrink(8);
                    if (!world.isRemote)
                        world.setBlockState(newPos, Blocks.FLOWING_LAVA.getDefaultState());
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        states = ItemHelper.getOrCreateCompound(player.getHeldItem(hand)).getString("states");
        if (!player.isSneaking()) {
            if (states == "fire_ball" || states.isEmpty()) {
                if (!player.capabilities.isCreativeMode)
                    player.getHeldItem(hand).shrink(1);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                        SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                if (!world.isRemote) {
                    EntityEssenceFire fire = new EntityEssenceFire(player);
                    fire.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
                    world.spawnEntity(fire);
                }
            }
        } else {
            switch (states) {
                case "lava":
                    states = "fire_ball";
                    break;
                case "fire_ball":
                    states = "lava";
                    break;
                default:
                    states = "lava";
                    break;
            }
            NBTTagCompound compound = ItemHelper.getOrCreateCompound(player.getHeldItem(hand));
            compound.setString("states", states);
            if (!world.isRemote)
                player.sendMessage(new TextComponentTranslation(I18n.format("tooltip.extraalchemy.essence_fire." + states)));
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
