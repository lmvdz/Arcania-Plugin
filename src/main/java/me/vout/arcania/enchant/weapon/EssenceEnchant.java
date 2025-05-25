package me.vout.arcania.enchant.weapon;

import me.vout.arcania.enchant.ArcaniaEnchant;
import me.vout.arcania.enchant.EnchantRarityEnum;
import me.vout.arcania.enchant.tool.MagnetEnchant;
import me.vout.arcania.util.InventoryHelper;
import me.vout.arcania.util.ItemHelper;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EssenceEnchant extends ArcaniaEnchant {
    public static final EssenceEnchant INSTANCE = new EssenceEnchant();
    private EssenceEnchant() {
        super("Essence",
                "Increases xp dropped by mobs",
                EnchantRarityEnum.UNCOMMON,
                3,
                0.6,
                2,
                ItemHelper::isMeleeWeapon);
    }

    @Override
    public boolean canApplyWith(ArcaniaEnchant enchant) {
        return true;
    }

    public static void onProc(Player player,EntityDeathEvent event, Map<ArcaniaEnchant, Integer> enchants) {
        int essenceLevel = 0;
        if (enchants.containsKey(EssenceEnchant.INSTANCE))
            essenceLevel = enchants.get(EssenceEnchant.INSTANCE);

        boolean hasMagnet = enchants.containsKey(MagnetEnchant.INSTANCE);

        int xp = event.getDroppedExp();
        if (essenceLevel > 0) {
            xp = getBonusMultiplier(essenceLevel) * xp;
        }

        if (enchants.containsKey(MagnetEnchant.INSTANCE)) {
           MagnetEnchant.onProc(player, event, xp);
        }
        else
            event.setDroppedExp(xp);
    }

    private static int getBonusMultiplier(int level) {
        return switch (level) {
            case 1 -> 2; // 2x
            case 2 -> 3; // 3x
            case 3 -> 4; // 4x
            default -> 1;
        };
    }
}
