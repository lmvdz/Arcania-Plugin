package me.vout.paper.arcania.enchant.tool;

import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
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
import me.vout.paper.arcania.enchant.ArcaniaEnchant;
import me.vout.paper.arcania.item.registry.RegistryTags;
import me.vout.paper.arcania.util.InventoryHelper;

public class MagnetEnchant extends ArcaniaEnchant {
    public static  final MagnetEnchant INSTANCE = new MagnetEnchant();
    private MagnetEnchant() {
        super("Magnet",
            "magnet",
            "Attempts to put item drops directly in inventory",
            1,
            1,
            5,
            1,
            3,
            10,
            15,
            1
        );
    }

    public static void onProc(Player player, EntityDeathEvent event, float xp) {
        event.setDroppedExp(0);

        InventoryHelper.giveOrDrop(player, event.getDrops().toArray(new ItemStack[0]));
        event.getDrops().clear();
        player.getWorld().spawn(player.getLocation(), ExperienceOrb.class).setExperience((int)xp);
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
        return RegistrySet.keySet(RegistryTags.SWORDS_AND_TOOLS.registryKey());
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }


    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return RegistrySet.keySet(RegistryTags.SWORDS_AND_TOOLS.registryKey());
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
