package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.chauvedev.woodencog.capability.MachineCapacity;
import net.chauvedev.woodencog.capability.MachineCapacityProvider;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import static net.dries007.tfc.util.Metal.ItemType.HAMMER;

@Mixin(value= KineticBlock.class, remap = false)
public class MixinKineticBlock extends Block {

    public MixinKineticBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof KineticBlockEntity kineticBlockEntity) {
            MachineCapacity capacity = kineticBlockEntity.getCapability(MachineCapacityProvider.MACHINE_CAPACITY).resolve().get();
            if(pPlayer.getOffhandItem().is(TFCTags.Items.HAMMERS)) {
                capacity.setDurability(0);
                capacity.setDestroyed(false);
                if(kineticBlockEntity.getOrCreateNetwork() != null) {
                    kineticBlockEntity.getOrCreateNetwork().sync();
                } else {
                    System.out.println("oh no network is null");
                }
            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
