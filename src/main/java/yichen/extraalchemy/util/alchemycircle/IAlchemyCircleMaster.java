package yichen.extraalchemy.util.alchemycircle;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public interface IAlchemyCircleMaster {

    void setSlavePos(BlockPos pos);
    BlockPos getSlavePos();

    void isActive(boolean isActive);
    boolean isActive();

    BlockPos getBlockPos();
    int getDimension();

    boolean isNether();
    
}
