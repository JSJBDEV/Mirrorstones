package gd.rf.acro.mirrorstones.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;

public class CushioningStatusEffect extends StatusEffect {
    public CushioningStatusEffect() {
        super(StatusEffectType.BENEFICIAL, Formatting.AQUA.getColorValue());
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.getEntityWorld().getBlockState(entity.getBlockPos().down()).isAir() && entity.fallDistance>2){
            Vec3d vec3d = entity.getVelocity();
            if (vec3d.y < 0.0D) {
                entity.setVelocity(vec3d.x, -vec3d.y * 0.2, vec3d.z);
                entity.fallDistance=0;
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
