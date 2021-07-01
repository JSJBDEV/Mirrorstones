package gd.rf.acro.mirrorstones.blocks;

import gd.rf.acro.mirrorstones.Mirrorstones;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class AlchemicalBlock extends Block {
    public AlchemicalBlock() {
        super(Settings.of(Material.AMETHYST).strength(-1.0F, 3600000.0F).ticksRandomly().dropsNothing().luminance(i->
        {
            if(i.get(Properties.POWERED))
            {
                return 15;
            }
            else
            {
                return 0;
            }
        }));
        this.setDefaultState(this.getDefaultState().with(Properties.POWERED,false));
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && hand==Hand.MAIN_HAND)
        {
            if(isAlchemicalMoonPhase(world,pos))
            {
                ServerWorld serverWorld = (ServerWorld) world;
                BlockPos opposite = new BlockPos(pos.getX()*-1,pos.getY(),pos.getZ()*-1);
                BlockPos other = serverWorld.locateStructure(Mirrorstones.MY_STRUCTURE,opposite,1000,false);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,200,255));
                player.teleport(other.getX(),255,other.getZ());
                serverWorld.playSound(other.getX(),other.getY(),other.getZ(),SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS,1,1,false);
                EntityType.LIGHTNING_BOLT.spawn(serverWorld,null,null,player,pos, SpawnReason.EVENT,false,false);
                if(!player.getScoreboardTags().contains("ms_try1"))
                {
                    player.addScoreboardTag("ms_try1");
                    player.giveItemStack(Mirrorstones.createBook("Acrogenous","Observations of a Scholar II",
                            "Hark! It would seem that the secret to the Mirrorstones has been unveiled! I appear to be somewhere faraway from my home, next to another Mirrorstone",
                            "If I was to hazard a guess, I'd say these stones got there name from the effective 'reflection' of my position in this strange world.",
                            "It would seem however, that each element on the pillar has a different moon phase to invoke the phenomena, I feel perhaps, this is not completely random, perhaps I can learn to read them?",
                            "Whilst I appear to have discovered the main purpose of these strange pillars, I feel there is much more to learn about what they represent... perhaps I should assemble a lab?"));
                }
            }
            else
            {
                player.sendMessage(new LiteralText("The Mirrorstones don't appear to react at this time"),false);
            }
            if(!player.getScoreboardTags().contains("ms_init"))
            {
                player.addScoreboardTag("ms_init");
                player.giveItemStack(Mirrorstones.createBook("Acrogenous","Observations of a Scholar I",
                        "Huzzah! \n I have found a so called 'Mirrorstone' \n it seems to be a pillar of alchemical elements, of course, I am familiar with them! So what to do with them?",
                        "It would seem these monoliths do not break or fall, nor are they effected by the Luminiferous aether, however they do seem to to have odd properties linked to the positions of the celestial bodies",
                        "I suspect these pillars must do something during certain phases, but I will have to examine them consistently for at least a week"));
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public static int getMoonPhase(long lunarTime) {
        return (int)(lunarTime / 24000L % 8L + 8L) % 8;
    }

    private boolean isAlchemicalMoonPhase(World world, BlockPos clicked)
    {
        int mp = getMoonPhase(world.getLunarTime());
        int total = 0;
        BlockPos loc = clicked;
        while (world.getBlockState(loc).getBlock() instanceof AlchemicalBlock)
        {
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_AIR)
            {
                total+=1;
            }
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_EARTH)
            {
                total+=2;
            }
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_WATER)
            {
                total+=3;
            }
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_FIRE)
            {
                total+=4;
            }
            if(total>7)
            {
                total=7-total;
            }
            if(total<0)
            {
                total= total+7;
            }
            loc=loc.up();
        }
        loc = clicked;
        while (world.getBlockState(loc).getBlock() instanceof AlchemicalBlock)
        {
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_AIR)
            {
                total-=4;
            }
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_EARTH)
            {
                total-=3;
            }
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_WATER)
            {
                total-=2;
            }
            if(world.getBlockState(loc).getBlock()==Mirrorstones.ALCHEMICAL_FIRE)
            {
                total-=1;
            }
            if(total>7)
            {
                total=8-total;
            }
            if(total<0)
            {
                total= total+8;
            }
            loc=loc.down();
        }
        //System.out.println("stack total: "+total+" current phase: "+mp);
        return total == mp;

    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if(isAlchemicalMoonPhase(world,pos))
        {
            world.setBlockState(pos,state.with(Properties.POWERED,true));
        }
        else
        {
            world.setBlockState(pos,state.with(Properties.POWERED,false));
        }
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.POWERED);
    }
}
