package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.logistics.filter.FilterItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FilterItem.class, remap = false)
public class MixinFilterItem {

    /**
     * Trick to enable bucket filter for tfc
     */
    @Inject(
            method = {"test(Lnet/minecraft/world/level/Level;Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/world/item/ItemStack;Z)Z"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"
            )},
            remap = false
    )
    private static void test(Level world, FluidStack stack, ItemStack filter, boolean matchNBT, CallbackInfoReturnable<Boolean> cir){
        if(stack.getTag() != null) {
            if(stack.getTag().isEmpty()) {
                stack.setTag(null);
            }
        }
    }
}
