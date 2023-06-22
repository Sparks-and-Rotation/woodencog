package net.chauvedev.woodencog;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.chauvedev.woodencog.commands.WoodenCogCommand;
import net.chauvedev.woodencog.config.CustomBlockConfig;
import net.chauvedev.woodencog.config.WoodenCogCommonConfigs;
import net.chauvedev.woodencog.core.MachineCapacityStorage;
import net.chauvedev.woodencog.interaction.CustomArmInteractionPointTypes;
import net.chauvedev.woodencog.item.ModItem;
import net.chauvedev.woodencog.item.fluids.can.FireclayCrucibleItem;
import net.chauvedev.woodencog.item.fluids.can.FireclayCrucibleModel;
import net.chauvedev.woodencog.ponder.Heating;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;

@Mod(WoodenCog.MOD_ID)
public class WoodenCog
{
    public static final String MOD_ID = "woodencog";
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);
    public static final Logger LOGGER = LogUtils.getLogger();
    static final PonderRegistrationHelper PONDER_HELPER = new PonderRegistrationHelper(WoodenCog.MOD_ID);
    final MachineCapacityStorage machineCapacityStorage;

    public WoodenCog()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        CustomArmInteractionPointTypes.registerAll();
        MinecraftForge.EVENT_BUS.register(this);

        CustomBlockConfig.init(new File(FMLPaths.CONFIGDIR.get().toString(), "woodencog-custom-block.json"));


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
    }

    @SubscribeEvent
    public void onRegisterCommandEvent(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();

        WoodenCogCommand cogCommand = new WoodenCogCommand(this);
        cogCommand.register(commandDispatcher);

    }
}


