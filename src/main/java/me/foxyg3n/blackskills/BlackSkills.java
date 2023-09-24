package me.foxyg3n.blackskills;

import me.foxyg3n.blackskills.adapters.PlayerSkillsAdapter;
import me.foxyg3n.blackskills.commands.SkillsCommand;
import me.foxyg3n.blackskills.commands.TestCommand;
import me.foxyg3n.blackskills.commands.Test2Command;
import me.foxyg3n.blackskills.listeners.LevelUpListener;
import me.foxyg3n.blackskills.listeners.SkillToggleListener;
import me.foxyg3n.blackskills.skilldata.PlayerDataManager;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.foxyg3n.blackskills.skilldata.SkillsManager;
import me.foxyg3n.utils.JsonDataSaver;
import me.foxyg3n.utils.Messenger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlackSkills extends JavaPlugin {

    private static BlackSkills instance;
    private static Messenger messenger;

    public static BlackSkills getInstance() {
        return instance;
    }
    public static Messenger getMessenger() {
        return messenger;
    }

    public SkillsManager skillsManager;
    public PlayerDataManager playerDataManager;
    public Economy economy;

    @Override
    public void onEnable() {
        instance = this;
        messenger = new Messenger("Black", "Skills");

        JsonDataSaver.updateGson(gson -> gson.registerTypeAdapter(PlayerSkills.class, new PlayerSkillsAdapter()));

        if(Bukkit.getPluginManager().getPlugin("Vault") != null) {
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if(rsp != null) economy = rsp.getProvider();
            else getLogger().warning(String.format("[%s] - Vault found but couldn't initialize economy!", getName()));
        } else {
            getLogger().warning(String.format("[%s] - No Vault dependency found!", getName()));
        }

        skillsManager = new SkillsManager();
        playerDataManager = new PlayerDataManager(this);

        // Commands
        new SkillsCommand();
        new TestCommand();
        new Test2Command();

        //Events
        new LevelUpListener();
        new SkillToggleListener();
    }

    @Override
    public void onDisable() {
        if(playerDataManager != null) playerDataManager.save();
    }

}
