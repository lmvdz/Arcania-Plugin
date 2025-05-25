package me.vout.arcania.manager;

import me.vout.arcania.Arcania;
import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.hoe.TillerEnchant;
import me.vout.arcania.enchant.pickaxe.QuarryEnchant;
import me.vout.arcania.enchant.pickaxe.VeinminerEnchant;
import me.vout.arcania.enchant.tool.MagnetEnchant;
import me.vout.arcania.enchant.weapon.EssenceEnchant;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ArcaniaEnchantManager {
    private final List<ArcaniaEnchant> enchants = new ArrayList<>();
    public final Map<String, ArcaniaEnchant> enchantMap = new HashMap<>();

    public ArcaniaEnchantManager() {
        enchantInit();
        buildEnchantMap();
    }

    public List<ArcaniaEnchant> getEnchants() {
        return Collections.unmodifiableList(enchants);
    }

    private void enchantInit() {
        enchants.add(QuarryEnchant.INSTANCE);
        enchants.add(TillerEnchant.INSTANCE);
        enchants.add(VeinminerEnchant.INSTANCE);
        enchants.add(MagnetEnchant.INSTANCE);
        enchants.add(EssenceEnchant.INSTANCE);
    }

    private void buildEnchantMap() {
        for (ArcaniaEnchant enchant : enchants) {
            enchantMap.put(enchant.getName(), enchant);
        }
    }

    public Map<String, ArcaniaEnchant> getEnchantMap() {
        return enchantMap;
    }
}