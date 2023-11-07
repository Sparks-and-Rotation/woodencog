package net.chauvedev.woodencog.mixin.recipes;

import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeApplier;
import net.chauvedev.woodencog.WoodenCog;
import net.chauvedev.woodencog.recipes.advancedProcessingRecipe.AllAdvancedRecipeTypes;
import net.chauvedev.woodencog.recipes.advancedProcessingRecipe.baseRecipes.SetItemStackProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.items.ItemHandlerHelper;
import org.checkerframework.checker.units.qual.A;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Mixin(value = RecipeApplier.class, remap = false)
public class MixinRecipeApplier {


    /**
     * @author DeltaAnto
     * @reason Replace method to allow usage of current item not referenced item
     */
    @Overwrite
    public static List<ItemStack> applyRecipeOn(ItemStack stackIn, Recipe<?> recipe) {
        List<ItemStack> stacks;

        if (recipe instanceof ProcessingRecipe<?> pr) {
            stacks = new ArrayList<>();


            boolean is_advanced_recipe = AllAdvancedRecipeTypes.CACHES.containsKey(pr.getId().toString());
            for(int i = 0; i < stackIn.getCount(); ++i) {
                List var10000;
                if (pr instanceof ManualApplicationRecipe mar) {
                    var10000 = mar.getRollableResults();
                } else {
                    var10000 = pr.getRollableResults();
                }

                List<ProcessingOutput> outputs = var10000;

                Iterator var12 = pr.rollResults(outputs).iterator();

                while(var12.hasNext()) {
                    ItemStack stack = (ItemStack)var12.next();
                    Iterator var8 = ((List)stacks).iterator();



                    while(var8.hasNext()) {
                        ItemStack previouslyRolled = (ItemStack)var8.next();
                        if (!stack.isEmpty() && ItemHandlerHelper.canItemStacksStack(stack, previouslyRolled)) {
                            int amount = Math.min(previouslyRolled.getMaxStackSize() - previouslyRolled.getCount(), stack.getCount());
                            previouslyRolled.grow(amount);
                            stack.shrink(amount);
                        }
                    }

                    if (!stack.isEmpty()) {
                        stacks.add(stack);
                    }
                }
            }

            if (is_advanced_recipe){
                ArrayList<ItemStack> newStacks = new ArrayList<>();

                SetItemStackProvider provider = AllAdvancedRecipeTypes.CACHES.get(pr.getId().toString());
                (stacks).forEach(o -> {
                    newStacks.add(provider.onResultStackSingle(stackIn,o));
                });

                stacks = newStacks;

            }

        } else {
            ItemStack out = recipe.getResultItem().copy();
            stacks = ItemHelper.multipliedOutput(stackIn, out);
        }

        return stacks;
    }
}
