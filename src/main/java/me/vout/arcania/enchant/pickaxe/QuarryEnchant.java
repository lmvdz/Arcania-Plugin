package me.vout.arcania.enchant.pickaxe;

import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.EnchantRarityEnum;
import me.vout.arcania.util.ItemHelper;
import me.vout.arcania.util.ToolHelper;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class QuarryEnchant extends ArcaniaEnchant {

    public static  final QuarryEnchant INSTANCE = new QuarryEnchant();
    private QuarryEnchant() {
        super("Quarry",
                "Mines a 3x3 around broken block",
                EnchantRarityEnum.RARE,
                1,
                0.5,
                2,
                ItemHelper::isDigger);
    }

    @Override
    public boolean canApplyWith(ArcaniaEnchant enchant) {
        return true;
    }
    public static void onProc(Player player, ItemStack item, BlockBreakEvent event, Map<ArcaniaEnchant, Integer> enchants) {
        Block block = event.getBlock();
        if (!block.isPreferredTool(item)) return;

        int veinMinerLevel = enchants.getOrDefault(VeinminerEnchant.INSTANCE, 0);
        BlockFace face = getBlockFace(player);
        if (face == null)
            face = player.getFacing();

        int[][] offsets = getBlocksOffset(face);
        event.setCancelled(true);
        for (int[] offset : offsets) {
            Block relative = block.getRelative(offset[0], offset[1], offset[2]);
            if (relative.isPreferredTool(item)) {
                if (VeinminerEnchant.isVeinMineBlock(relative.getType()) && veinMinerLevel > 0) {
                    VeinminerEnchant.veinMine(player, relative, enchants);
                }
                else {
                    if (!ToolHelper.customBreakBlock(player, relative, item, enchants)) break;
                }
            }
        }
    }

    private static BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 5);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }


    private static int[][] getBlocksOffset(BlockFace face) {
        if (face == BlockFace.UP || face == BlockFace.DOWN) {
            // Horizontal plane (X, Z)
            return new int[][] {
                    {-1, 0, -1}, {0, 0, -1}, {1, 0, -1},
                    {-1, 0,  0}, {0, 0,  0}, {1, 0,  0},
                    {-1, 0,  1}, {0, 0,  1}, {1, 0,  1}
            };
        } else if (face == BlockFace.NORTH || face == BlockFace.SOUTH) {
            // Vertical plane (X, Y)
            return new int[][] {
                    {-1, -1, 0}, {0, -1, 0}, {1, -1, 0},
                    {-1,  0, 0}, {0,  0, 0}, {1,  0, 0},
                    {-1,  1, 0}, {0,  1, 0}, {1,  1, 0}
            };
        } else {
            // EAST or WEST: Vertical plane (Y, Z)
            return new int[][] {
                    {0, -1, -1}, {0, -1, 0}, {0, -1, 1},
                    {0,  0, -1}, {0,  0, 0}, {0,  0, 1},
                    {0,  1, -1}, {0,  1, 0}, {0,  1, 1}
            };
        }
    }
}
