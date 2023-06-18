package net.chauvedev.woodencog.config;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.List;

public class WoodenCogCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> HANDLE_TEMPERATURE;

    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WEAR_BLACKLIST;

    static {
        BUILDER.push("woodencog");

        BUILDER.push("temperature");
        HANDLE_TEMPERATURE = BUILDER
                .comment("Should create handle temperature ?")
                .define("handle_temperature", true);
        BUILDER.pop();

        BUILDER.push("wearing");
        WEAR_BLACKLIST = BUILDER
                .comment("This list contains block that should not damage over time")
                .defineList("blacklist", List.of(
                        "create:shaft",
                        "create:cogwheel",
                        "create:large_cogwheel",
                        "create:gearbox",
                        "create:vertical_gearbox",
                        "create:clutch",
                        "create:gearshift",
                        "create:encased_chain_drive",
                        "create:adjustable_chain_gearshift",
                        "create:speedometer",
                        "create:stressometer",
                        "create:wooden_bracket",
                        "create:metal_bracket",
                        "create:sequenced_gearshift",
                        "create:rotation_speed_controller",
                        "create:display_board",
                        "create:schematicannon"
                ), entry -> true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}

