package de.beklauter.skriptYml.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YamlManager {

    private static final Map<String, YamlConfiguration> loadedFiles = new HashMap<>();

    public static YamlConfiguration getOrCreateFile(String path) {
        if (loadedFiles.containsKey(path)) {
            return loadedFiles.get(path);
        }

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        loadedFiles.put(path, config);
        return config;
    }

    public static void saveFile(String path) {
        YamlConfiguration config = loadedFiles.get(path);
        if (config == null) return;

        try {
            config.save(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getValue(String path, String key) {
        YamlConfiguration config = getOrCreateFile(path);
        return config == null ? null : config.get(key);
    }

    public static void setValue(String path, String key, Object value) {
        YamlConfiguration config = getOrCreateFile(path);
        if (config == null) return;

        config.set(key, value);
        saveFile(path);
    }

    public static void removeValue(String path, String key) {
        YamlConfiguration config = getOrCreateFile(path);
        if (config == null) return;

        config.set(key, null);
        saveFile(path);
    }

    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static Set<String> getKeys(String path, String section) {
        YamlConfiguration config = getOrCreateFile(path);
        if (config == null) return null;

        if (section == null || section.isEmpty()) {
            return config.getKeys(false);
        }

        if (!config.isConfigurationSection(section)) {
            return null;
        }

        return config.getConfigurationSection(section).getKeys(false);
    }
}