package me.vout.arcania.manager;

import me.vout.arcania.Arcania;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private boolean fixEnchantCheckEnabled;

    public void reload() {
        Arcania.getInstance().getLogger().info("Reloading configs");
        Arcania.getInstance().reloadConfig();
        FileConfiguration config = Arcania.getInstance().getConfig();
        fixEnchantCheckEnabled = config.getBoolean("utility.fix-enchant-check", false);
    }

    // Getters
    public boolean isFixEnchantCheckEnabled() { return fixEnchantCheckEnabled; }
}
