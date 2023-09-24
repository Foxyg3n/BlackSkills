package me.foxyg3n.blackskills.commands;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    public TestCommand() {
        BlackSkills.getInstance().getCommand("skilltest").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player) {
            PlayerSkills playerSkills = BlackSkills.getInstance().playerDataManager.getPlayerSkills(player);
            playerSkills.upgradeSkill(SkillType.EXP_BOOST);
            BlackSkills.getMessenger().message(player, String.valueOf(playerSkills.getSkillLevel(SkillType.EXP_BOOST)));
        }

        return true;
    }

}
