package yichen.extraalchemy.util.alchemycircle;

import net.minecraft.util.math.BlockPos;

public interface IAlchemyCircleSlave {

    void setMasterPos(BlockPos pos);
    BlockPos getMasterPos();

    void setMasterDimension(int dimension);
    int getMasterDimension();

    BlockPos getBlockPos();
    int getDimension();

    boolean isNether();
}
