package yichen.extraalchemy.base.blocks.alchemy_array;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.http.util.EntityUtils;

import com.google.common.hash.BloomFilter;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import yichen.extraalchemy.api.ExtraAlchemyAPI;
import yichen.extraalchemy.api.RecipeTransmute;
import yichen.extraalchemy.config.ConfigLoader;
import yichen.extraalchemy.util.Counter;
import yichen.extraalchemy.util.Vector3;

public class TileAlchemyArrayTransmute extends TileEntityBase  implements ITickable {
	
	/*
	 * 炼金阵状态
	 * 0.未启动	1.启动中	2.已启动	3.关闭中
	 */	
	public int status = 0 ;
	//炼金阵-计数
	public Counter counter = new Counter();
    //炼金阵圆控制
    public Counter counterWork = new Counter();
    //炼金阵效率
    public float eff = ConfigLoader.alchemyArrayEfficiency;
    //嬗变所需时间
    public float TICKS_TRANSMUTE = 100;
    //激活所需时间
    public float TICKS_ACTIVE = 200;
    //激活物品的ID
    private int entityIdActive = -1;
    //激活的物品
    public Entity activeEntity = null;
    //配方
    private RecipeTransmute RT = null;
    //悬浮位置
    private Vector3 itemHoverPos;
    
    public ItemStackHandler inventory;

	//更新事件
	@Override
	public void update(){
		super.update();
		
		if(status == 1) {
     		counter.increment();
     		if (counter.value() >= (TICKS_ACTIVE/eff)) {
     			this.status = 2;
     		}
     		//物品悬浮
     		floatItem(world);
 		}else if(status == 2) {
 			if(floatItem(world)) {
     			counterWork.increment();
				if(counterWork.value() % 4 == 0) {
					markForUpdate();
				}
				if(counterWork.value() >= (TICKS_TRANSMUTE/eff)) {
					//减少物品数量
					ItemStack activeItem = ((EntityItem) activeEntity).getItem();
					activeItem.shrink(1);
					
					//掉出加工物
					ItemStack tunedStack = RT.getOutput().copy();
					dropItem(world, itemHoverPos.getX(), itemHoverPos.getY(), itemHoverPos.getZ(), tunedStack);
					
					//如果物品处理完毕则关闭炼金阵
					if(activeItem.getCount() == 0) {
						activeEntity.setDead();
						startWork(3, null);
					}
					
					//重置工作时间
					counterWork.set(0);
				}
			}
		}else if(status == 0) {
			if (getTicksExisted() % 10 == 0) {
				AxisAlignedBB box = new AxisAlignedBB(0.3, -0.25, 0.3, 0.7, 0.25, 0.7).grow(0.2).offset(getPos());
				List<EntityItem> unfilteredItems = world.getEntitiesWithinAABB(EntityItem.class, box);
		        if(unfilteredItems.size() > 0) {
		        	//验证是否有匹配配方
		        	for (int i = 0; i < unfilteredItems.size(); i++) {
			            EntityItem entityItem = unfilteredItems.get(i);
			        	if(matchRecipe(entityItem)) {
			        		EntityItem item = unfilteredItems.get(i);
			        		startWork(1, item);
			        		return;
			        	}
					}
		        }
			}
		}else if (status == 3) {
    		counter.decrement();
            if (counter.value() <= 0) {
	            startWork(0, null);
                this.counter.clear();
            }
		}
    }
	//浮动物品
    private Boolean floatItem(World world) {
    	if(activeEntity == null) {
			startWork(3, null);
		}else {
			if(activeEntity.isDead || !(activeEntity instanceof EntityItem) ) {
				startWork(3, null);
			}else {
				//(counter.value() / 720F)-0.3
				itemHoverPos = new Vector3(this).add(0.5, -0.18, 0.5);
				activeEntity.setPosition(itemHoverPos.getX(), itemHoverPos.getY(), itemHoverPos.getZ());
				activeEntity.setRotationYawHead(this.getTicksExisted()/360);
				if(!world.isRemote) {
					((EntityItem) activeEntity).setNoDespawn();
				}
				return true;
			}
		} 
		return false;
	}
    //修改炼金阵状态
    private void startWork(int sta, Entity item) {
    	this.status = MathHelper.clamp(sta, 0, 4);
    	this.counterWork.set(0);
        switch (sta) {
	        case 0:
	        case 3:
	            this.entityIdActive = -1;
	            this.activeEntity = null;
	            this.RT = null;
	            this.TICKS_TRANSMUTE=100;
	            break;
	        case 1:
	            this.entityIdActive = item.getEntityId();
	            this.activeEntity = item;
	            break;
	        default:
	            break;
        }
        markForUpdate();
    }
    //验证物品的配方
    public boolean matchRecipe(EntityItem item) {
    	if( item.isDead || item.getItem().isEmpty())
			return false;
    	   	
    	List<RecipeTransmute> matchingRecipes = new ArrayList<>();
		for (RecipeTransmute recipe : ExtraAlchemyAPI.transmuteRecipes) {
			if (recipe.matches(item.getItem())) {
				RT=recipe;
				TICKS_TRANSMUTE=recipe.getTime();
				return true;
			}
		}
		return false;
    }
    //物品掉出
    public EntityItem dropItem(World world, double x, double y, double z, ItemStack stack) {
        if (world.isRemote) return null;
        EntityItem ei = new EntityItem(world, x, y, z, stack);
        ei.motionX = 0;
        ei.motionY = 0;
        ei.motionZ = 0;
        world.spawnEntity(ei);
        ei.setDefaultPickupDelay();
        return ei;
    }
    
	//读取NBT
	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
        this.status = compound.getInteger("status");

    }
	//写入NBT
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
    	compound.setInteger("status", this.status);
        return super.writeToNBT(compound);
    }
	@Override
	protected void onFirstTick() {
		// TODO 自动生成的方法存根
		
	}


}