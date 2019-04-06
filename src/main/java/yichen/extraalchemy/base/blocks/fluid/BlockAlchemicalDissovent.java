package yichen.extraalchemy.base.blocks.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.util.Counter;
import yichen.extraalchemy.util.DamageSourceExtraAlchemy;

import java.util.ArrayList;
import java.util.List;

public class BlockAlchemicalDissovent extends BlockFluidClassic {
    private static final Fluid alchemicalDissovent = new FluidAlchemicalDissovent();
    private Counter counter = new Counter();
    private Counter CD = new Counter();

    public BlockAlchemicalDissovent() {
        super(new FluidAlchemicalDissovent(), Material.WATER);
        this.setRegistryName("alchemical_dissovent");
        this.setUnlocalizedName("alchemical_dissovent");
        getAlchemicalDissovent().setBlock(this);
    }

    public static Fluid getAlchemicalDissovent() {
        return alchemicalDissovent;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        if (!worldIn.isRemote) {
            if (counter.value() >= 10 && CD.value() <= 0) {
                double d = Math.random();
                if (entityIn instanceof EntityLivingBase && ((EntityLivingBase) entityIn).getHealth() >= 0.001f) {
                    EntityLivingBase entityliving = (EntityLivingBase) entityIn;
                    entityliving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 20, 0, true, true));
                    entityliving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 0, true, true));

                    // 受到伤害
                    // 玩家10%几率、生物0.5%几率掉落生命源质
                    if (entityIn instanceof EntityPlayer) {
                        if (!((EntityPlayer) entityIn).capabilities.isCreativeMode) causeDamages(entityliving);
                        if (d <= 0.1) entityIn.dropItem(ItemLoader.itemEssenceLife, 1);
                    } else {
                        causeDamages(entityliving);
                        if (d <= 0.005) entityIn.dropItem(ItemLoader.itemEssenceLife, 1);
                    }
                    CD.set(5);
                } else if (entityIn instanceof EntityItem) {
                    ((EntityItem) entityIn).setNoDespawn();
                    ItemStack itemStack = ((EntityItem) entityIn).getItem();
                    if (!itemStack.isEmpty()) {
                        if (entityIn.getEntityWorld().isRemote)
                            return;
                        RecipeDissovent recipe = matchRecipe((EntityItem) entityIn);
                        if (recipe != null) {
                            if (d <= recipe.getChance()) {
                                itemStack.shrink(1);
                                ItemStack tunedStack = recipe.getOutput().copy();
                                EntityItem outputItem = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5,
                                        pos.getZ() + 0.5, tunedStack);
                                worldIn.spawnEntity(outputItem);
                            }
                        }
                        CD.set(20);
                    }
                }
                counter.clear();
            } else {
                CD.decrement();
                counter.increment();
            }
        }
    }

    // 对生物造成伤害
    public void causeDamages(EntityLivingBase entityliving) {
        entityliving.attackEntityFrom(DamageSourceExtraAlchemy.DISSOLVE, 2.0F);
    }

    // 验证物品的配方
    public RecipeDissovent matchRecipe(EntityItem item) {
        if (item.isDead || item.getItem().isEmpty())
            return null;

        List<RecipeDissovent> matchingRecipes = new ArrayList<>();
        for (RecipeDissovent recipe : ExtraAlchemyAPI.dissoventRecipes) {
            if (recipe.matches(item.getItem())) {
                return recipe;
            }
        }
        return null;
    }

	public static class FluidAlchemicalDissovent extends Fluid {
		public FluidAlchemicalDissovent() {
			super("alchemical_dissovent",
					new ResourceLocation(ExtraAlchemy.MODID + ":blocks/fluid/alchemical_dissovent_still"),
					new ResourceLocation(ExtraAlchemy.MODID + ":blocks/fluid/alchemical_dissovent_flow"));
			this.setUnlocalizedName("alchemical_dissovent");
			this.setDensity(13600);// 密度
			this.setViscosity(5000);// 粘稠
			this.setLuminosity(5);// 亮度
			this.setTemperature(10000);// 温度
		}
	}

}
