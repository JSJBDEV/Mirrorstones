package gd.rf.acro.mirrorstones.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TheoryItem extends Item {
    String tag;
    public TheoryItem(Settings settings,String theory) {
        super(settings);
        this.tag=theory;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.addScoreboardTag(tag);
        user.getStackInHand(hand).decrement(1);
        return super.use(world, user, hand);
    }
}
