package gd.rf.acro.mirrorstones.blocks;

import gd.rf.acro.mirrorstones.interfaces.TheoryTaskAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ResearchBlock extends Block {
    public ResearchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(hand==Hand.MAIN_HAND && !world.isClient)
        {
            TheoryTaskAccessor theoryTaskAccessor = (TheoryTaskAccessor) player;
            if(player.getStackInHand(hand).isEmpty())
            {
                NbtList list = theoryTaskAccessor.getAirTasks();
                StringBuilder s = new StringBuilder("\nAir Tasks:\n");
                for (NbtElement task : list) {
                    NbtCompound tag = (NbtCompound) task;
                    s.append("Collect: ").append(tag.getString("item")).append("\n");
                }
                player.sendMessage(new LiteralText(s.toString()),false);

                list = theoryTaskAccessor.getWaterTasks();
                s = new StringBuilder("\nWater Tasks:\n");
                for (NbtElement task : list) {
                    NbtCompound tag = (NbtCompound) task;
                    s.append("Collect: ").append(tag.getString("item")).append("\n");
                }
                player.sendMessage(new LiteralText(s.toString()),false);

                list = theoryTaskAccessor.getEarthTasks();
                s = new StringBuilder("\nEarth Tasks:\n");
                for (NbtElement task : list) {
                    NbtCompound tag = (NbtCompound) task;
                    s.append("Collect: ").append(tag.getString("item")).append("\n");
                }
                player.sendMessage(new LiteralText(s.toString()),false);

                list = theoryTaskAccessor.getFireTasks();
                s = new StringBuilder("\nFire Tasks:\n");
                for (NbtElement task : list) {
                    NbtCompound tag = (NbtCompound) task;
                    s.append("Collect: ").append(tag.getString("item")).append("\n");
                }
                player.sendMessage(new LiteralText(s.toString()),false);

            }
            else
            {
                String search = player.getStackInHand(hand).getItem().getTranslationKey();

                NbtList list = theoryTaskAccessor.getFireTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeFireTask(i);
                        player.getStackInHand(hand).decrement(1);
                        break;
                    }
                }


                list = theoryTaskAccessor.getWaterTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeWaterTask(i);
                        player.getStackInHand(hand).decrement(1);
                        break;
                    }
                }


                list = theoryTaskAccessor.getEarthTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeEarthTask(i);
                        player.getStackInHand(hand).decrement(1);
                        break;
                    }
                }


                list = theoryTaskAccessor.getAirTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeAirTask(i);
                        player.getStackInHand(hand).decrement(1);
                        break;
                    }
                }

            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
