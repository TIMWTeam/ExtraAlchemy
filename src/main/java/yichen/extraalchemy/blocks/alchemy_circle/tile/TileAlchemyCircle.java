package yichen.extraalchemy.blocks.alchemy_circle.tile;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.blocks.alchemy_circle.Counter;
import yichen.extraalchemy.blocks.alchemy_circle.tile.base.TileEntityBase;
import yichen.extraalchemy.init.ModFeatures;
import yichen.extraalchemy.util.Vector3;
import yichen.extraalchemy.util.alchemycircle.IAlchemyCircleMaster;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
public class TileAlchemyCircle extends TileEntityBase  implements ITickable, IAlchemyCircleMaster {

    //-----------TESR Counters and Booleans-------------\\
    //炼金阵初始化控制
    public Counter counterActivation = new Counter();
    //炼金阵圆控制
    public Counter counterCircle = new Counter();
    //炼金阵旋转控制
    public Counter counterRotation = new Counter();
    //传送控制
    public Counter counterTeleport = new Counter();
    public boolean isAtPosTopMiddleCircle;
    public boolean isAtPosSideCircles;
    
    //炼金阵状态
    private boolean status;
    //炼金阵状态-准备OK
    public boolean isReady;
    //炼金阵初始化中
    public boolean isActivating;
    //炼金阵初始化结束
    public boolean isActivationDone;
	//-----------工作状态-------------\\

    //掉落状态的物品
    private Entity activeEntity = null;
    //激活中的掉落物ID
    private boolean hasTask = false;
    //物品拾取半径
    private int RANGE = 2;
    
    public ItemStackHandler inventory;
    private FluidTank tank;

    private BlockPos slavePos = BlockPos.ORIGIN;

    private int oldFuel;
    private int oldFluidTransfer;
    private Counter counterFuel = new Counter();
    public Counter counterFluidTransfer = new Counter();
    //--------------------------------------------------\\

	private static final Function<ItemStack, Boolean> itemAcceptable = stack -> {
        if(!(stack.getItem() instanceof Item)) {
        	return false;
        }
		return true;

    };
    
    public TileAlchemyCircle  ()  {
    	isActivating=true;
        this.inventory = new ItemStackHandler(11) {
            @Override
            protected void onContentsChanged(int slot) {
                TileAlchemyCircle.this.markDirty();
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                
//            	ItemStack slotStack = PortalRegistry.getItemForSlot(ConfigHandler.netherPortalItems, ConfigHandler.netherPortalFuel, slot, stack);
//                boolean i = !(slotStack.getItem().equals(stack.getItem()));
//                boolean j = (slotStack.getCount() == 2 && slotStack.getMetadata() != stack.getMetadata());
//                if (i || j) {
//                    return stack;
//                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
    //更新事件
    @Override
    public void update() {
        super.update();
    	if (getWorld().isRemote) {
        	updateRendering();
            return;
        }
    	//正在激活炼金法阵
        if (isActivating) {
            if (isActivationDone) {
                getWorld().createExplosion(null, getPos().getX() + 0.5, getPos().up().getY(), getPos().getZ() + 0.5, 1.0F, false);
                getWorld().playSound(null, getPos().getX(), getPos().getY(), getPos().getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 2.5F, 1.0F);
                getWorld().playSound(null, getPos().getX(), getPos().getY(), getPos().getZ(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.BLOCKS, 4.0F, 1.0F);
                isActive(true);
                isActivating = false;
                isActivationDone = false;
            }
            //setFuel(new FluidStack(getFuel().getFluid(), getFuelAmount() - 10));
        }
        //updateFluidItem();
        	//正在待机
        	//if (this.status) {
        		if(!hasTask) {
        			if(getTicksExisted() % 10 == 0 && activeEntity == null) { 
        				//检查物品
        				List<EntityItem> items = this.getWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-RANGE, -RANGE, -RANGE), pos.add(RANGE+1, RANGE+1, RANGE+1)));
        				System.out.println(getTicksExisted()+"进入"+items.size());
        				if(items.isEmpty()) return;
        				activeEntity=items.get(0);
        				if(activeEntity != null) hasTask=true;
                   	}
               	}else {
               		if(activeEntity == null) {
               			return;
               		}
               		
               		Vector3 itemFloatPos = new Vector3(this).add(0.5, 1, 0.5);
               		activeEntity.setPosition(itemFloatPos.getX(), itemFloatPos.getY(), itemFloatPos.getZ());
               		((EntityItem) activeEntity).setNoDespawn();
               		ItemStack current = ((EntityItem) activeEntity).getItem();

               	}
        	//}
            //正在待机
            if (this.isActive()) {
                
//    			if (counterFuel.value() >= 100) {
//    				setFuel(new FluidStack(getFuel(), getFuelAmount() - 1));
//    				counterFuel.clear();
//    			} else counterFuel.increment();
            	//
//    			doTeleportation();

//    			if (!getSlavePos().equals(BlockPos.ORIGIN)) {
//                	World overworld = getSlaveWorld();//DimensionManager.getWorld(0);
//                    if (overworld != null && !(overworld.getTileEntity(getSlavePos()) instanceof TileAlchemyArraySlave)) {
            			//附属法阵激活
//                    	overworld.setBlockState(getSlavePos(), ModBlocks.PORTAL_NETHER_SLAVE.getDefaultState());
//    					IAlchemyArraySlave slave = (IAlchemyArraySlave) overworld.getTileEntity(getSlavePos());
//    					if (slave != null) {
//    						slave.setMasterPos(getPos());
//    						slave.setMasterDimension(getDimension());
//    					}
//    				}
//    			}
            }
//    		if (!hasFuel() || !hasRequiredBlocks() || !hasRequiredItems()) {
//    			if (isActive()) {
//    				isActive(false);
//    			}
//    			if (!hasFuel()) {
//    				setFuel(null);
//    			}
//    			if (isActivating) {
//    				isActivating = false;
//    			}
//    		}
        }

    //更新渲染
    private void updateRendering() {
        boolean isPlayerInRange = getWorld().isAnyPlayerWithinRangeAt(this.getPos().getX() + 0.5, this.getPos().getY() + 0.75, this.getPos().getZ() + 0.5, 0.5);
        //炼金法阵正在初始化
        if (this.isActivating) {
            if (!this.isActivationDone) {
                this.counterActivation.increment(1);
                //初始化完成
                if (this.counterActivation.value() >= 200) {
                	this.isActivationDone = true;
                    this.counterActivation.clear();
                }
            }
        } else if (this.isActive()) {
//            if (isPlayerInRange) {
                this.counterCircle.increment(1);
                this.counterTeleport.increment(1);

                if (this.counterCircle.value() >= 300) {
                    this.counterCircle.clear();
                }

                if (this.counterTeleport.value() >= 100) {
                    if (this.isAtPosTopMiddleCircle) {
                        this.isReady = true;
                    }
                    this.isAtPosTopMiddleCircle = true;
                    this.counterTeleport.clear();
                }
//            } else {
//                this.counterCircle.clear();
//                this.counterTeleport.clear();
//                this.counterActivation.clear();
//                this.isAtPosSideCircles = false;
//                this.isAtPosTopMiddleCircle = false;
//            }
        } else if (!this.isActive()) {
            this.counterActivation.clear();
            this.counterCircle.clear();
            this.counterTeleport.clear();
            this.isAtPosSideCircles = false;
            this.isAtPosTopMiddleCircle = false;
        }

        if (this.counterRotation.value() >= 360) {
            this.counterRotation.clear();
        } else this.counterRotation.increment();
    }

    //渲染边框大小
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos().add(-5, -5, -5), getPos().add(5, 5, 5));
    }
    private World getSlaveWorld() {
        if (getDimension() == 0) {
            return DimensionManager.getWorld(-1);
        } else return DimensionManager.getWorld(0);
    }

    private void updateFluidItem() {
        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(stack);
            IFluidHandlerItem fluidHandlerCopy = FluidUtil.getFluidHandler(stack.copy());

            if (fluidHandler == null || fluidHandler.getTankProperties()[0].getContents() == null) {
                counterFluidTransfer.set(-1);
                return;
            }
            if (fluidHandlerCopy == null || fluidHandlerCopy.getTankProperties()[0].getContents() == null) {
                counterFluidTransfer.set(-1);
                return;
            }

            int toDrain = Math.min(getFuelCapacity() - getFuelAmount(), Fluid.BUCKET_VOLUME);

            if (toDrain <= 0)
                return;


            FluidStack drainedStack = fluidHandler.drain(toDrain, false);
            IFluidTankProperties[] tankProperties = fluidHandler.getTankProperties();
            if (tankProperties == null || tankProperties.length < 1)
                return;

            if (drainedStack != null) {
                if (counterFluidTransfer.value() > 40) {
                    drainedStack = fluidHandler.drain(toDrain, true);
                    tankProperties = fluidHandler.getTankProperties();
                    tank.fill(drainedStack, true);

                    if (tankProperties[0].getContents() == null) {
                        ItemStack itemStack = inventory.insertItem(1, fluidHandler.getContainer(), false);
                        if (itemStack.equals(ItemStack.EMPTY)) {
                            inventory.extractItem(0, fluidHandler.getContainer().getCount(), false);
                        } else inventory.setStackInSlot(0, fluidHandler.getContainer());
                        if (inventory.getStackInSlot(0).getCount() <= 0) {
                            inventory.setStackInSlot(0, ItemStack.EMPTY);
                        }
                    }
                    counterFluidTransfer.clear();
                } else counterFluidTransfer.increment();
            } else counterFluidTransfer.set(-1);

        }

        if (getFuelAmount() >= getFuelCapacity() || stack.isEmpty()) {
            counterFluidTransfer.set(-1);
        }

        if (oldFluidTransfer != (int) counterFluidTransfer.value()) {
            oldFluidTransfer = (int) counterFluidTransfer.value();        }

    }
    //���д���
    private void doTeleportation() {
        if (!getWorld().provider.getDimensionType().getName().equalsIgnoreCase(DimensionType.NETHER.getName()) && !getWorld().provider.getDimensionType().getName().equalsIgnoreCase(DimensionType.OVERWORLD.getName()))
            return;
        if (!getWorld().isRemote) {
            EntityPlayer player = getWorld().getClosestPlayer(getPos().getX() + 0.5, getPos().getY() + 0.75, getPos().getZ() + 0.5, 0.5, false);
            if (player != null && this.isReady && player instanceof EntityPlayerMP) {

                if (getSlavePos().equals(BlockPos.ORIGIN)) {
                    generatePortal();
                }

                String dimName = getWorld().provider.getDimensionType().getName();
                //TeleporterNTF.teleport((EntityPlayerMP) player, dimName.equalsIgnoreCase(DimensionType.NETHER.getName()) ? 0 : -1, getSlavePos().add(1, 0, 0), false);

                this.isReady = false;            
            }
        }
    }
    //������ԵĴ�����
    private void generatePortal() {
    }
    //����Ϊ��
    public boolean isInvEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    //����������Ʒ
    public boolean isInvFull() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /* == 炼金阵状态 == */
    public void setActivating() {
    	isActivating=true;
    }
    @Override
    public BlockPos getBlockPos() {
        return this.getPos();
    }
    
    @Override
    public void setSlavePos(BlockPos pos) {
        this.slavePos = pos;
    }
    
    @Override
    public BlockPos getSlavePos() {
        return this.slavePos;
    }
    
    @Override
    public void isActive(boolean isActive) {
        this.status = isActive;
    }
    //炼金阵状态
    @Override
    public boolean isActive() {
        return this.status;
    }
    
    @Override
    public int getDimension() {
        return world.provider.getDimension();
    }

    @Override
    public boolean isNether() {
        return true;
    }
    /* == End == */
    //���෽��ṹ
    public boolean hasRequiredBlocks() {
        List<BlockPos> positions = Lists.newArrayList(getPos().east().north(), getPos().south().east(), getPos().west().south(), getPos().west().north());
        boolean value = false;
        for (BlockPos pos : positions) {

            IBlockState state = getWorld().getBlockState(pos);
//            if (state.getBlock() == ModBlocks.PEDESTAL && state.getValue(BlockPedestal.TYPE).getMetadata() == 0) {
//
//            } else return false;
        }
        return value;
    }
    //����Ҫ����Ʒ
    public boolean hasRequiredItems() {
        for (int i = 2; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    //����Ҫ��ȼ��
    public boolean hasFuel() {
        return (getFuelAmount() > 0);
    }

    public void setFuel(FluidStack fluidStack) {
        if (fluidStack == null) {
            this.tank.setFluid(null);
        } else this.tank.setFluid(fluidStack);
    }
    //���ȼ������
    public int getFuelCapacity() {
        return getTank().getCapacity();
    }
    //���ȼ����ֵ
    public int getFuelAmount() {
        return (getFuel() != null ? getFuel().amount : 0);
    }
    public FluidStack getFuel() {
        return this.tank.getFluid();
    }
    public FluidTank getTank() {
        return this.tank;
    }
    private static ItemStack getStack(String string) {
        int meta = 0;
        if (string.contains("|")) {
            string = string.substring(string.indexOf("|") + 1);
            meta = Integer.parseInt(CharMatcher.digit().retainFrom(string));
        }
        Item item = Item.getByNameOrId(string.contains("|") ? string.substring(0, string.indexOf("|")) : string);
        if (item != null) {
            return new ItemStack(item, 1, meta);
        } else return ItemStack.EMPTY;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        return !isInvalid() && playerIn.getDistanceSq(getPos().add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    /* == End == */


    /* == TileEntity == */
    //��ȡNBT
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        //tank = new FluidTankCustom(this, getFuelCapacity()).readFromNBT(compound);
        isActive(compound.getBoolean("Active"));
        setSlavePos(new BlockPos(compound.getInteger("xSlave"), compound.getInteger("ySlave"), compound.getInteger("zSlave")));
        inventory.deserializeNBT(compound.getCompoundTag("Inventory"));
        super.readFromNBT(compound);
    }
    //д��NBT
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
//        tank.writeToNBT(compound);
//        compound.setBoolean("Active", isActive());
//        compound.setInteger("xSlave", getSlavePos().getX());
//        compound.setInteger("ySlave", getSlavePos().getY());
//        compound.setInteger("zSlave", getSlavePos().getZ());
//        compound.setTag("Inventory", inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readNetNBT(NBTTagCompound compound) {
        this.status = compound.getBoolean("status");
        this.isActivating = compound.getBoolean("isActivating");
        this.isActivationDone = compound.getBoolean("isActivationDone");
    }

    @Override
    public void writeNetNBT(NBTTagCompound compound) {
        compound.setBoolean("status", status);
        compound.setBoolean("isActivating", isActivating);
        compound.setBoolean("isActivationDone", isActivationDone);
    }
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank) : super.getCapability(capability, facing);
    }
	@Override
	protected void onFirstTick() {
		// TODO 自动生成的方法存根
		
	}

    /* == End == */
}