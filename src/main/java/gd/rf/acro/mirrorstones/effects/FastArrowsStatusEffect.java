package gd.rf.acro.mirrorstones.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;

import java.text.Format;

public class FastArrowsStatusEffect extends StatusEffect {
    public FastArrowsStatusEffect() {
        super(StatusEffectType.NEUTRAL, Formatting.AQUA.getColorValue());
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
        entity.world.getEntitiesByClass(ArrowEntity.class,new Box(entity.getPos().add(-5,-5,-5),entity.getPos().add(5,5,5)),null)
                .forEach(arrow->arrow.setVelocity(arrow.getVelocity().multiply(amplifier+1.5)));
    }
}
