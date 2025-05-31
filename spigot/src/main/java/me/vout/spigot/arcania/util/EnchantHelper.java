package me.vout.spigot.arcania.util;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import me.vout.spigot.arcania.Arcania;
import me.vout.spigot.arcania.enchant.ArcaniaEnchant;
import me.vout.spigot.arcania.enchant.EnchantExtraEnum;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EnchantHelper {

    public static ItemStack getEnchantBook(ArcaniaEnchant enchant, int level, boolean includeDescription) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);

        // Add NBT data
        NBT.modify(book, nbt -> {
            // Get or create the arcania:enchants compound
            var nbtCompound = nbt.getOrCreateCompound("arcania").getOrCreateCompound("enchants");

            // Store the enchant name and level in the 'enchants' compound
            nbtCompound.setString(enchant.getName(), String.valueOf(level));
        });

        // Update display name and lore
        ItemMeta meta = book.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.YELLOW + "Enchanted Book");

            List<String> lore = new ArrayList<>();
            lore.add(String.format("%s%s %s",
                    ItemHelper.colorizeHex(enchant.getRarity().getColor()),
                    enchant.getName(),
                    ItemHelper.intToRoman(level)));

            if (includeDescription) {
                lore.add(String.format("%s%s",
                        ItemHelper.colorizeHex(EnchantExtraEnum.TOOL_TIP.getColor()),
                        enchant.getDescription()));
            }
            meta.setLore(lore);
            meta.addEnchant(Enchantment.UNBREAKING, 1, true); // Glowing effect
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
            book.setItemMeta(meta);
        }
        return book;
    }

    public static Map<ArcaniaEnchant, Integer> getItemEnchants(ItemStack item) {
        Map<ArcaniaEnchant, Integer> result = new HashMap<>();
        if (item.getType().isAir()) return result;
        NBT.get(item, nbt -> {
            if (nbt != null) {
                ReadableNBT enchantsCompound = nbt.resolveCompound("arcania.enchants");
                if (enchantsCompound != null) {
                    // Iterate through the keys in the 'enchants' compound
                    for (String enchantName : enchantsCompound.getKeys()) {
                        ArcaniaEnchant enchant = Arcania.getEnchantManager().enchantMap.get(enchantName);
                        result.put(enchant, Integer.parseInt(enchantsCompound.getString(enchantName)));
                    }
                }
            }
        });
        return result;
    }

    public static ItemStack toMaxLevelBook(ArcaniaEnchant enchant) {
        return getEnchantBook(enchant, enchant.getMaxLevel(), true);
    }

    public static boolean needsUpdate(ItemStack item) {
        Map<ArcaniaEnchant, Integer> enchants = getItemEnchants(item);
        if (enchants.isEmpty()) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        // Build the expected, sorted lore
        List<Map.Entry<ArcaniaEnchant, Integer>> sortedEnchants = new ArrayList<>(enchants.entrySet());
        sortedEnchants.sort(Comparator
                .comparing((Map.Entry<ArcaniaEnchant, Integer> e) -> e.getKey().getRarity().getCost())
                .thenComparing(e -> e.getKey().getName(), String.CASE_INSENSITIVE_ORDER));

        List<String> expectedLore = new ArrayList<>();
        for (Map.Entry<ArcaniaEnchant, Integer> entry : sortedEnchants) {
            ArcaniaEnchant e = entry.getKey();
            int level = entry.getValue();
            expectedLore.add(ItemHelper.colorizeHex(String.format("%s%s %s",
                    e.getRarity().getColor(),
                    e.getName(),
                    ItemHelper.intToRoman(level)
            )));
        }

        List<String> currentLore = meta.getLore();
        if (!Objects.equals(currentLore, expectedLore)) {
            meta.setLore(expectedLore);
            item.setItemMeta(meta);
            return true;
        }
        return false;
    }
}