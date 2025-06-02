package me.vout.paper.arcania.enchant.bow;

import java.util.Set;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
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

public class BlinkEnchant extends ArcaniaEnchant {
    public static final BlinkEnchant INSTANCE = new BlinkEnchant();

    private BlinkEnchant() {
        super("Blink",
                "blink",
                "Shoot an ender pearl for further range",
                1,
                1,
                10,
                15,
                45,
                10,
                15,
                1
        );
    }

    public static void onProc(Player player, EntityShootBowEvent event, ItemStack offhand) {
        event.setCancelled(true);
        EnderPearl pearl = player.launchProjectile(EnderPearl.class);
        pearl.setVelocity(event.getProjectile().getVelocity());

        offhand.setAmount(offhand.getAmount() - 1);
        player.getInventory().setItemInOffHand(offhand);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 0.4f, 1.0f);
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
        return null;
    }


    @Override
    public @Nullable RegistryKeySet<ItemType> getPrimaryItems() {
        return null;
    }

    @Override
    public @NotNull TagKey<ItemType> getPrimaryItemsTagKey() {
        return RegistryTags.RANGED;
    }


    @Override
    public @NotNull RegistryKeySet<ItemType> getSupportedItems() {
        return null;
    }

    @Override
    public @NotNull TagKey<ItemType> getSupportedItemsTagKey() {
        return RegistryTags.RANGED;
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
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
    }


    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }
}
