package yichen.extraalchemy.base.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;
import yichen.extraalchemy.util.DamageSourceExtraAlchemy;

public class ItemAlchemicalDissovent extends ItemDefault {
	public ItemAlchemicalDissovent() {
		super("alchemical_dissovent");
		this.setMaxStackSize(1);
		this.setMaxDamage(32);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(I18n.format("tooltip.extraalchemy.alchemical_dissovent"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		RayTraceResult rtr = getHitBlock(player);
		if (rtr != null && rtr.getBlockPos() != null && !rtr.getBlockPos().equals(pos)) {
			pos = rtr.getBlockPos();
			side = rtr.sideHit;
		}
		// 获得注视方块
		ItemStack hitblock = new ItemStack(world.getBlockState(pos).getBlock(), 1);
		RecipeDissovent recipe = matchRecipe(hitblock);
		if (recipe != null) {
			// 方块销毁
			world.destroyBlock(pos, false);
			// 耐久减少
			ItemStack stack = player.getHeldItem(hand);
			stack.damageItem(1, player);
			// 生成掉落物
			ItemStack tunedStack = recipe.getOutput().copy();
			EntityItem outputItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
					tunedStack);
			world.spawnEntity(outputItem);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}

	// 验证物品的配方
	public RecipeDissovent matchRecipe(ItemStack item) {
		List<RecipeDissovent> matchingRecipes = new ArrayList<>();
		for (RecipeDissovent recipe : ExtraAlchemyAPI.dissoventRecipes) {
			if (recipe.matches(item)) {
				return recipe;
			}
		}
		return null;
	}

	// 获取玩家触及方块
	public RayTraceResult getHitBlock(EntityPlayer player) {
		return rayTrace(player.getEntityWorld(), player, player.isSneaking());
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		player.setActiveHand(hand);
		return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World world, EntityLivingBase living) {
		living.attackEntityFrom(DamageSourceExtraAlchemy.DISSOLVE_DRING, Float.MAX_VALUE);
		return ItemStack.EMPTY;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 60;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.DRINK;
	}
}
