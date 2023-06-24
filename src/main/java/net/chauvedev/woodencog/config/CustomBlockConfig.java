package net.chauvedev.woodencog.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomBlockConfig {
    public static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
    public static Map<String, BlockInformation> registeredBlocks = new HashMap<String, BlockInformation>();
    public static void init(File jsonConfig) {
        try {
            // Create the config if it doesn't already exist.
            if (!jsonConfig.exists() && jsonConfig.createNewFile()) {
                // Get a default map of blocks. You could just use a blank map, however.
                Map<String, BlockInformation> defaultMap = getDefaults();
                // Convert the map to JSON format. There is a built in (de)serializer for it already.
                String json = gson.toJson(defaultMap, new TypeToken<Map<String, BlockInformation>>(){}.getType());
                FileWriter writer = new FileWriter(jsonConfig);
                // Write to the file you passed
                writer.write(json);
                // Always close when done.
                writer.close();
            }

            // If the file exists (or we just made one exist), convert it from JSON format to a populated Map object
            registeredBlocks = gson.fromJson(new FileReader(jsonConfig), new TypeToken<Map<String, BlockInformation>>(){}.getType());
        } catch (IOException e) {
            // Print an error if something fails. Please use a real logger, not System.out.
            System.out.println("Error creating default configuration.");
        }
    }

    private static Map<String, BlockInformation> getDefaults() {
        Map<String, BlockInformation> ret = new HashMap<>();
        ret.put("examplemod:exampleblock", new BlockInformation(100, 5));
        return ret;
    }

    public static class BlockInformation {
        public int durability;
        public int chance;

        public BlockInformation(int durability, int chance) {
            this.durability = durability;
            this.chance = chance;
        }
    }
}