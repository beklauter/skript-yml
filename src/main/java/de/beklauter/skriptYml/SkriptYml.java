package de.beklauter.skriptYml;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class SkriptYml extends JavaPlugin {

    private static SkriptYml instance;
    private SkriptAddon addon;

    @Override
    public void onEnable() {
        instance = this;

        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("de.beklauter.skriptYml", "elements");
            getLogger().info("Successfully registered SkriptYml addon!");
        } catch (IOException e) {
            getLogger().severe("Failed to load SkriptYml addon: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static SkriptYml getInstance() {
        return instance;
    }

    public SkriptAddon getAddon() {
        return addon;
    }
}