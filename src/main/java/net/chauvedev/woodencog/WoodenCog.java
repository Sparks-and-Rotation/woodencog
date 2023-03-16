package net.chauvedev.woodencog;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import net.chauvedev.woodencog.interaction.CustomArmInteractionPointTypes;
import net.chauvedev.woodencog.item.fluids.can.FireclayCrucibleItem;
import net.chauvedev.woodencog.item.fluids.can.FireclayCrucibleModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

    public WoodenCog()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        CustomArmInteractionPointTypes.registerAll();
        MinecraftForge.EVENT_BUS.register(this);
        ModelLoaderRegistry.registerLoader(new ResourceLocation("woodencog", "fireclay_crucible"), FireclayCrucibleModel.LOADER);
        WoodenCog.REGISTRATE.item("fireclay_crucible", FireclayCrucibleItem::new)
                .properties(properties -> properties.stacksTo(1))
                .tab(() -> CreativeModeTab.TAB_TOOLS)
                .register();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }
}


