package dev.thebjoredcraft.safeBlockPlace.config.loader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
 
public class SafeBlockPlacementConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("safeblockplacement.json");
    private static SafeBlockPlacementConfig instance;

    public boolean enabled = true;
 
    public static SafeBlockPlacementConfig get() {
      if (instance == null) {
        instance = load();
      }
        return instance;
    }
 
    private static SafeBlockPlacementConfig load() {
        if (Files.exists(PATH)) {
            try {
                SafeBlockPlacementConfig cfg = GSON.fromJson(Files.readString(PATH), SafeBlockPlacementConfig.class);
              if (cfg != null) {
                return cfg;
              }
            } catch (Exception e) {
                System.err.println("[SafeBlockPlacement] Failed to load config: " + e);
            }
        }
        SafeBlockPlacementConfig cfg = new SafeBlockPlacementConfig();
        cfg.save();
        return cfg;
    }
 
    public void save() {
        try {
            Files.writeString(PATH, GSON.toJson(this));
        } catch (IOException e) {
            System.err.println("[SafeBlockPlacement] Failed to save config: " + e);
        }
    }
}