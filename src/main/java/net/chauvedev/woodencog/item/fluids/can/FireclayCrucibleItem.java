package net.chauvedev.woodencog.item.fluids.can;

import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FireclayCrucibleItem extends Item {
    private static final String TAG_FLUID = "fluid";
    private static final String TAG_FLUID_TAG = "fluid_tag";

    protected static final int CAPACITY = 100;

    public FireclayCrucibleItem(Item.Properties properties) {
        super(properties);
    }

    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FireclayCrucibleFluidHandler(stack);
    }

    public boolean hasContainerItem(ItemStack stack) {
        return getFluid(stack) != Fluids.EMPTY;
    }

    public ItemStack getContainerItem(ItemStack stack) {
        Fluid fluid = getFluid(stack);
        return fluid != Fluids.EMPTY ? new ItemStack(this) : ItemStack.EMPTY;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        Fluid fluid = getFluid(stack);
        if (fluid != Fluids.EMPTY) {
            CompoundTag fluidTag = getFluidTag(stack);
            Object text;
            if (fluidTag != null) {
                FluidStack displayFluid = new FluidStack(fluid, FireclayCrucibleItem.CAPACITY, fluidTag);
                text = displayFluid.getDisplayName().plainCopy();
            } else {
                text = new TranslatableComponent(fluid.getAttributes().getTranslationKey());
            }

            tooltip.add((new TranslatableComponent(this.getDescriptionId() + ".contents", new Object[]{text})).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add((new TranslatableComponent(this.getDescriptionId() + ".tooltip")).withStyle(ChatFormatting.GRAY));
        }

    }

    public static ItemStack setFluid(ItemStack stack, FluidStack fluid) {
        CompoundTag nbt;
        if (fluid.isEmpty()) {
            nbt = stack.getTag();
            if (nbt != null) {
                nbt.remove("fluid");
                nbt.remove("fluid_tag");
                if (nbt.isEmpty()) {
                    stack.setTag((CompoundTag)null);
                }
            }
        } else {
            nbt = stack.getOrCreateTag();
            nbt.putString("fluid", Objects.requireNonNull(fluid.getFluid().getRegistryName()).toString());
            CompoundTag fluidTag = fluid.getTag();
            if (fluidTag != null) {
                nbt.put("fluid_tag", fluidTag.copy());
            } else {
                nbt.remove("fluid_tag");
            }
        }

        return stack;
    }

    public static Fluid getFluid(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            ResourceLocation location = ResourceLocation.tryParse(nbt.getString("fluid"));
            if (location != null && ForgeRegistries.FLUIDS.containsKey(location)) {
                Fluid fluid = (Fluid)ForgeRegistries.FLUIDS.getValue(location);
                if (fluid != null) {
                    return fluid;
                }
            }
        }

        return Fluids.EMPTY;
    }

    @Nullable
    public static CompoundTag getFluidTag(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null && nbt.contains("fluid_tag", 10) ? nbt.getCompound("fluid_tag") : null;
    }

    public static String getSubtype(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        return nbt != null ? nbt.getString("fluid") : "";
    }
}
