package net.chauvedev.woodencog.recipes.advancedProcessingRecipe.baseRecipes;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.List;

public interface SetItemStackProvider {

    void setItemStackProviders(ArrayList<ItemStackProvider> providers);
    ArrayList<ItemStackProvider> getItemStackProviders();
    ArrayList<ProcessingOutput> onResult(NonNullList<ProcessingOutput> results);
    ItemStack onResultStackSingle(ItemStack input,ItemStack stack);
}


