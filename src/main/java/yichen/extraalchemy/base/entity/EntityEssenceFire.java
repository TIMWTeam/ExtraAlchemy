package yichen.extraalchemy.base.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEssenceFire extends EntityThrowable {

	public EntityEssenceFire(World worldIn) {
		super(worldIn);
	}

	public EntityEssenceFire(EntityPlayer player) {
		super(player.world, player);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		// 渲染火焰粒子效果
		if (world.isRemote) {
			double r = 0.1;
			double m = 0.1;
			for (int i = 0; i < 3; i++)
				world.spawnParticle(EnumParticleTypes.FLAME, posX + r * (Math.random() - 0.5),
						posY + r * (Math.random() - 0.5), posZ + r * (Math.random() - 0.5), m * (Math.random() - 0.5),
						m * (Math.random() - 0.5), m * (Math.random() - 0.5));
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!world.isRemote) {
			switch (result.typeOfHit) {
			case BLOCK: {
				if (result != null) {
					EnumFacing dir = result.sideHit;

					if (dir != null) {
						BlockPos pos = result.getBlockPos().offset(dir);
						IBlockState state = world.getBlockState(pos);
						Block block = state.getBlock();
						if (block.isAir(state, world, pos)) {
							IBlockState stateSet = Blocks.FIRE.getDefaultState();
							world.setBlockState(pos, stateSet, 1 | 2);
							world.playEvent(2001, pos, Block.getStateId(stateSet));
							pos = pos.down();
						}
					}
				}
			}
			case ENTITY: {
				if (result.entityHit != null && result.entityHit instanceof EntityLivingBase
						&& result.entityHit != getThrower()) {
					EntityLivingBase thrower = getThrower();
					result.entityHit.attackEntityFrom(
							thrower != null
									? thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, thrower)
											: DamageSource.causeMobDamage(thrower)
									: DamageSource.GENERIC,
							6);
					result.entityHit.setFire(5);
				}
			}
			default:
				break;
			}
			this.world.setEntityState(this, (byte) 3);
			setDead();
		}
	}

}
