package net.chauvedev.woodencog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.chauvedev.woodencog.commands.WoodenCogCommand;
import net.chauvedev.woodencog.config.WoodenCogCommonConfigs;
import net.chauvedev.woodencog.core.MachineCapacityStorage;
import net.chauvedev.woodencog.interaction.CustomArmInteractionPointTypes;
import net.chauvedev.woodencog.item.fluids.can.FireclayCrucibleItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(WoodenCog.MOD_ID)
public class WoodenCog
{
    public static final String MOD_ID = "woodencog";
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);
    public static final Logger LOGGER = LogUtils.getLogger();
    private final MachineCapacityStorage machineCapacityStorage;

    public WoodenCog()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        CustomArmInteractionPointTypes.registerAll();
        MinecraftForge.EVENT_BUS.register(this);

        CustomArmInteractionPointTypes.registerAll();

        ItemEntry<FireclayCrucibleItem> FIRECLAY_CRUCIBLE_ITEM = WoodenCog.REGISTRATE.item("fireclay_crucible", FireclayCrucibleItem::new)
                .properties(properties -> properties.stacksTo(1))
                .tab(() -> CreativeModeTab.TAB_TOOLS)
                .register();

        WoodenCog.REGISTRATE.item("unfired_fireclay_crucible", ModItem::new)
                .properties(properties -> properties.stacksTo(1))
                .tab(() -> CreativeModeTab.TAB_TOOLS)
                .register();

        machineCapacityStorage = new MachineCapacityStorage(this);

        if(FMLEnvironment.dist == Dist.CLIENT) {
            ModelLoaderRegistry.registerLoader(new ResourceLocation("woodencog", "fireclay_crucible"), FireclayCrucibleModel.LOADER);
            PONDER_HELPER
                    .forComponents(FIRECLAY_CRUCIBLE_ITEM)
                    .addStoryBoard("heating/heat", Heating::heating)
                    .addStoryBoard("heating/cool", Heating::cooling);
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WoodenCogCommonConfigs.SPEC, "woodencog-common.toml");
    }

    private void setup(final FMLCommonSetupEvent event)
    {

        BlockEntity target = null;

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("machineCapacityStorage Load Config");
        try{
            machineCapacityStorage.loadConfig();
        }catch (Error error){
            LOGGER.info("machineCapacityStorage setup crash");
            error.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();

        WoodenCogCommand cogCommand = new WoodenCogCommand(this);
        cogCommand.register(commandDispatcher);

    }
}


