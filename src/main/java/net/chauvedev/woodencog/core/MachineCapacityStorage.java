package net.chauvedev.woodencog.core;

import com.simibubi.create.AllBlocks;
import net.chauvedev.woodencog.WoodenCog;
import net.chauvedev.woodencog.capability.MachineCapacity;
import net.chauvedev.woodencog.config.CustomBlockConfig;
import net.chauvedev.woodencog.config.WoodenCogCommonConfigs;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class MachineCapacityStorage {

    private static MachineCapacityStorage INSTANCE = null;

    public static MachineCapacityStorage getInstance(){
        return INSTANCE;
    }

    public HashMap<String,MachineCapacityEntry> machineCapacityEntryMap_cache;


    public boolean active = true;

    public MachineCapacityStorage(){
        MachineCapacityStorage.INSTANCE = this;
        this.machineCapacityEntryMap_cache = new HashMap<>();
    }

    public boolean isBlackListBlock(BlockEntity blockEntity){
        Block block = blockEntity.getBlockState().getBlock();

        String full = block.getRegistryName().getNamespace()+":"+block.getRegistryName().getPath();

        return WoodenCogCommonConfigs.WEAR_BLACKLIST.get().contains(full);
    }
    public MachineCapacityEntry getCapacity(Block block){
        String full = block.getRegistryName().getNamespace()+":"+block.getRegistryName().getPath();
        try {
            CustomBlockConfig.BlockInformation info = CustomBlockConfig.registeredBlocks.get(full);
            return MachineCapacityEntry.createEntryBlock(false,
                    info.durability,
                    (int) (info.chance * 100)
            );
        } catch (NullPointerException e) {
            return MachineCapacityEntry.createEntryBlock(false,
                    WoodenCogCommonConfigs.DEFAULT_DURABILITY.get(),
                    (int) (WoodenCogCommonConfigs.DEFAULT_DAMAGE_CHANCE.get() * 100)
            );
        }

    }

}
