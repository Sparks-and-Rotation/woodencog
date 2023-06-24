package net.chauvedev.woodencog.core;

import net.chauvedev.woodencog.capability.MachineCapacity;
import net.minecraft.world.level.block.Block;

public class MachineCapacityEntry {


    public boolean isBlackList = false;
    public String registryName = "";
    public float durabilityMax = 500;

    public int damageChance = 100;


    public static MachineCapacityEntry createEntryBlock(boolean isBlackList,float durabilityMax,int damageChance){
        MachineCapacityEntry entry = new MachineCapacityEntry();
        entry.isBlackList = isBlackList;
        entry.durabilityMax = durabilityMax;
        entry.damageChance = damageChance;
        return entry;
    }
}
