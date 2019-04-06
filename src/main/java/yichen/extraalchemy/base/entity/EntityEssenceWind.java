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

public class EntityEssenceWind extends EntityThrowable {

    private boolean shift = false;

    public EntityEssenceWind(World worldIn) {
        super(worldIn);
    }

    public EntityEssenceWind(EntityPlayer player) {
        super(player.world, player);
    }

    public EntityEssenceWind(EntityPlayer player, boolean shift) {
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
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + r * (Math.random() - 0.5),
                        posY + r * (Math.random() - 0.5), posZ + r * (Math.random() - 0.5), m * (Math.random() - 0.5),
                        m * (Math.random() - 0.5), m * (Math.random() - 0.5));

            }
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            switch (result.typeOfHit) {
                case BLOCK: {
                    if (result != null) {

                    }
                }
                case ENTITY: {
                    if (result.entityHit != null && result.entityHit instanceof EntityLivingBase
                            && result.entityHit != getThrower()) {
                        EntityLivingBase thrower = getThrower();
                        ((EntityLivingBase) result.entityHit)
                                .addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 0, true, true));
                        result.entityHit.attackEntityFrom(
                                thrower != null
                                        ? thrower instanceof EntityPlayer ? DamageSource.causeThrownDamage(this, thrower)
                                        : DamageSource.causeMobDamage(thrower)
                                        : DamageSource.GENERIC,
                                6);
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
