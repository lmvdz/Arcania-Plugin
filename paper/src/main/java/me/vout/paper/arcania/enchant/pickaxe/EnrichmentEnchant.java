package me.vout.paper.arcania.enchant.pickaxe;

import java.util.List;
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
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import me.vout.paper.arcania.Arcania;
import me.vout.paper.arcania.enchant.ArcaniaEnchant;
import me.vout.paper.arcania.item.registry.RegistryTags;
import me.vout.paper.arcania.manager.ConfigManager;

/**
 * Enrichment Enchantment
 * 
 * A powerful enchantment that increases the experience dropped from mining blocks.
 * 
 * Technical Specifications:
 * - Rarity: RARE
 * - Max Level: 3
 * - Success Rate: 70%
 * - Cost: 15 levels
 * - Applies to: Pickaxes only
 * 
 * Experience Scaling:
 * The enchantment scales the base XP drop using the formula:
 * scaledXP = baseXP * (1 + (level * 0.5))
 * 
 * Scaling by Level:
 * - Level 1: 1.5x XP (50% increase)
 * - Level 2: 2.0x XP (100% increase)
 * - Level 3: 2.5x XP (150% increase)
 * 
 * Functionality:
 * - Applies to all sources of mining XP:
 *   - Ore blocks
 *   - Spawner breaks
 *   - Smelting XP (when combined with Smelt enchant)
 * - Rounds up to nearest whole XP point
 * - Applies after all other XP modifications
 * 
 * Integration:
 * - Compatible with all other mining enchantments
 * - Works with the block breaking queue system
 * - Stacks multiplicatively with other XP-modifying effects
 * - Particularly effective when combined with the Smelt enchantment
 */
public class EnrichmentEnchant extends ArcaniaEnchant {
    public static final EnrichmentEnchant INSTANCE = new EnrichmentEnchant();

    private EnrichmentEnchant() {
        super("Enrichment",
                "enrichment",
                "Increase xp dropped by ores. Effects are applied after smelting.",
                3,
                1,
                3,
                1,
                3,
                10,
                15,
                1
        );
    }

    public static float getScaledXP(float baseXP, int level) {
        ConfigManager configManager = Arcania.getConfigManager();
        double k = configManager.getEssenceK();
        List<Double> multipliers = configManager.getEssenceXpMultiplier();

        if (baseXP <= 5) {
            return (float) (baseXP * (1 + multipliers.get(level - 1)));
        } else {
            double bonus = baseXP * multipliers.get(level - 1) * (k / (baseXP + k));
            return (float) (baseXP + bonus);
        }
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
    
    @Override
    public @NotNull RegistryKeySet<Enchantment> getExclusiveWith() {
        return RegistrySet.keySet(RegistryKey.ENCHANTMENT, EnchantmentKeys.create(getKey()));
    }

    @Override
    public @Nullable RegistryKeySet<ItemType> getPrimaryItems() {
        return RegistrySet.keySet(RegistryTags.PICKAXES.registryKey());
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }

    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return RegistrySet.keySet(RegistryTags.PICKAXES.registryKey());
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