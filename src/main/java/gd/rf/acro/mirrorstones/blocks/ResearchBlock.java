package gd.rf.acro.mirrorstones.blocks;

import gd.rf.acro.mirrorstones.Mirrorstones;
import gd.rf.acro.mirrorstones.interfaces.TheoryTaskAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ResearchBlock extends Block {
    public ResearchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if(placer!=null && !placer.getScoreboardTags().contains("ms_iii"))
        {
            placer.addScoreboardTag("ms_iii");
            Mirrorstones.createBook("Acrogenous","Alchemy and Mirrorstones I",
                    "Now that I have assembled a lab, I should now start studying the mirrorstones for any other properties they may have. \n I have a suspicion that they should react to theories I produce in different ways",
                    "I should firstly craft a Fire, Water, Air or Earth theory and then study items and objects at my lab to continue my research on there effects, I should note down when I find tasks for my theories and try to complete them in one moon cycle",
                    "Perhaps if I spend some time near the Mirrorstones I will inspire myself to find these tasks for my theories?");

        }
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
                String search = player.getStackInHand(hand).getItem().toString();

                NbtList list = theoryTaskAccessor.getFireTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeFireTask(i);
                        player.getStackInHand(hand).decrement(1);
                        player.sendMessage(new LiteralText("Completed a fire task!"),false);
                        break;
                    }
                }


                list = theoryTaskAccessor.getWaterTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeWaterTask(i);
                        player.getStackInHand(hand).decrement(1);
                        player.sendMessage(new LiteralText("Completed a water task!"),false);
                        break;
                    }
                }


                list = theoryTaskAccessor.getEarthTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeEarthTask(i);
                        player.getStackInHand(hand).decrement(1);
                        player.sendMessage(new LiteralText("Completed a earth task!"),false);
                        break;
                    }
                }


                list = theoryTaskAccessor.getAirTasks();
                for (int i = 0; i < list.size(); i++) {
                    if(list.getCompound(i).getString("item").equals(search))
                    {
                        theoryTaskAccessor.removeAirTask(i);
                        player.getStackInHand(hand).decrement(1);
                        player.sendMessage(new LiteralText("Completed a air task!"),false);
                        break;
                    }
                }

            }
        }
        return ActionResult.PASS;
    }
}
