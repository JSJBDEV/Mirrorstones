package gd.rf.acro.mirrorstones.interfaces;

import net.minecraft.nbt.NbtList;

public interface TheoryTaskAccessor {
    void addTaskForFire();
    void addTaskForAir();
    void addTaskForWater();
    void addTaskForEarth();
    NbtList getAirTasks();
    NbtList getFireTasks();
    NbtList getWaterTasks();
    NbtList getEarthTasks();
    void removeAirTask(int i);
    void removeWaterTask(int i);
    void removeEarthTask(int i);
    void removeFireTask(int i);
    boolean hasFailedFireTask();
    boolean hasFailedWaterTask();
    boolean hasFailedAirTask();
    boolean hasFailedEarthTask();
}
