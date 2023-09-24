package me.foxyg3n.blackskills.utils;

import me.sharyxxx.levelsystem.JSON;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class LevelUtils {

    public static int getLevel(@Nullable Player player) {
        if(player == null) return 0;
        double[] playerData = JSON.playerData.get(player);
        return (int) playerData[0];
    }

    public static double getExperience(@Nullable Player player) {
        if(player == null) return 0;
        double[] playerData = JSON.playerData.get(player);
        return (int) playerData[1];
    }

    public static void setLevel(@Nullable Player player, int level) {
        if(player == null) return;
        JSON.playerData.put(player, new double[] { level, getExperience(player) });
    }

    public static void setExperience(@Nullable Player player, double experience) {
        if(player == null) return;
        JSON.playerData.put(player, new double[] { getLevel(player), experience });
    }

}
