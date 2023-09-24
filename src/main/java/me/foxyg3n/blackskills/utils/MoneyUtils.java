package me.foxyg3n.blackskills.utils;

import me.foxyg3n.blackskills.BlackSkills;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class MoneyUtils {

    private static final EconomyResponse FAILURE_RES = new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Economy is not initialized");

    private static Economy getEconomy() {
        return BlackSkills.getInstance().economy;
    }

    private static boolean isEcoDisabled() {
        return getEconomy() == null || !getEconomy().isEnabled();
    }

    public static EconomyResponse addMoney(Player player, double money) {
        if(isEcoDisabled()) return FAILURE_RES;
        return getEconomy().depositPlayer(player, money);
    }

    public static EconomyResponse removeMoney(Player player, double money) {
        if(isEcoDisabled()) return FAILURE_RES;
        return getEconomy().withdrawPlayer(player, money);
    }

    public static boolean hasMoney(Player player, double money) {
        if(isEcoDisabled()) return false;
        return getEconomy().has(player, money);
    }

}
