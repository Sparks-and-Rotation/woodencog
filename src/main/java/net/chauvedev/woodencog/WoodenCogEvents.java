package net.chauvedev.woodencog;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.chauvedev.woodencog.capability.MachineCapacity;
import net.chauvedev.woodencog.capability.MachineCapacityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class WoodenCogEvents {



    @Mod.EventBusSubscriber(modid = WoodenCog.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {

            if (! event.getCapabilities().containsKey(MachineCapacityProvider.MACHINE_CAPACITY_KEY)){
                try {
                    if(event.getObject() instanceof KineticBlockEntity) {
                        event.addCapability(MachineCapacityProvider.MACHINE_CAPACITY_KEY, new MachineCapacityProvider());
                    }
                }catch (Error error){
                    WoodenCog.LOGGER.debug("-------ERROR---");
                    WoodenCog.LOGGER.error("Error found",error);
                }
            }

        }

        @SubscribeEvent
        public static void onAttachCapabilitiesItemStack(AttachCapabilitiesEvent<ItemStack> event) {


            if (! event.getCapabilities().containsKey(MachineCapacityProvider.MACHINE_CAPACITY_KEY)){
                try {
                    if(event.getObject().getItem() instanceof BlockItem) {
                        Block block = ((BlockItem) event.getObject().getItem()).getBlock();
                        if (block instanceof KineticBlock){
                            event.addCapability(MachineCapacityProvider.MACHINE_CAPACITY_KEY, new MachineCapacityProvider());
                        }
                    }
                }catch (Error error){
                    WoodenCog.LOGGER.debug("-------ERROR---");
                    WoodenCog.LOGGER.error("Error found",error);
                }
            }


        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            WoodenCog.LOGGER.debug("Register MachineCapacity");
            event.register(MachineCapacity.class);
        }

    }
}


