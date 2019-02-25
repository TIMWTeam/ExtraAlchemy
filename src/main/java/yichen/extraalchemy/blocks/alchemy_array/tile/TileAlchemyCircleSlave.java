package yichen.extraalchemy.blocks.alchemy_array.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import yichen.extraalchemy.blocks.alchemy_array.tile.base.TileEntityBase;
import yichen.extraalchemy.util.Counter;
import yichen.extraalchemy.util.alchemycircle.IAlchemyCircleMaster;
import yichen.extraalchemy.util.alchemycircle.IAlchemyCircleSlave;

public class TileAlchemyCircleSlave extends TileEntityBase implements ITickable, IAlchemyCircleSlave {
    
    private boolean oldActive;
    private boolean status;
    private int masterDim = Integer.MIN_VALUE;
    private BlockPos masterPos = BlockPos.ORIGIN;

    //-----------TESR Counters and Booleans-------------\\
    public Counter counterCircle = new Counter();
    public Counter counterTeleport = new Counter();
    public Counter counterRotation = new Counter();
    public boolean isAtPosTopMiddleCircle;
    public boolean isAtPosSideCircles;

    public boolean isReadyToTeleport;
    //--------------------------------------------------\\

    @Override
    public void update() {
        if (getWorld().isRemote) {
            updateRendering();
            return;
        }
    }



    // == IPortalSlave == \\

    @Override
    public BlockPos getBlockPos() {
        return this.getPos();
    }

    @Override
    public void setMasterPos(BlockPos pos) {
        this.masterPos = pos;
    }

    @Override
    public BlockPos getMasterPos() {
        return this.masterPos;
    }

    @Override
    public int getDimension() {
        return world.provider.getDimension();
    }

    @Override
    public boolean isNether() {
        return true;
    }

    @Override
    public void setMasterDimension(int dim) {
        this.masterDim = dim;
    }

    @Override
    public int getMasterDimension() {
        return this.masterDim;
    }

    // == End == \\


    // == Basic & Rendering == \\

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(getPos().add(-5, -5, -5), getPos().add(5, 5, 5));
    }
    private void updateRendering() {
        boolean isPlayerInRange = getWorld().isAnyPlayerWithinRangeAt(this.getPos().getX() + 0.5, this.getPos().getY() + 0.75, this.getPos().getZ() + 0.5, 0.5);
        if (this.isActive()) {
            if (isPlayerInRange) {
                this.counterCircle.increment(1);
                this.counterTeleport.increment(1);

                if (this.counterCircle.value() >= 300) {
                    this.counterCircle.clear();
                }

                if (this.counterTeleport.value() >= 100) {
                    if (this.isAtPosTopMiddleCircle) {
                        this.isReadyToTeleport = true;
                    }
                    this.isAtPosTopMiddleCircle = true;
                    this.counterTeleport.clear();
                }
            } else {
                this.counterCircle.clear();
                this.counterTeleport.clear();
                this.isAtPosSideCircles = false;
                this.isAtPosTopMiddleCircle = false;
            }
        } else if (!this.isActive()) {
            this.counterCircle.clear();
            this.counterTeleport.clear();
            this.isAtPosTopMiddleCircle = false;
            this.isAtPosSideCircles = false;
        }

        if (this.counterRotation.value() >= 360) {
            this.counterRotation.clear();
        } else this.counterRotation.increment();
    }

    private void doTeleportation() {
        String dimName = getWorld().provider.getDimensionType().getName();
        if (!dimName.equalsIgnoreCase(DimensionType.OVERWORLD.getName()) && !dimName.equalsIgnoreCase(DimensionType.NETHER.getName()))
            return;

        if (getWorld().isRemote)
            return;

        EntityPlayer player = getWorld().getClosestPlayer(getPos().getX() + 0.5, getPos().getY() + 0.75, getPos().getZ() + 0.5, 0.5, false);
        if (player instanceof EntityPlayerMP && isReadyToTeleport) {

            if (getMasterDimension() == getDimension()) {

            }

            this.isReadyToTeleport = false;
        }
    }

    public void isActive(boolean isActive) {
        this.status = isActive;
    }
    public boolean isActive() {
        return this.status;
    }

    // == End == \\


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        isActive(compound.getBoolean("Active"));
        setMasterDimension(compound.getInteger("masterDim"));
        setMasterPos(new BlockPos(compound.getInteger("xMaster"), compound.getInteger("yMaster"), compound.getInteger("zMaster")));
        super.readFromNBT(compound);
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("Active", isActive());
        compound.setInteger("masterDim", getMasterDimension());
        compound.setInteger("xMaster", getMasterPos().getX());
        compound.setInteger("yMaster", getMasterPos().getY());
        compound.setInteger("zMaster", getMasterPos().getZ());
        return super.writeToNBT(compound);
    }



	@Override
	protected void onFirstTick() {
		// TODO 自动生成的方法存根
		
	}
}