package net.chauvedev.woodencog;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import net.chauvedev.woodencog.config.WoodenCogCommonConfigs;
import net.chauvedev.woodencog.interaction.CustomArmInteractionPointTypes;
import net.chauvedev.woodencog.recipes.advancedProcessingRecipe.AllAdvancedRecipeTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(WoodenCog.MOD_ID)
public class WoodenCog
{
    public static final String MOD_ID = "woodencog";
    public static final Logger LOGGER = LogUtils.getLogger();
    static final PonderRegistrationHelper PONDER_HELPER = new PonderRegistrationHelper(WoodenCog.MOD_ID);

    public WoodenCog()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);

        CustomArmInteractionPointTypes.registerAll();

        AllAdvancedRecipeTypes.register(modEventBus);

        if(FMLEnvironment.dist == Dist.CLIENT) {
            /*PONDER_HELPER
                    .forComponents(FIRECLAY_CRUCIBLE_ITEM)
                    .addStoryBoard("heating/heat", Heating::heating)
                    .addStoryBoard("heating/cool", Heating::cooling);*/
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WoodenCogCommonConfigs.SPEC, "woodencog-common.toml");
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    }

    @SubscribeEvent
    public void onRegisterCommandEvent(RegisterCommandsEvent event) {

    }
}


