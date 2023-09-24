package me.foxyg3n.blackskills.skilldata;

import com.google.gson.reflect.TypeToken;
import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.foxyg3n.utils.JsonDataSaver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager extends JsonDataSaver {

    private HashMap<UUID, PlayerSkills> playerSkillData;

    public PlayerDataManager(BlackSkills plugin) {
        super(plugin, "playerSkillData");
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                createPlayerSkillData(event.getPlayer());
            }
        }, plugin);
    }

    public PlayerSkills getPlayerSkills(Player player) {
        return playerSkillData.get(player.getUniqueId());
    }

    public void createPlayerSkillData(Player player) {
        if(!playerSkillData.containsKey(player.getUniqueId()))
            playerSkillData.put(player.getUniqueId(), new PlayerSkills(player.getUniqueId()));
    }

    @Override
    public void save() {
        toFile(playerSkillData);
    }

    @Override
    public void load() {
        playerSkillData = fromFile(new TypeToken<HashMap<UUID, PlayerSkills>>() {}).orElse(new HashMap<>());
    }
}
