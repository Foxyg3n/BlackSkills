package me.foxyg3n.blackskills.commands;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Test2Command implements CommandExecutor {

    public Test2Command() {
        BlackSkills.getInstance().getCommand("skilltest2").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player player) {
        }

        return true;
    }

}
