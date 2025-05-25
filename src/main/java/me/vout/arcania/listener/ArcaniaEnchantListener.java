package me.vout.arcania.listener;

import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.hoe.TillerEnchant;
import me.vout.arcania.enchant.pickaxe.QuarryEnchant;
import me.vout.arcania.enchant.pickaxe.VeinminerEnchant;
import me.vout.arcania.enchant.tool.MagnetEnchant;
import me.vout.arcania.enchant.weapon.EssenceEnchant;
import me.vout.arcania.manager.ArcaniaEnchantManager;
import me.vout.arcania.util.EnchantHelper;
import me.vout.arcania.util.ItemHelper;
import me.vout.arcania.util.ToolHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ArcaniaEnchantListener implements Listener {
    private final ArcaniaEnchantManager enchantManager;

    public ArcaniaEnchantListener(ArcaniaEnchantManager manager) {
        this.enchantManager = manager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir() || item.getType().equals(Material.ENCHANTED_BOOK)) return;

        Map<ArcaniaEnchant, Integer> activeEnchants = EnchantHelper.getItemEnchants(item);
        if (activeEnchants.isEmpty()) return;

        boolean hasMagnet = activeEnchants.containsKey(MagnetEnchant.INSTANCE);

        if (activeEnchants.containsKey(QuarryEnchant.INSTANCE) &&
                event.getBlock().isPreferredTool(item)) {
            boolean hasVeinMiner = activeEnchants.containsKey(VeinminerEnchant.INSTANCE);
            QuarryEnchant.onProc(player, item, event, hasVeinMiner ? activeEnchants.get(VeinminerEnchant.INSTANCE) : 0, hasMagnet);
        }
        if (!event.isCancelled() &&
                activeEnchants.containsKey(VeinminerEnchant.INSTANCE)) {
            VeinminerEnchant.onProc(player, item, event, activeEnchants.get(VeinminerEnchant.INSTANCE), hasMagnet);
        }

        if (!event.isCancelled() &&
                activeEnchants.containsKey(MagnetEnchant.INSTANCE)) {
            ToolHelper.customBreakBlock(player, event.getBlock(), item, true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block clicked = event.getClickedBlock();

        // Check if the tool is a hoe
        if (!ItemHelper.isHoe(tool.getType())) return;
        Map<ArcaniaEnchant, Integer> activeEnchants = EnchantHelper.getItemEnchants(tool);

        if (activeEnchants.containsKey(TillerEnchant.INSTANCE)) {
            // Check if the clicked block can be tilled
            if (clicked == null || !TillerEnchant.isTillable(clicked.getType())) return;
            TillerEnchant.onProc(player, tool, clicked, event);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player != null) {
            ItemStack item = player.getInventory().getItemInMainHand();
            Map<ArcaniaEnchant, Integer> enchants = EnchantHelper.getItemEnchants(item);

            if (enchants.isEmpty()) return;
            boolean handled = false;
            if (enchants.containsKey(EssenceEnchant.INSTANCE)) {
                EssenceEnchant.onProc(player, event, enchants);
                handled = true;
            }
            if (!handled && enchants.containsKey(MagnetEnchant.INSTANCE)) {
                MagnetEnchant.onProc(player, event, event.getDroppedExp());
            }

        }
    }

    /*@EventHandler
    public void onentityDamgeByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        Entity damager = event.getDamager();
        Entity victim = event.getEntity();
        if (damager instanceof Player player && victim instanceof LivingEntity) {
            ItemStack weapon = player.getInventory().getItemInMainHand();

            Map<ArcaniaEnchant, Integer> enchants = EnchantHelper.getItemEnchants(weapon);
            if (enchants.isEmpty()) return;
        }
    } */
}