package me.vout.paper.arcania.enchant;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;

public abstract class ArcaniaEnchant extends Enchantment {
    public static final String NAMESPACE = "arcania";
    protected final String key;
    protected final String name;
    protected final String description;
    protected final int maxLevel;
    protected final int startLevel;
    protected final int weight;
    protected final int minModifiedCost;
    protected final int maxModifiedCost;
    protected final int anvilCost;
    protected final int minEnchantmentTableCost;
    protected final int maxEnchantmentTableCost;


    public ArcaniaEnchant(String name, String key, String description, int maxLevel, int startLevel, int weight, int minEnchantmentTableCost, int maxEnchantmentTableCost, int minModifiedCost, int maxModifiedCost, int anvilCost) {
        this.name = name;   
        this.key = key;
        this.description = description;
        this.maxLevel = maxLevel;
        this.startLevel = startLevel;
        this.weight = weight;
        this.minEnchantmentTableCost = minEnchantmentTableCost;
        this.maxEnchantmentTableCost = maxEnchantmentTableCost;
        this.minModifiedCost = minModifiedCost;
        this.maxModifiedCost = maxModifiedCost;
        this.anvilCost = anvilCost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public @NotNull Component description() {
        return Component.text(description);
    }

    @Override
    public @NotNull Component displayName(int arg0) {
        return Component.text(name);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(NAMESPACE, key);
    }

    public int getMinEnchantmentTableCost() {
        return minEnchantmentTableCost;
    }

    public int getMaxEnchantmentTableCost() {
        return maxEnchantmentTableCost;
    }

    public int getEnchantmentTableCostPerLevel() {
        return (maxEnchantmentTableCost - minEnchantmentTableCost) / maxLevel;
    }

    @Override
    public int getMaxModifiedCost(int arg0) {
        return maxModifiedCost;
    }

    @Override
    public int getMinModifiedCost(int arg0) {
        return minModifiedCost;
    }

    @Override
    public int getStartLevel() {
        return startLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getWeight() {
        return weight;
    }
    @Override
    public boolean isCursed() {
        return false;
    }
    
    @Override
    public @NotNull String translationKey() {
        return "enchantment." + NAMESPACE + "." + key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return "enchantment." + NAMESPACE + "." + key;
    }
    
    @Override
    public int getAnvilCost() {
        return anvilCost;
    }

    // public void register(WritableRegistry<Enchantment, Builder> registry) {
    //     registry.register(EnchantmentKeys.create(getKey()), b -> 
    //      b.description(Component.text(getName()))
    //         .supportedItems(getSupportedItems())
    //         .primaryItems(getPrimaryItems())
    //         .exclusiveWith(getExclusiveWith())
    //         .anvilCost(getAnvilCost())
    //         .maxLevel(getMaxLevel())
    //         .weight(getWeight())
    //         .minimumCost(EnchantmentCost.of(getStartLevel(), getMinModifiedCost(getStartLevel())))
    //         .maximumCost(EnchantmentCost.of(getStartLevel(), getMaxModifiedCost(getStartLevel())))
    //         .activeSlots(getActiveSlotGroups())
    //     );
    // };

}
