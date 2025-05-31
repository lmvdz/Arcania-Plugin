package me.vout.spigot.arcania.enchant.bow;

import me.vout.spigot.arcania.enchant.ArcaniaEnchant;
import me.vout.spigot.arcania.enchant.EnchantRarityEnum;
import me.vout.spigot.arcania.util.ItemHelper;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

public class BlinkEnchantment extends ArcaniaEnchant {

    public static  final BlinkEnchantment INSTANCE = new BlinkEnchantment();

    private BlinkEnchantment() {
        super("Blink",
                "Shoot an ender pearl for further range",
                EnchantRarityEnum.LEGENDARY,
                1,
                0.4,
                3,
                ItemHelper::isRangedWeapon);
    }
    @Override
    public boolean canApplyWith(ArcaniaEnchant enchant) {
        return true;
    }

    public static void onProc(Player player, EntityShootBowEvent event, ItemStack offhand) {
        event.setCancelled(true);
        EnderPearl pearl = player.launchProjectile(EnderPearl.class);
        pearl.setVelocity(event.getProjectile().getVelocity());

        offhand.setAmount(offhand.getAmount() - 1);
        player.getInventory().setItemInOffHand(offhand);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1.0f, 1.0f);
    }
}
