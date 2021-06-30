package gd.rf.acro.mirrorstones.blocks;

import gd.rf.acro.mirrorstones.Mirrorstones;
import gd.rf.acro.mirrorstones.interfaces.TheoryTaskAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AetherBlock extends Block {
    public AetherBlock() {
        super(AbstractBlock.Settings.of(Material.AMETHYST).strength(-1.0F, 3600000.0F).ticksRandomly().dropsNothing());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class,new Box(pos.add(-25,-25,-25),pos.add(25,25,25)),PlayerEntity::isPlayer);
        int[] tower = getTowerStack(world,pos);
        System.out.println(Arrays.toString(tower));
        players.forEach(player->
        {
            if(player.getScoreboardTags().contains("air_devotee") && tower[0]>0)
            {
                TheoryTaskAccessor theoryTaskAccessor = (TheoryTaskAccessor) player;
                if(theoryTaskAccessor.hasFailedAirTask())
                {
                    player.removeScoreboardTag("air_devotee");
                }
                else
                {
                    player.addStatusEffect(new StatusEffectInstance(Mirrorstones.FAST_ARROWS_STATUS_EFFECT,10000,1,true,false));
                    if(random.nextBoolean())
                    {
                        theoryTaskAccessor.addTaskForAir();
                        player.sendMessage(new LiteralText("You got a new Air task!"),false);
                    }
                }
            }
            if(player.getScoreboardTags().contains("earth_devotee") && tower[1]>0)
            {
                TheoryTaskAccessor theoryTaskAccessor = (TheoryTaskAccessor) player;
                if(theoryTaskAccessor.hasFailedEarthTask())
                {
                    player.removeScoreboardTag("earth_devotee");
                }
                else
                {

                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,10000,0,true,false));
                    if(random.nextBoolean())
                    {
                        theoryTaskAccessor.addTaskForEarth();
                        player.sendMessage(new LiteralText("You got a new Earth task!"),false);
                    }
                }
            }
            if(player.getScoreboardTags().contains("water_devotee") && tower[2]>0)
            {
                TheoryTaskAccessor theoryTaskAccessor = (TheoryTaskAccessor) player;
                if(theoryTaskAccessor.hasFailedWaterTask())
                {
                    player.removeScoreboardTag("water_devotee");
                }
                else
                {
                    player.addStatusEffect(new StatusEffectInstance(Mirrorstones.CUSHIONING_STATUS_EFFECT,10000,1,true,false));
                    if(random.nextBoolean())
                    {
                        theoryTaskAccessor.addTaskForWater();
                        player.sendMessage(new LiteralText("You got a new Water task!"),false);
                    }
                }
            }
            if(player.getScoreboardTags().contains("fire_devotee") && tower[3]>0)
            {
                TheoryTaskAccessor theoryTaskAccessor = (TheoryTaskAccessor) player;
                if(theoryTaskAccessor.hasFailedFireTask())
                {
                    player.removeScoreboardTag("fire_devotee");
                }
                else
                {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,10000,0,true,false));
                    if(random.nextBoolean())
                    {
                        theoryTaskAccessor.addTaskForFire();
                        player.sendMessage(new LiteralText("You got a new Fire task!"),false);
                    }
                }
            }
        });
    }


    private int[] getTowerStack(ServerWorld world, BlockPos pos)
    {
        BlockPos tec = pos.down();
        int air =0;
        int earth=0;
        int water=0;
        int fire=0;
        while (world.getBlockState(tec).getBlock() instanceof AlchemicalBlock)
        {
            if(world.getBlockState(tec).getBlock()== Mirrorstones.ALCHEMICAL_AIR)
            {
                air++;
            }
            if(world.getBlockState(tec).getBlock()== Mirrorstones.ALCHEMICAL_EARTH)
            {
                earth++;
            }
            if(world.getBlockState(tec).getBlock()== Mirrorstones.ALCHEMICAL_WATER)
            {
                water++;
            }
            if(world.getBlockState(tec).getBlock()== Mirrorstones.ALCHEMICAL_FIRE)
            {
                fire++;
            }
            tec=tec.down();
        }
        return new int[]{air,earth,water,fire};
    }
}
