package me.vout.paper.arcania.enchant.pickaxe;

import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.papermc.paper.enchantments.EnchantmentRarity;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.tag.TagKey;
import me.vout.paper.arcania.enchant.ArcaniaEnchant;

public class SmeltEnchant extends ArcaniaEnchant {
    public static final SmeltEnchant INSTANCE = new SmeltEnchant();
    private SmeltEnchant() {
        super("Smelt",
            "smelt",
            "Instantly smelt the block into it's smelting result",
            1,
            1,
            5,
            30,
            75,
            10,
            15,
            1
        );
    }
    

    @Override
    public boolean canEnchantItem(@NotNull ItemStack arg0) {
        return true;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment arg0) {
        return false;
    }


    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Set.of(EquipmentSlotGroup.MAINHAND);
    }


    @Override
    public float getDamageIncrease(int arg0, @NotNull EntityCategory arg1) {
        return 0;
    }

    @Override
    public float getDamageIncrease(int arg0, @NotNull EntityType arg1) {
        return 0;
    }
    
    /**
     * Silk Touch is exclusive with Smelt
     */
    @Override
    public @NotNull RegistryKeySet<Enchantment> getExclusiveWith() {
        return null;
    }

    @Override
    public @Nullable RegistryKeySet<ItemType> getPrimaryItems() {
        return null;
    }

    @Override
    public @NotNull TagKey<ItemType> getPrimaryItemsTagKey() {
        return ItemTypeTagKeys.PICKAXES;
    }


    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return null;
    }

    @Override
    public @NotNull TagKey<ItemType> getSupportedItemsTagKey() {
        return ItemTypeTagKeys.PICKAXES;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }


    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }
    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }


    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }
}
