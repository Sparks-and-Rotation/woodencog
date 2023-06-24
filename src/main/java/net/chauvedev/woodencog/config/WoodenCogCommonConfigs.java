package net.chauvedev.woodencog.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class WoodenCogCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> HANDLE_TEMPERATURE;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WEAR_BLACKLIST;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFAULT_DURABILITY;
    public static ForgeConfigSpec.ConfigValue<Integer> DEFAULT_DAMAGE_CHANCE;

    static {
        BUILDER.push("woodencog");

        BUILDER.push("temperature");
        HANDLE_TEMPERATURE = BUILDER
                .comment("Should create handle temperature ?")
                .define("handle_temperature", true);
        BUILDER.pop();

        BUILDER.push("wearing");
        DEFAULT_DURABILITY = BUILDER
                .comment("default durability of each machine bloc (144000 allow a machine to run at 256rpm for ~1H at 10% chance")
                .define("durability", 144000);
        DEFAULT_DAMAGE_CHANCE = BUILDER
                .comment("chance of machine getting damage (from 0 to 100 number over 100 are the same as 100)")
                .define("chance", 10);
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
                        "create:schematicannon",
                        "create:belt",
                        "create:creative_motor",
                        "create:mechanical_pump"
                ), entry -> true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}

