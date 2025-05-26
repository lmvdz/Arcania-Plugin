package me.vout.arcania.enchant.pickaxe;

import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.EnchantRarityEnum;
import me.vout.arcania.util.BlockXpTable;
import me.vout.arcania.util.ItemHelper;
import me.vout.arcania.util.ToolHelper;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EnrichmentEnchant extends ArcaniaEnchant {
    public static final EnrichmentEnchant INSTANCE = new EnrichmentEnchant();

    private EnrichmentEnchant() {
        super("Enrichment",
                "Increase xp dropped by ores",
                EnchantRarityEnum.RARE,
                3,
                0.7,
                3,
                ItemHelper::isPickaxe);
    }
    @Override
    public boolean canApplyWith(ArcaniaEnchant enchant) {
        return true;
    }

    public static void onProc(Player player, ItemStack tool, BlockBreakEvent event, Map<ArcaniaEnchant, Integer> enchants) {
        Block targetBlock = event.getBlock();

        int xp = BlockXpTable.getXp(event.getBlock().getType());
        if (xp == 0) return;
        ToolHelper.customBreakBlock(player, targetBlock, tool, enchants);
    }
}