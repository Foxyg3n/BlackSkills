package me.foxyg3n.blackskills.listeners;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.sharyxxx.levelsystem.Events.LevelUpEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LevelUpListener implements Listener {

    public LevelUpListener() {
        Bukkit.getPluginManager().registerEvents(this, BlackSkills.getInstance());
    }

    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        Player player = event.getPlayer();
        PlayerSkills playerSkills = BlackSkills.getInstance().playerDataManager.getPlayerSkills(player);
        playerSkills.gui.update();
        BlackSkills.getMessenger().message(player, "Otrzymałeś punkt umiejętności. Masz " + playerSkills.getAvailablePoints() + " punktów do wydania.");
    }

}
