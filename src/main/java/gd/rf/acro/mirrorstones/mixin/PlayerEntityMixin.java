package gd.rf.acro.mirrorstones.mixin;

import gd.rf.acro.mirrorstones.Mirrorstones;
import gd.rf.acro.mirrorstones.interfaces.TheoryTaskAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements TheoryTaskAccessor {
    private static final TrackedData<NbtCompound> AIR_TASKS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    private static final TrackedData<NbtCompound> WATER_TASKS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    private static final TrackedData<NbtCompound> EARTH_TASKS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    private static final TrackedData<NbtCompound> FIRE_TASKS = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void addTaskForAir() {
        NbtCompound tag = dataTracker.get(AIR_TASKS);
        NbtList list = new NbtList();
        if(tag.contains("list"))
        {
            list= (NbtList) tag.get("list");
        }
        NbtCompound task = new NbtCompound();
        task.putLong("timeout",world.getLunarTime()+24000L);
        task.putString("item",Mirrorstones.AIR_ITEMS.getRandom(this.random).toString());
        list.add(task);
        tag.put("list",list);
        dataTracker.set(AIR_TASKS,tag);

    }

    @Override
    public NbtList getAirTasks() {
        return (NbtList) dataTracker.get(AIR_TASKS).get("list");
    }

    @Override
    public void removeAirTask(int i) {
        NbtCompound tag = dataTracker.get(AIR_TASKS);
        NbtList list = (NbtList) tag.get("list");
        list.remove(i);
        tag.put("list",list);
        dataTracker.set(AIR_TASKS,tag);
    }

    @Override
    public boolean hasFailedAirTask() {
        NbtCompound tag = dataTracker.get(AIR_TASKS);
        if(tag.contains("list"))
        {
            NbtList list = (NbtList) tag.get("list");
            for (NbtElement task : list) {
                NbtCompound ind = (NbtCompound) task;
                if (ind.getLong("timeout") < world.getLunarTime()) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void addTaskForWater() {
        NbtCompound tag = dataTracker.get(WATER_TASKS);
        NbtList list = new NbtList();
        if(tag.contains("list"))
        {
            list= (NbtList) tag.get("list");
        }
        NbtCompound task = new NbtCompound();
        task.putLong("timeout",world.getLunarTime()+24000L);
        task.putString("item",Mirrorstones.WATER_ITEMS.getRandom(this.random).toString());
        list.add(task);
        tag.put("list",list);
        dataTracker.set(WATER_TASKS,tag);

    }
    @Override
    public NbtList getWaterTasks() {
        return (NbtList) dataTracker.get(WATER_TASKS).get("list");
    }

    @Override
    public void removeWaterTask(int i) {
        NbtCompound tag = dataTracker.get(WATER_TASKS);
        NbtList list = (NbtList) tag.get("list");
        list.remove(i);
        tag.put("list",list);
        dataTracker.set(WATER_TASKS,tag);
    }

    @Override
    public boolean hasFailedWaterTask() {
        NbtCompound tag = dataTracker.get(WATER_TASKS);
        if(tag.contains("list"))
        {
            NbtList list = (NbtList) tag.get("list");
            for (NbtElement task : list) {
                NbtCompound ind = (NbtCompound) task;
                if (ind.getLong("timeout") < world.getLunarTime()) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void addTaskForFire() {
        NbtCompound tag = dataTracker.get(FIRE_TASKS);
        NbtList list = new NbtList();
        if(tag.contains("list"))
        {
            list= (NbtList) tag.get("list");
        }
        NbtCompound task = new NbtCompound();
        task.putLong("timeout",world.getLunarTime()+24000L);
        task.putString("item",Mirrorstones.FIRE_ITEMS.getRandom(this.random).toString());
        list.add(task);
        tag.put("list",list);
        dataTracker.set(FIRE_TASKS,tag);

    }
    @Override
    public NbtList getFireTasks() {
        return (NbtList) dataTracker.get(FIRE_TASKS).get("list");
    }

    @Override
    public void removeFireTask(int i) {
        NbtCompound tag = dataTracker.get(FIRE_TASKS);
        NbtList list = (NbtList) tag.get("list");
        list.remove(i);
        tag.put("list",list);
        dataTracker.set(FIRE_TASKS,tag);
    }

    @Override
    public boolean hasFailedFireTask() {
        NbtCompound tag = dataTracker.get(FIRE_TASKS);
        if(tag.contains("list"))
        {
            NbtList list = (NbtList) tag.get("list");
            for (NbtElement task : list) {
                NbtCompound ind = (NbtCompound) task;
                if (ind.getLong("timeout") < world.getLunarTime()) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void addTaskForEarth() {
        NbtCompound tag = dataTracker.get(EARTH_TASKS);
        NbtList list = new NbtList();
        if(tag.contains("list"))
        {
            list= (NbtList) tag.get("list");
        }
        NbtCompound task = new NbtCompound();
        task.putLong("timeout",world.getLunarTime()+24000L);
        task.putString("item", Mirrorstones.EARTH_ITEMS.getRandom(this.random).toString());
        list.add(task);
        tag.put("list",list);
        dataTracker.set(EARTH_TASKS,tag);

    }
    @Override
    public NbtList getEarthTasks() {
        return (NbtList) dataTracker.get(EARTH_TASKS).get("list");
    }

    @Override
    public void removeEarthTask(int i) {
        NbtCompound tag = dataTracker.get(EARTH_TASKS);
        NbtList list = (NbtList) tag.get("list");
        list.remove(i);
        tag.put("list",list);
        dataTracker.set(EARTH_TASKS,tag);
    }

    @Override
    public boolean hasFailedEarthTask() {
        NbtCompound tag = dataTracker.get(AIR_TASKS);
        if(tag.contains("list"))
        {
            NbtList list = (NbtList) tag.get("list");
            for (NbtElement task : list) {
                NbtCompound ind = (NbtCompound) task;
                if (ind.getLong("timeout") < world.getLunarTime()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        dataTracker.set(AIR_TASKS,nbt.getCompound("air_tasks"));
        dataTracker.set(EARTH_TASKS,nbt.getCompound("earth_tasks"));
        dataTracker.set(WATER_TASKS,nbt.getCompound("water_tasks"));
        dataTracker.set(FIRE_TASKS,nbt.getCompound("fire_tasks"));
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("air_tasks",dataTracker.get(AIR_TASKS));
        nbt.put("fire_tasks",dataTracker.get(FIRE_TASKS));
        nbt.put("water_tasks",dataTracker.get(WATER_TASKS));
        nbt.put("earth_tasks",dataTracker.get(EARTH_TASKS));
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void initDataTracker(CallbackInfo ci) {
        NbtCompound a = new NbtCompound();
        a.put("list",new NbtList());
        dataTracker.startTracking(AIR_TASKS,a.copy());
        dataTracker.startTracking(EARTH_TASKS,a.copy());
        dataTracker.startTracking(FIRE_TASKS,a.copy());
        dataTracker.startTracking(WATER_TASKS,a.copy());
    }
}
