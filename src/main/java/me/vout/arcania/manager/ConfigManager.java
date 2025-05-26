package me.vout.arcania.manager;

import me.vout.arcania.Arcania;

public class ConfigManager {
    private boolean fixEnchantCheckEnabled;

    public void reload() {
        Arcania.getInstance().getLogger().info("Reloading configs");
        Arcania.getInstance().reloadConfig();
        fixEnchantCheckEnabled = Arcania.getInstance().getConfig().getBoolean("fix-enchant-check", false);
    }

    // Getters
    public boolean isFixEnchantCheckEnabled() { return fixEnchantCheckEnabled; }
}
