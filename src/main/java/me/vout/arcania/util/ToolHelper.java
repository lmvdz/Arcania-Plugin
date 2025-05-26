package me.vout.arcania.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;


public class ToolHelper {
    public static boolean customBreakBlock(
            Player player,
            Block block,
            ItemStack tool,
            boolean hasMagnet
    ) {
        // 1. Get drops
        Collection<ItemStack> drops = block.getDrops(tool, player);

        // 2. Get XP
        int xp = 0;
        if (tool.getEnchantmentLevel(Enchantment.SILK_TOUCH) == 0)
            xp = BlockXpTable.getXp(block.getType());

        // 3. Remove block
        block.setType(Material.AIR, true);

        // 4. Handle drops
        if (hasMagnet) {
            InventoryHelper.giveOrDrop(player, drops.toArray(new ItemStack[0]));
        }
        else {
            for (ItemStack drop : drops) {
                block.getWorld().dropItemNaturally(block.getLocation().add(0.5, 0.5, 0.5), drop);
            }
        }

        // 5. Handle XP
        if (xp > 0) {
            Location xpLoc = hasMagnet ? player.getLocation() : block.getLocation();
            block.getWorld().spawn(xpLoc, ExperienceOrb.class).setExperience(xp);
        }

        // 6. Damage tool (returns false if tool is broken)
        return damageTool(player, tool, 1);
    }

    public static boolean damageTool(Player player, ItemStack tool, int amount) {
        int unbreaking = tool.getEnchantmentLevel(Enchantment.UNBREAKING);
        int actualDamage = 0;

        for (int i = 0; i < amount; i++) {
            if (unbreaking > 0) {
                // Chance to ignore damage: unbreaking / (unbreaking + 1)
                if (ThreadLocalRandom.current().nextInt(unbreaking + 1) == 0) {
                    actualDamage++; // Only apply damage if random == 0
                }
            } else {
                actualDamage++;
            }
        }

        // For 1.13+ (ItemMeta.setDamage)
        ItemMeta meta = tool.getItemMeta();
        if (meta instanceof Damageable) {
            Damageable damageable = (Damageable) meta;
            int newDamage = damageable.getDamage() + actualDamage;
            damageable.setDamage(newDamage);
            tool.setItemMeta(meta);

            if (newDamage >= tool.getType().getMaxDurability()) {
                player.getInventory().remove(tool);
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                return false;
            }
        }
        return true;
    }

}
