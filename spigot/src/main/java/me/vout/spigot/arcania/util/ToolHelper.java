package me.vout.spigot.arcania.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import me.vout.spigot.arcania.Arcania;
import me.vout.spigot.arcania.enchant.ArcaniaEnchant;
import me.vout.spigot.arcania.enchant.pickaxe.EnrichmentEnchant;
import me.vout.spigot.arcania.enchant.pickaxe.QuarryEnchant;
import me.vout.spigot.arcania.enchant.pickaxe.VeinminerEnchant;
import me.vout.spigot.arcania.enchant.tool.MagnetEnchant;
import me.vout.spigot.arcania.enchant.tool.ProsperityEnchant;
import me.vout.spigot.arcania.enchant.tool.SmeltEnchant;
import me.vout.spigot.arcania.enchant.weapon.EssenceEnchant;



public class ToolHelper {

    


    /**
     * Custom break block logic
     * @param player The player breaking the block
     * @param event The block break event
     * @param tool The tool used to break the block
     * @param enchants The enchants on the tool used by the player to break the block
     */
    public static void customBreakBlock(
            Player player,
            BlockBreakEvent event,
            ItemStack tool,
            Map<ArcaniaEnchant, Integer> enchants
    ) {



        // get the block to break
        Block block = event.getBlock();

        // prepare xp to give
        final float[] xpToGive = {event.getExpToDrop()};

        // prepare list of blocks to attempt to break
        List<Block> additionalBlocksToBreak = new ArrayList<>();

        /**
         * 
         * Magnet
         * 
         */
        // then process magnet to collect the drops + xp
        boolean hasMagnet = enchants.containsKey(MagnetEnchant.INSTANCE);
        Location dropLocation = hasMagnet ? player.getLocation() : block.getLocation();

         /**
         * 
         * VeinMiner
         * 
         */
        // then process veinminer to collect which blocks to break based on the quarry
        boolean hasVeinminer = enchants.containsKey(VeinminerEnchant.INSTANCE);
        boolean hasVeinminerMetadata = block.hasMetadata("arcania:veinmined");

        /**
         * 
         * Quarry
         * 
         */
        // first process quarry to collect which blocks to break
        boolean hasQuarry = enchants.containsKey(QuarryEnchant.INSTANCE);
        boolean hasQuarryMetadata = block.hasMetadata("arcania:quarried");

        if (hasQuarry && !hasQuarryMetadata && !hasVeinminerMetadata) {
            QuarryEnchant.getBlocksToBreak(player, tool, event, additionalBlocksToBreak);
            additionalBlocksToBreak.forEach(blockToBreak -> {
                blockToBreak.setMetadata("arcania:quarried", new FixedMetadataValue(Arcania.getInstance(), true));
            });
        }

        if (hasVeinminer && !hasVeinminerMetadata) {
            List<Block> veinminerQueue = new ArrayList<>();
            veinminerQueue.add(block);
            veinminerQueue.addAll(additionalBlocksToBreak);
            for (Block blockToBreak : veinminerQueue) {
                if (VeinminerEnchant.isVeinMineBlock(blockToBreak.getType())) {
                    VeinminerEnchant.getBlocksToBreak(player, blockToBreak, enchants, additionalBlocksToBreak);

                    additionalBlocksToBreak.forEach(veinMineBlock -> {
                        veinMineBlock.setMetadata("arcania:veinmined", new FixedMetadataValue(Arcania.getInstance(), true));
                    });
                }
            }
        }

        int cumulativeDamage = 0;
        // Track tool durability and break blocks until tool breaks
        for (Block blockToBreak : additionalBlocksToBreak.stream().distinct().toList()) {
            boolean blockToBreakHasMetadata = false;
            if (blockToBreak == null) continue;

            blockToBreakHasMetadata = blockToBreak.hasMetadata("arcania:quarried") || blockToBreak.hasMetadata("arcania:veinmined");
            if (!blockToBreakHasMetadata) continue;

            // Simulate if tool would survive breaking this block
            int damage = simulateToolDamage(tool, 1);
            cumulativeDamage += damage;
            if (!wouldToolSurvive(tool, cumulativeDamage)) {
                // Tool would break, stop breaking blocks
                break;
            }
            player.breakBlock(blockToBreak);
        }

        boolean hasSmelting = enchants.containsKey(SmeltEnchant.INSTANCE);
        int prosperityLevel = enchants.getOrDefault(ProsperityEnchant.INSTANCE, 0);
        List<ItemStack> blockDrops = new ArrayList<>();

        // Calculate drops and XP
        block.getDrops(tool).forEach(itemStack -> {
            if (hasSmelting) {
                FurnaceRecipe furnaceRecipe = ItemHelper.getFurnaceRecipeForItemStack(itemStack);
                if (furnaceRecipe != null) {
                    ItemStack smeltedItemStack = furnaceRecipe.getResult().clone();
                    smeltedItemStack.setAmount(itemStack.getAmount());
                    blockDrops.add(smeltedItemStack);
                    float experienceToGive = furnaceRecipe.getExperience();
                    Arcania.getInstance().getLogger().log(Level.INFO, "experienceToGive: " + experienceToGive);
                    xpToGive[0] += experienceToGive;

                } else {
                    blockDrops.add(itemStack);
                }
            } else {
                blockDrops.add(itemStack);
            }
            if (prosperityLevel > 0 && ProsperityEnchant.shouldApplyEffect(prosperityLevel)) { // each block applies its own chance
                ItemStack item = blockDrops.get(blockDrops.size() -1);
                item.setAmount(item.getAmount() * 2);
            }
        });
        

        /**
         * 
         * Enrichment
         * 
         */
        // process enrichment to modify the xp before giving it
        boolean hasEnrichment = enchants.containsKey(EnrichmentEnchant.INSTANCE);
        
        // give the xp
        if (xpToGive[0] > 0) {
            if (hasEnrichment) {
                xpToGive[0] = EssenceEnchant.getScaledXP(xpToGive[0], enchants.get(EnrichmentEnchant.INSTANCE));
            }
        }

        // clear the drops from the original block since we're using the queue to break the blocks
        event.setDropItems(false);
        event.setExpToDrop(0);

        // Queue the block break
        BlockBreakQueue.queueBlockBreak(
            player, 
            new BlockBreakQueue.BlockBreakData(
                tool,
                block,
                blockDrops,
                xpToGive[0],
                dropLocation,
                hasMagnet
            )
        );
    }
    

    public static boolean damageTool(Player player, ItemStack tool, int amount) {
        int actualDamage = simulateToolDamage(tool, amount);
        
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
            return true;
        }
        return true;
    }

    /**
     * Simulates tool damage without actually applying it
     * @param tool The tool to simulate damage for
     * @param amount The amount of damage to simulate
     * @return The actual amount of damage that would be applied after unbreaking calculations
     */
    public static int simulateToolDamage(ItemStack tool, int amount) {
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

        return actualDamage;
    }

    /**
     * Simulates whether a tool would break after taking a certain amount of damage
     * @param tool The tool to check
     * @param amount The amount of damage to simulate
     * @return true if the tool would survive, false if it would break
     */
    public static boolean wouldToolSurvive(ItemStack tool, int amount) {
        if (!(tool.getItemMeta() instanceof Damageable damageable)) {
            return true;
        }
        
        int actualDamage = simulateToolDamage(tool, amount);
        int newDamage = damageable.getDamage() + actualDamage;
        return newDamage < tool.getType().getMaxDurability();
    }
}