package yichen.extraalchemy.base.blocks.fluid;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleDrip.LavaFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeDissovent;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.util.Counter;
import yichen.extraalchemy.util.DamageSourceExtraAlchemy;
import yichen.extraalchemy.util.TextHelper;

public class BlockAlchemicalDissovent extends BlockFluidClassic {
	private static final Fluid alchemicalDissovent = new FluidAlchemicalDissovent();

	private Counter counter = new Counter();
	
	public BlockAlchemicalDissovent() {
		super(new FluidAlchemicalDissovent() , Material.WATER);

		getAlchemicalDissovent().setBlock(this);
	}
	
	public static Fluid getAlchemicalDissovent() {
        return alchemicalDissovent;
    }
	
	@Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        if(counter.value() >= 10) {
        	double d = Math.random();
	        if (entityIn instanceof EntityLivingBase  && ((EntityLivingBase) entityIn).getHealth() >= 0.001f) {
	            EntityLivingBase entityliving = (EntityLivingBase) entityIn;
	            entityliving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 10, 0, true, true));
	            entityliving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 0, true, true));

	            //受到伤害
                //玩家10%几率、生物0.5%几率掉落生命源质
	            if(entityIn instanceof EntityPlayer) {
            		if( !((EntityPlayer) entityIn).isCreative()) 
            			causeDamages(entityliving);
            		if(d<=0.1) 
    	            	entityIn.dropItem(ItemLoader.itemLifeEssence,1);
            	}else {
        			causeDamages(entityliving);
        			if (d<=0.005)
    	            	entityIn.dropItem(ItemLoader.itemLifeEssence,1);
            	}
	        } else if (entityIn instanceof EntityItem) {
	        	((EntityItem) entityIn).setNoDespawn();
	        	ItemStack itemStack = ((EntityItem) entityIn).getItem();
	        	if (!itemStack.isEmpty()) {
	        		if (entityIn.getEntityWorld().isRemote) return;
	        		RecipeDissovent recipe = matchRecipe((EntityItem) entityIn);
	        		if(recipe != null) {
	        			if(d<=recipe.getChance() ){
	        				itemStack.shrink(1);
	        				ItemStack tunedStack = recipe.getOutput().copy();
	        				EntityItem outputItem = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, tunedStack);
	        				worldIn.spawnEntity(outputItem);
	        			}
	        		}
	        	}
	        }
	        counter.clear();
        }else {
        	counter.increment();
        }
	}
	//对生物造成伤害
	public void causeDamages(EntityLivingBase entityliving) {
        entityliving.attackEntityFrom(DamageSourceExtraAlchemy.DISSOLVE, 0.001F);
        entityliving.setHealth(Math.max(entityliving.getHealth() - 2, 0.0001f));
        if (entityliving.getHealth() <= 0.001f ) {
        	entityliving.onDeath(DamageSourceExtraAlchemy.DISSOLVE);
        	entityliving.setHealth(0);
        }
	}
	//验证物品的配方
    public RecipeDissovent matchRecipe(EntityItem item) {
    	if( item.isDead || item.getItem().isEmpty())
			return null;
    	   	
    	List<RecipeDissovent> matchingRecipes = new ArrayList<>();
		for (RecipeDissovent recipe : ExtraAlchemyAPI.dissoventRecipes) {
			if (recipe.matches(item.getItem())) {
				return recipe;
			}
		}
		return null;
    }
	
	public static class FluidAlchemicalDissovent extends Fluid{
	    public FluidAlchemicalDissovent()
	    {
	        super("alchemical_dissovent", 
	        		new ResourceLocation(ExtraAlchemy.MODID + ":blocks/fluid/alchemical_dissovent_still"), 
	        		new ResourceLocation(ExtraAlchemy.MODID + ":blocks/fluid/alchemical_dissovent_flow"));
	        this.setDensity(13600);//密度
	        this.setViscosity(5000);//粘稠
	        this.setLuminosity(5);//亮度
	        this.setTemperature(10000);//温度
	    }
	    @Override
	    public String getLocalizedName(FluidStack fluidStack) {
	        return TextHelper.localize("tile."+ExtraAlchemy.MODID+".fluid.alchemical_dissovent.name");
	    }
	}
	
}
