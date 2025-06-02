package me.vout.paper.arcania.enchant.bow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
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
import net.kyori.adventure.text.Component;

public class GravityEnchant extends ArcaniaEnchant {
    public static final GravityEnchant INSTANCE = new GravityEnchant();

    private GravityEnchant() {
        super("Gravity",
                "gravity",
                "Pull in nearby entities wherever the arrow lands",
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
        Location location = event.getProjectile().getLocation();
        List<Entity> nearbyEntities = new ArrayList<>(location.getNearbyEntities(10, 10, 10));
        for (Entity entity : nearbyEntities) {
            // pull in the entities towwards the bowshootevent target location
            entity.teleport(location);
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
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.RARE;
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
        return EnchantmentTarget.ALL;
    }
}
