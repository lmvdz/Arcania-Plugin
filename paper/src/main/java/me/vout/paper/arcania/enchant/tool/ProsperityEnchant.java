package me.vout.paper.arcania.enchant.tool;

import java.util.Random;
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
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.tag.TagKey;
import me.vout.paper.arcania.enchant.ArcaniaEnchant;
import me.vout.paper.arcania.item.registry.RegistryTags;
import net.kyori.adventure.text.Component;

public class ProsperityEnchant extends ArcaniaEnchant {
    public static final ProsperityEnchant INSTANCE = new ProsperityEnchant();

    private ProsperityEnchant() {
        super("Prosperity",
                "prosperity",
                "Adds chance for double block drops",
                3,
                1,
                10,
                45,
                100,
                10,
                15,
                1
            );
    }

    public static boolean shouldApplyEffect(int level) {
        Random random = new Random();
        float chance = switch (level) {
            case 1 -> 0.1f; // 10% chance
            case 2 -> 0.2f; // 20% chance
            case 3 -> 0.35f; // 35% chance
            default -> 0f;
        };
        return random.nextFloat() < chance;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return "enchantment." + NAMESPACE + "." + key;
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
    public @NotNull Component description() {
        return Component.text(description);
    }

    @Override
    public @NotNull Component displayName(int arg0) {
        return Component.text(name);
    }

    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Set.of(EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public int getAnvilCost() {
        return anvilCost;
    }

    @Override
    public float getDamageIncrease(int arg0, @NotNull EntityCategory arg1) {
        return 0;
    }

    @Override
    public float getDamageIncrease(int arg0, @NotNull EntityType arg1) {
        return 0;
    }
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
        return RegistryTags.PROSPERITY_TOOLS_AND_BOOKS;
    }


    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return null;
    }

    @Override
    public @NotNull TagKey<ItemType> getSupportedItemsTagKey() {
        return RegistryTags.PROSPERITY_TOOLS_AND_BOOKS;
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
    public @NotNull String translationKey() {
        return "enchantment." + NAMESPACE + "." + key;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }
}
