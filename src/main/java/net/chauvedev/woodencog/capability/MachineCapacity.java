package net.chauvedev.woodencog.capability;


import net.minecraft.nbt.CompoundTag;
import org.checkerframework.checker.units.qual.C;

public class MachineCapacity {

    private float machine_capacity = 0;
    private float machine_capacity_max = 0;



    public float getDurability(){
        return this.machine_capacity;
    }
    public float getMaxDurability(){
        return this.machine_capacity_max;
    }

    public void setDurability(float var1){
        this.machine_capacity = var1;
    }
    public void setMaxDurability(float var1){
        this.machine_capacity_max = var1;
    }


    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putFloat("machine_capacity",this.machine_capacity);
        tag.putFloat("machine_capacity_max",this.machine_capacity_max);
        return tag;
    }
    public void toTag(CompoundTag tag){
        if (tag.contains("machine_capacity")){
            this.machine_capacity = tag.getFloat("machine_capacity");
        }
        if (tag.contains("machine_capacity_max")){
            this.machine_capacity_max = tag.getFloat("machine_capacity_max");
        }
    }
}
