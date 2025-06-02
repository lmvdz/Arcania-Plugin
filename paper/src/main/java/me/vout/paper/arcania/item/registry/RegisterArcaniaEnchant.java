package me.vout.paper.arcania.item.registry;


import org.bukkit.enchantments.Enchantment;

import io.papermc.paper.registry.data.EnchantmentRegistryEntry.Builder;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry.EnchantmentCost;
import io.papermc.paper.registry.event.WritableRegistry;
import io.papermc.paper.registry.keys.EnchantmentKeys;
import me.vout.paper.arcania.enchant.ArcaniaEnchant;
import net.kyori.adventure.text.Component;

public class RegisterArcaniaEnchant {

    public static void register(WritableRegistry<Enchantment, Builder> registry, ArcaniaEnchant enchantment) {
        registry.register(EnchantmentKeys.create(enchantment.getKey()), builder -> {
             builder.description(Component.text(enchantment.getName()))
               .supportedItems(enchantment.getSupportedItems())
               .primaryItems(enchantment.getPrimaryItems())
               .anvilCost(enchantment.getAnvilCost())
               .maxLevel(enchantment.getMaxLevel())
               .weight(enchantment.getWeight())
               .minimumCost(EnchantmentCost.of(enchantment.getMinEnchantmentTableCost(), enchantment.getEnchantmentTableCostPerLevel()))
               .maximumCost(EnchantmentCost.of(enchantment.getMaxEnchantmentTableCost(), enchantment.getEnchantmentTableCostPerLevel()))
               .activeSlots(enchantment.getActiveSlotGroups());
   
               if (enchantment.getExclusiveWith() != null) {
                   builder.exclusiveWith(enchantment.getExclusiveWith());
               }
            }
        );
    }
}
