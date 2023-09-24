package me.foxyg3n.blackskills.listeners;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SkillToggleListener implements Listener {

    public SkillToggleListener() {
        Bukkit.getPluginManager().registerEvents(this, BlackSkills.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerSkills playerSkills = BlackSkills.getInstance().playerDataManager.getPlayerSkills(player);
        playerSkills.enableAllSkills();
        playerSkills.gui.update();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerSkills playerSkills = BlackSkills.getInstance().playerDataManager.getPlayerSkills(player);
        playerSkills.disableAllSkills();
        playerSkills.gui.update();
    }

}
