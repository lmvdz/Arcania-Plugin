package me.vout.spigot.arcania.enchant.tool;

import me.vout.spigot.arcania.enchant.ArcaniaEnchant;
import me.vout.spigot.arcania.enchant.EnchantRarityEnum;
import me.vout.spigot.arcania.util.ItemHelper;

import java.util.Random;

public class ProsperityEnchant extends ArcaniaEnchant {
    public static final ProsperityEnchant INSTANCE = new ProsperityEnchant();

    private ProsperityEnchant() {
        super("Prosperity",
                "Adds chance for double block drops",
                EnchantRarityEnum.RARE,
                3,
                0.4,
                2,
                ItemHelper::isBlockBreakTool);
    }
    @Override
    public boolean canApplyWith(ArcaniaEnchant enchant) {
        return true;
    }

    public static boolean shouldApplyEffect(int level) {
        Random random = new Random();
        float chance = switch (level) {
            case 1 -> 0.1f; // 10% chance
            case 2 -> 0.2f; // 20% chance
            case 3 -> 0.35f; // 35% chance
            default -> 0f;
        };
        return random.nextFloat() < chance;
    }
}
