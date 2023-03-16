package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinRecipe;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.foundation.utility.recipe.RecipeFinder;
import net.chauvedev.woodencog.WoodenCog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(value = BasinOperatingTileEntity.class, remap = false)
public abstract class MixinBasinRecipe extends KineticTileEntity {
    public MixinBasinRecipe(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    /**
     * @author ChauveDev
     * @reason This function didn't take in account the fluid ingredients which is pretty bad in a basin
     */
    @Overwrite
    protected List<Recipe<?>> getMatchingRecipes() {
        if (this.getBasin().map(BasinTileEntity::isEmpty).orElse(true)) {
            return new ArrayList();
        } else {
            List<Recipe<?>> list = RecipeFinder.get(this.getRecipeCacheKey(), this.level, this::matchStaticFilters);
            return list.stream().filter(this::matchBasinRecipe).sorted((r1, r2) -> {
                int r1Size = (r1.getIngredients().size() + ((BasinRecipe)r1).getFluidIngredients().size());
                int r2Size = (r2.getIngredients().size() + ((BasinRecipe)r2).getFluidIngredients().size());
                return r2Size - r1Size;
            }).collect(Collectors.toList());
        }
    }

    @Shadow() abstract boolean matchStaticFilters(Recipe<?> recipe);

    @Shadow() abstract boolean matchBasinRecipe(Recipe<?> recipe);

    @Shadow() abstract Object getRecipeCacheKey();

    @Shadow() abstract Optional<BasinTileEntity> getBasin();
}
