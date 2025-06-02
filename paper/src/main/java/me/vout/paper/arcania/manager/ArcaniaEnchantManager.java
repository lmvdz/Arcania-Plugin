package me.vout.paper.arcania.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.vout.paper.arcania.enchant.ArcaniaEnchant;
import me.vout.paper.arcania.enchant.bow.BlinkEnchant;
import me.vout.paper.arcania.enchant.bow.GravityEnchant;
import me.vout.paper.arcania.enchant.hoe.HarvesterEnchant;
import me.vout.paper.arcania.enchant.hoe.TillerEnchant;
import me.vout.paper.arcania.enchant.pickaxe.EnrichmentEnchant;
import me.vout.paper.arcania.enchant.pickaxe.QuarryEnchant;
import me.vout.paper.arcania.enchant.pickaxe.SmeltEnchant;
import me.vout.paper.arcania.enchant.pickaxe.VeinminerEnchant;
import me.vout.paper.arcania.enchant.tool.MagnetEnchant;
import me.vout.paper.arcania.enchant.tool.ProsperityEnchant;
import me.vout.paper.arcania.enchant.weapon.EssenceEnchant;
import me.vout.paper.arcania.enchant.weapon.FrostbiteEnchant;
import net.kyori.adventure.key.Key;

public class ArcaniaEnchantManager {
    private final List<ArcaniaEnchant> enchants = new ArrayList<>();
    public final Map<Key, ArcaniaEnchant> enchantMap = new HashMap<>();

    public ArcaniaEnchantManager() {
        enchantInit();
    }

    public List<ArcaniaEnchant> getEnchants() {
        return Collections.unmodifiableList(enchants);
    }

    private void enchantInit() {
        enchants.add(QuarryEnchant.INSTANCE);
        enchants.add(TillerEnchant.INSTANCE);
        enchants.add(ProsperityEnchant.INSTANCE);
        enchants.add(BlinkEnchant.INSTANCE);
        enchants.add(GravityEnchant.INSTANCE);
        enchants.add(EnrichmentEnchant.INSTANCE);
        enchants.add(HarvesterEnchant.INSTANCE);
        enchants.add(FrostbiteEnchant.INSTANCE);
        enchants.add(VeinminerEnchant.INSTANCE);
        enchants.add(MagnetEnchant.INSTANCE);
        enchants.add(EssenceEnchant.INSTANCE);
        enchants.add(SmeltEnchant.INSTANCE);
    }
}