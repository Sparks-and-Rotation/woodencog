package net.chauvedev.woodencog.mixin;

import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelBlockEntity;
import com.simibubi.create.content.kinetics.transmission.ClutchBlockEntity;
import net.chauvedev.woodencog.WoodenCog;
import net.chauvedev.woodencog.capability.MachineCapacity;
import net.chauvedev.woodencog.capability.MachineCapacityProvider;
import net.chauvedev.woodencog.core.MachineCapacityEntry;
import net.chauvedev.woodencog.core.MachineCapacityStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
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

    @Shadow public abstract void setSpeed(float speed);

    @Shadow protected float capacity;

    @Shadow public abstract float calculateAddedStressCapacity();

    @Shadow public abstract float calculateStressApplied();

    @Shadow protected float lastStressApplied;
    @Shadow protected float lastCapacityProvided;

    @Shadow public abstract void detachKinetics();

    boolean destroyed = false;

    @Inject(
            method = {"tick"},
            at = {@At("HEAD")},
            remap = false)
    public void tick(CallbackInfo ci){
        KineticBlockEntity block = (KineticBlockEntity)((Object)this) ;
        try{
            this.tickDamagedTick(block);
        }catch (Error error){
            WoodenCog.LOGGER.debug("Error pendant le system tick");
            error.printStackTrace();
        }
    }

    public void tickDamagedTick(KineticBlockEntity block){
        if (!MachineCapacityStorage.getInstance().active){
            return;
        }
        if (MachineCapacityStorage.getInstance().isBlackListBlock(block)){
            return;
        }
        MachineCapacityEntry config = MachineCapacityStorage.getInstance().getCapacity(block.getBlockState().getBlock());
        MachineCapacity capacity = block.getCapability(MachineCapacityProvider.MACHINE_CAPACITY).resolve().get();
        int chance = block.getLevel().random.nextInt(0,100);

        float left = config.durabilityMax - capacity.getDurability();

        if (chance > (100-config.damageChance) && left > 0 && block.getSpeed() > 0){
            capacity.setDurability(capacity.getDurability()+1);
        }

        left = config.durabilityMax - capacity.getDurability();

        if (left<10){
            if (getOrCreateNetwork()!=null){
                if (!destroyed || !capacity.isDestroyed() || this.stress != Integer.MAX_VALUE){
                    destroyed = true;
                    getOrCreateNetwork().updateCapacityFor(block,0);
                    getOrCreateNetwork().updateStressFor(block, Integer.MAX_VALUE);
                    getOrCreateNetwork().updateNetwork();
                    getOrCreateNetwork().sync();
                    capacity.setDestroyed(true);
                }
            }
            block.getLevel().addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,false,block.getBlockPos().getX()+0.5f,block.getBlockPos().getY()+0.5f,block.getBlockPos().getZ()+0.5f,0,0.01,0);
        }
    }

}
