package net.chauvedev.woodencog.capability;


import net.minecraft.nbt.CompoundTag;
import org.checkerframework.checker.units.qual.C;

public class MachineCapacity {

    private float machine_capacity = 0;
    private boolean destroyed = false;

    public float getDurability(){
        return this.machine_capacity;
    }

    public void setDurability(float var1){
        this.machine_capacity = var1;
    }

    public boolean isDestroyed(){
        return this.destroyed;
    }

    public void setDestroyed(boolean var1){
        this.destroyed = var1;
    }

    public CompoundTag toTag(){
        CompoundTag tag = new CompoundTag();
        tag.putFloat("machine_usage",this.machine_capacity);
        tag.putBoolean("machine_destroyed",this.destroyed);
        return tag;
    }
    public void toTag(CompoundTag tag){
        if (tag.contains("machine_usage")){
            this.machine_capacity = tag.getFloat("machine_usage");
        }
        if (tag.contains("machine_destroyed")){
            this.destroyed = tag.getBoolean("machine_destroyed");
        }

    }
}
