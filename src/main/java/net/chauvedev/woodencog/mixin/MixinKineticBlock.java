package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.chauvedev.woodencog.capability.MachineCapacity;
import net.chauvedev.woodencog.capability.MachineCapacityProvider;
import net.dries007.tfc.common.TFCTags;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;


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
            ItemStack itemInHand = pPlayer.getMainHandItem();
            if(itemInHand.is(TFCTags.Items.HAMMERS)) {
                if(capacity.isDestroyed()) {
                    pLevel.playLocalSound(
                            pPos.getX(),
                            pPos.getY(),
                            pPos.getZ(),
                            SoundEvents.ITEM_BREAK,
                            SoundSource.BLOCKS,
                            1,
                            1,
                            false
                    );
                } else {
                    capacity.setDurability(0);
                    int durability_to_remove = (int)(capacity.getDurability() / 10) + 1;
                    itemInHand.hurtAndBreak(durability_to_remove, pPlayer, (player -> player.broadcastBreakEvent(player.getUsedItemHand())));
                    pLevel.playLocalSound(
                            pPos.getX(),
                            pPos.getY(),
                            pPos.getZ(),
                            SoundEvents.ANVIL_USE,
                            SoundSource.BLOCKS,
                            1,
                            1,
                            false
                    );
                }

            }
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
