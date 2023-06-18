package net.chauvedev.woodencog.capability;


import net.chauvedev.woodencog.WoodenCog;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MachineCapacityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<MachineCapacity> MACHINE_CAPACITY = CapabilityManager.get(new CapabilityToken<MachineCapacity>() { });
    public static ResourceLocation MACHINE_CAPACITY_KEY = new ResourceLocation(WoodenCog.MOD_ID, "machine_capacity");


    private MachineCapacity machineCapacity = null;
    private final LazyOptional<MachineCapacity> optional = LazyOptional.of(this::createMachineCapacity);

    private MachineCapacity createMachineCapacity() {
        if(this.machineCapacity == null) {
            this.machineCapacity = new MachineCapacity();
        }
        return this.machineCapacity;
    }


    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == MACHINE_CAPACITY) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("machine_capacity",createMachineCapacity().toTag());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.machineCapacity = new MachineCapacity();
        if (nbt.contains("machine_capacity")){
            this.machineCapacity.toTag(nbt.getCompound("machine_capacity"));
        }
    }
}

