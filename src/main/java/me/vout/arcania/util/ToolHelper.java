package me.vout.arcania.util;

import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.pickaxe.EnrichmentEnchant;
import me.vout.arcania.enchant.tool.MagnetEnchant;
import me.vout.arcania.enchant.weapon.EssenceEnchant;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class ToolHelper {
    public static boolean customBreakBlock(
            Player player,
            Block block,
            ItemStack tool,
            Map<ArcaniaEnchant, Integer> enchants
    ) {
        boolean hasMagnet = enchants.containsKey(MagnetEnchant.INSTANCE);
        // 1. Get drops
        Collection<ItemStack> drops = block.getDrops(tool, player);

        // 2. Get XP
        int xp = 0;
        // applies enrichment or essence scalar
        int xpScalarLevel = enchants.containsKey(EssenceEnchant.INSTANCE) ?
                enchants.get(EssenceEnchant.INSTANCE) :
                enchants.getOrDefault(EnrichmentEnchant.INSTANCE, 0);

        if (tool.getEnchantmentLevel(Enchantment.SILK_TOUCH) == 0) {
            xp = BlockXpTable.getXp(block.getType());
            if (xpScalarLevel > 0)
                xp = EssenceEnchant.getScaledXP(xp, xpScalarLevel);
        }
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
                if (ThreadLocalRandom.current().nextInt(unbreaking + 1) == 0)
                    actualDamage++; // Only apply damage if random == 0
            } else
                actualDamage++;
        }

        // For 1.13+ (ItemMeta.setDamage)
        ItemMeta meta = tool.getItemMeta();
        if (meta instanceof Damageable damageable) {
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