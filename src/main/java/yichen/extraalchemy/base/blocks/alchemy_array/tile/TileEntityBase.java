package yichen.extraalchemy.base.blocks.alchemy_array.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity {

    protected int ticksExisted = 0;
    
    public void update() {
        if(ticksExisted == 0) {
            onFirstTick();
        }
        ticksExisted++;
    }
    public void reSetTicksExisted() {
    	ticksExisted = 0;
    }

    public int getTicksExisted() {
        return ticksExisted;
    }

    protected abstract void onFirstTick();

    public void readCustomNBT(NBTTagCompound compound) {
        ticksExisted = compound.getInteger("ticksExisted");
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        readCustomNBT(compound);
        readSaveNBT(compound);
    }
    
    public void writeCustomNBT(NBTTagCompound compound) {
        compound.setInteger("ticksExisted", ticksExisted);
    }
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        writeCustomNBT(compound);
        writeSaveNBT(compound);
        return compound;
    }
    public void writeSaveNBT(NBTTagCompound compound) {}
    public void readSaveNBT(NBTTagCompound compound) {}
    public void readNetNBT(NBTTagCompound compound) {}
    public void writeNetNBT(NBTTagCompound compound) {}

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
    	NBTTagCompound compound = getUpdateTag();
        writeCustomNBT(compound);
        writeNetNBT(compound);
        return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), compound);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }
    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
    	readFromNBT(packet.getNbtCompound());
    	readNetNBT(packet.getNbtCompound());
    }
    
    public void markForUpdate() {
        IBlockState thisState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, thisState, thisState, 3);
        markDirty();
    }
}