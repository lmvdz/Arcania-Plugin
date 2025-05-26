package me.vout.arcania.enchant.pickaxe;

import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.EnchantRarityEnum;
import me.vout.arcania.util.ItemHelper;
import me.vout.arcania.util.ToolHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class VeinminerEnchant extends ArcaniaEnchant {
    public static final VeinminerEnchant INSTANCE = new VeinminerEnchant();
    private VeinminerEnchant() {
        super("Vein Miner",
                "Mines connecting veins of initial block",
                EnchantRarityEnum.LEGENDARY,
                3,
                0.2,
                4,
                ItemHelper::isPickaxe);
    }

    @Override
    public boolean canApplyWith(ArcaniaEnchant enchant) {
        return true;
    }

    public static void onProc(Player player, BlockBreakEvent event, Map<ArcaniaEnchant, Integer> enchants) {
        Block block = event.getBlock();

        if (isVeinMineBlock(block.getType())) {
            event.setCancelled(true);
            veinMine(player, block, enchants);
        }
    }

    public static boolean isVeinMineBlock(Material mat) {
        return mat.name().endsWith("_ORE") ||
                mat == Material.GRANITE ||
                mat == Material.DIORITE ||
                mat == Material.ANDESITE ||
                mat == Material.TUFF;
    }

    public static void veinMine(Player player, Block startBlock, Map<ArcaniaEnchant, Integer> enchants) {
        int maxBlocks = 10 + enchants.get(VeinminerEnchant.INSTANCE) * 5;
        Material targetType = startBlock.getType();
        Set<Block> checked = new HashSet<>();
        Queue<Block> toCheck = new LinkedList<>();
        List<Block> toBreak = new ArrayList<>();

        toCheck.add(startBlock);

        while (!toCheck.isEmpty() && toBreak.size() < maxBlocks) {
            Block block = toCheck.poll();
            if (checked.contains(block)) continue;
            checked.add(block);

            if (block.getType() != targetType) continue;

            toBreak.add(block);

            // Check all 26 neighbors in a 3x3x3 cube
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue; // Skip self
                        Block neighbor = block.getRelative(dx, dy, dz);
                        if (!checked.contains(neighbor) && neighbor.getType() == targetType) {
                            toCheck.add(neighbor);
                        }
                    }
                }
            }
        }

        // Break all found blocks as the player
        ItemStack tool = player.getInventory().getItemInMainHand();
        for (Block block : toBreak) {
            if (!ToolHelper.customBreakBlock(player, block, tool, enchants))
                break;
        }
    }
}