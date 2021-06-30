package gd.rf.acro.mirrorstones.structures;

import gd.rf.acro.mirrorstones.Mirrorstones;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class MirrorstoneGenerator {
    private static final Identifier MIRRORSTONE = new Identifier("mirrostones","mirrorstone");

    public static void addPieces(StructureManager manager, BlockPos pos, BlockRotation rotation, List<StructurePiece> pieces) {
        if(pieces.size()<1)
        {
            pieces.add(new MyPiece(manager, pos, rotation));
        }
    }

    public static class MyPiece extends SimpleStructurePiece {


        public MyPiece(StructureManager structureManager, BlockPos pos,BlockRotation rotation) {
            super(Mirrorstones.MY_PIECE, 0, structureManager, MIRRORSTONE, MIRRORSTONE.toString(), createPlacementData(rotation), pos);
        }

        public MyPiece(ServerWorld serverWorld, NbtCompound nbtCompound) {
            super(Mirrorstones.MY_PIECE, nbtCompound, serverWorld, (identifier) -> createPlacementData(BlockRotation.valueOf(nbtCompound.getString("Rot"))));
        }

        protected void writeNbt(ServerWorld world, NbtCompound nbt) {
            super.writeNbt(world, nbt);
            nbt.putString("Rot", this.placementData.getRotation().name());
        }

        private static final BlockState[] ALCHEMY_STONES = {Mirrorstones.ALCHEMICAL_FIRE.getDefaultState(),Mirrorstones.ALCHEMICAL_WATER.getDefaultState(),Mirrorstones.ALCHEMICAL_EARTH.getDefaultState(),Mirrorstones.ALCHEMICAL_AIR.getDefaultState()};
        @Override
        public boolean generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {

            int l = RandomUtils.nextInt(2,9);
            for (int i = 0; i < l; i++) {
                world.setBlockState(pos.add(0,i,0), ALCHEMY_STONES[RandomUtils.nextInt(0,ALCHEMY_STONES.length)], 3);
            }
            world.setBlockState(pos.add(0,l,0), Mirrorstones.ALCHEMICAL_AETHER.getDefaultState(), 3);
            return super.generate(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);
        }

        @Override
        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {

        }

        private static StructurePlacementData createPlacementData(BlockRotation rotation) {
            return (new StructurePlacementData()).setRotation(rotation).setMirror(BlockMirror.NONE);
        }
    }

}
