package net.chauvedev.woodencog.item.fluids.can;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class FireclayCrucibleFluidHandler implements IFluidHandlerItem, ICapabilityProvider {
    private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);
    private final ItemStack container;

    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, this.holder);
    }

    public int getTanks() {
        return 1;
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return true;
    }

    public int getTankCapacity(int tank) {
        return FireclayCrucibleItem.CAPACITY;
    }

    private Fluid getFluid() {
        return FireclayCrucibleItem.getFluid(this.container);
    }

    @Nullable
    private CompoundTag getFluidTag() {
        return FireclayCrucibleItem.getFluidTag(this.container);
    }

    @Nonnull
    public FluidStack getFluidInTank(int tank) {
        return new FluidStack(this.getFluid(), FireclayCrucibleItem.CAPACITY, this.getFluidTag());
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if (this.getFluid() == Fluids.EMPTY && resource.getAmount() >= FireclayCrucibleItem.CAPACITY) {
            if (action.execute()) {
                FireclayCrucibleItem.setFluid(this.container, resource);
            }

            return FireclayCrucibleItem.CAPACITY;
        } else {
            return 0;
        }
    }

    @Nonnull
    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (!resource.isEmpty() && resource.getAmount() >= FireclayCrucibleItem.CAPACITY) {
            Fluid fluid = this.getFluid();
            if (fluid != Fluids.EMPTY && fluid == resource.getFluid()) {
                FluidStack output = new FluidStack(fluid, FireclayCrucibleItem.CAPACITY, this.getFluidTag());
                if (action.execute()) {
                    FireclayCrucibleItem.setFluid(this.container, FluidStack.EMPTY);
                }

                return output;
            } else {
                return FluidStack.EMPTY;
            }
        } else {
            return FluidStack.EMPTY;
        }
    }

    @Nonnull
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        if (maxDrain < FireclayCrucibleItem.CAPACITY) {
            return FluidStack.EMPTY;
        } else {
            Fluid fluid = this.getFluid();
            if (fluid == Fluids.EMPTY) {
                return FluidStack.EMPTY;
            } else {
                FluidStack output = new FluidStack(fluid, FireclayCrucibleItem.CAPACITY, this.getFluidTag());
                if (action.execute()) {
                    FireclayCrucibleItem.setFluid(this.container, FluidStack.EMPTY);
                }

                return output;
            }
        }
    }

    public FireclayCrucibleFluidHandler(ItemStack container) {
        this.container = container;
    }

    public ItemStack getContainer() {
        return this.container;
    }
}
