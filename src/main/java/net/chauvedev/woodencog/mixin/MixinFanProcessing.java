package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.FanProcessing;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.capabilities.food.FoodTraits;
import net.dries007.tfc.common.capabilities.heat.Heat;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.capabilities.heat.IHeat;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.common.recipes.inventory.ItemStackInventory;
import net.dries007.tfc.util.Helpers;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(value = FanProcessing.class, remap = false)
public class MixinFanProcessing {



    private static ItemStack applyProcessingTCF(ItemStack inputStack, FanProcessing.Type type) {


        if (!inputStack.getCapability(HeatCapability.CAPABILITY).isPresent()){
            return inputStack;
        }
        IHeat cap = inputStack.getCapability(HeatCapability.CAPABILITY).resolve().get();


        float itemTemp = cap.getTemperature();
        HeatCapability.addTemp(cap, 10000);
        HeatingRecipe recipe = HeatingRecipe.getRecipe(inputStack);

        if (recipe!=null){
            if ((double)itemTemp > 1.1 * (double)recipe.getTemperature()) {
                if (recipe.assemble(new ItemStackInventory(inputStack)).isEmpty()){
                    return null;
                }
            }
            if (recipe.isValidTemperature(cap.getTemperature()))
            {
                ItemStack output = recipe.assemble(new ItemStackInventory(inputStack));
                FluidStack fluidStack = recipe.assembleFluid(new ItemStackInventory(inputStack));
                FoodCapability.applyTrait(output, FoodTraits.WOOD_GRILLED);
                if (!output.isEmpty()){
                    output.setCount(inputStack.getCount());
                    return output;
                }else{
                    return inputStack;
                }
            }
        }
        return inputStack;
    }

    @Inject(
            method = {"applyProcessing(Lcom/simibubi/create/content/kinetics/belt/transport/TransportedItemStack;Lnet/minecraft/world/level/Level;Lcom/simibubi/create/content/kinetics/fan/FanProcessing$Type;)Lcom/simibubi/create/content/kinetics/belt/behaviour/TransportedItemStackHandlerBehaviour$TransportedResult;"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private static void applyProcessing(TransportedItemStack transported, Level world, FanProcessing.Type type,CallbackInfoReturnable<TransportedItemStackHandlerBehaviour.TransportedResult> cir) {
        boolean hasHeat = transported.stack.getCapability(HeatCapability.CAPABILITY).isPresent();
        if (hasHeat)
        {
            transported.stack = MixinFanProcessing.applyProcessingTCF(transported.stack,type);
            cir.setReturnValue(TransportedItemStackHandlerBehaviour.TransportedResult.doNothing());
            cir.cancel();
        }
    }


    @Inject(
            method = {"applyProcessing(Lnet/minecraft/world/entity/item/ItemEntity;Lcom/simibubi/create/content/kinetics/fan/FanProcessing$Type;)Z"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private static void applyProcessing(ItemEntity entity, FanProcessing.Type type, CallbackInfoReturnable<Boolean> cir) {

        ItemStack inputStack = entity.getItem();


        boolean hasHeat = inputStack.getCapability(HeatCapability.CAPABILITY).isPresent();

        if (hasHeat)
        {
            ItemStack result = MixinFanProcessing.applyProcessingTCF(inputStack,type);

            if (result==null){
                entity.kill();
            }else{
                entity.setItem(result);
            }
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
