package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Objects;

@Mixin(value = KineticBlockEntity.class, remap = false)
public abstract class MixinKineticBlockEntity{


    private boolean lifeBlockBlocked = false;
    private float lifeBlock = 20*10;

    @Shadow public abstract KineticNetwork getOrCreateNetwork();

    @Shadow protected float speed;

    @Shadow protected float stress;

    @Shadow protected boolean overStressed;

    @Inject(
            method = {"tick"},
            at = {@At("HEAD")},
            remap = false,
            cancellable = true)
    public void tick(CallbackInfo ci){

    }

    @Inject(
            method = {"write"},
            at = {@At("HEAD")},
            remap = false
    )
    public void write(CompoundTag compound, boolean clientPacket, CallbackInfo ci){

    }

    @Inject(
            method = {"read"},
            at = {@At("HEAD")},
            remap = false)
    protected void read(CompoundTag compound, boolean clientPacket, CallbackInfo ci) {

    }

}
