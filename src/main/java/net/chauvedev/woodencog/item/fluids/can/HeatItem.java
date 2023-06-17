package net.chauvedev.woodencog.item.fluids.can;

import net.dries007.tfc.common.capabilities.DelegateHeatHandler;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.capabilities.heat.HeatHandler;
import net.dries007.tfc.common.capabilities.heat.IHeat;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatItem extends Item {
    public HeatItem(Properties pProperties) {
        super(pProperties);
    }

    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new HeatItem.HeatItemCapability();
    }

    static class HeatItemCapability implements ICapabilityProvider, INBTSerializable<CompoundTag>, DelegateHeatHandler {
        private final HeatHandler heat;
        private final LazyOptional<IHeat> capability = LazyOptional.of(() -> this);

        HeatItemCapability() {
            this.heat = new HeatHandler(1.0F, 0.0F, 0.0F);
        }

        @Override
        public @NotNull IHeat getHeatHandler() {
            return this.heat;
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if(cap == HeatCapability.CAPABILITY) {
                return capability.cast();
            }
            return LazyOptional.empty();
        }
    }
}
