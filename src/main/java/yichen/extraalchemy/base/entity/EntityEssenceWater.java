package yichen.extraalchemy.base.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityEssenceWater extends EntityThrowable {

	private boolean shift = false;

	public EntityEssenceWater(World worldIn) {
		super(worldIn);
	}

	public EntityEssenceWater(EntityPlayer player) {
		super(player.world, player);
	}

	public EntityEssenceWater(EntityPlayer player, boolean shift) {
		super(player.world, player);
		shift = this.shift;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		// 渲染粒子效果
		if (world.isRemote) {
			double r = 0.1;
			double m = 0.1;
			for (int i = 0; i < 3; i++) {
				world.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX + r * (Math.random() - 0.5),
						posY + r * (Math.random() - 0.5), posZ + r * (Math.random() - 0.5), m * (Math.random() - 0.5),
						m * (Math.random() - 0.5), m * (Math.random() - 0.5));
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!world.isRemote) {
			if (result.entityHit != null && result.entityHit instanceof EntityLivingBase
					&& result.entityHit != getThrower()) {
				EntityLivingBase thrower = getThrower();
				// 命中生物获得枯萎 玩家获得生命恢复
				((EntityLivingBase) result.entityHit)
						.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 1, true, true));
				thrower.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1, true, true));
				// 命中生物受到伤害 玩家恢复生命
				result.entityHit.attackEntityFrom(
						thrower != null
								? thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, thrower)
										: DamageSource.causeMobDamage(thrower)
								: DamageSource.GENERIC,
						2);
				thrower.heal(2);
			}
			this.world.setEntityState(this, (byte) 3);
			setDead();
		}
	}
}
