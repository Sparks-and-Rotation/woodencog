package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.contraptions.processing.BasinRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = BasinRecipe.class, remap = false)
public abstract class MixinBasin {

    /**
     * @author
     * @reason allow more fluids input for tfc
     */
    @Overwrite()
    protected int getMaxFluidInputCount() {
        return 4;
    }
}
