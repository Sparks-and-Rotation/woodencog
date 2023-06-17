package net.chauvedev.woodencog.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WoodenCogCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> HANDLE_TEMPERATURE;

    static {
        BUILDER.push("Config for woodencog");

        HANDLE_TEMPERATURE = BUILDER
                .comment("Should create handle temperature ?")
                .define("Handle temperature", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
