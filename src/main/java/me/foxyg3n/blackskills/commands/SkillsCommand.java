package me.foxyg3n.blackskills.commands;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerDataManager;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.foxyg3n.blackskills.skilldata.skills.Skill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.foxyg3n.utils.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SkillsCommand implements CommandExecutor, TabCompleter {

    private final Messenger messenger = BlackSkills.getMessenger();
    private final PlayerDataManager playerDataManager = BlackSkills.getInstance().playerDataManager;

    public SkillsCommand() {
        BlackSkills.getInstance().getCommand("skills").setExecutor(this);
        BlackSkills.getInstance().getCommand("skills").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player) {
            if(args.length == 0) {
                playerDataManager.getPlayerSkills(player).gui.openGUI();
            } else if(args.length == 1) {
                switch(args[0]) {
                    case "set", "add", "remove" -> messenger.warning(player, "Wprowadź gracza jakiemu chcesz ustawić poziom umiejętności");
                }
            } else if(args.length == 2) {
                switch(args[0]) {
                    case "set", "add", "remove" -> messenger.warning(player, "Wprowadź typ umiejętności, której chcesz zmienić poziom");
                    case "get" -> {
                        Player skillPlayer = Bukkit.getPlayer(args[1]);
                        if(skillPlayer == null) {
                            messenger.warning(player, "Podany gracz nie istnieje");
                            return true;
                        }
                        PlayerSkills playerSkills = playerDataManager.getPlayerSkills(skillPlayer);
                        if(playerSkills.getSkillData().isEmpty()) {
                            messenger.message(player, skillPlayer.getName() + " nie posiada żadnych umiejętności");
                            return true;
                        }
                        messenger.message(player, ChatColor.GREEN + "Umiejętności gracza " + ChatColor.WHITE + skillPlayer.getName() + ChatColor.GREEN + ":");
                        playerSkills.getSkillData().forEach((skillType, skillLevel) -> {
                            messenger.message(player, ChatColor.GOLD + "» " + ChatColor.WHITE + skillType + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + skillLevel);
                        });
                    }
                    case "reset" -> {
                        Player skillPlayer = Bukkit.getPlayer(args[1]);
                        if(skillPlayer == null) {
                            messenger.warning(player, "Podany gracz nie istnieje");
                            return true;
                        }
                        PlayerSkills playerSkills = playerDataManager.getPlayerSkills(skillPlayer);
                        playerSkills.resetSkills();
                        messenger.message(player, "Wyczyściłeś wszystkie umiejętności gracza " + skillPlayer.getName());
                    }
                }
            } else if(args.length == 3) {
                switch(args[0]) {
                    case "set" -> messenger.warning(player, "Wprowadź poziom umiejętności jaki chcesz ustawić");
                    case "add" -> messenger.warning(player, "Wprowadź poziom umiejętności jaki chcesz dodać");
                    case "remove" -> messenger.warning(player, "Wprowadź poziom umiejętności jaki chcesz zabrać");
                    case "get" -> {
                        Player skillPlayer = Bukkit.getPlayer(args[1]);
                        if(skillPlayer == null) {
                            messenger.warning(player, "Podany gracz nie istnieje");
                            return true;
                        }
                        SkillType skillType = SkillType.fromString(args[2]);
                        if(skillType == null) {
                            messenger.warning(player, "Podany typ umiejętności nie istnieje");
                            return true;
                        }
                        PlayerSkills playerSkills = playerDataManager.getPlayerSkills(skillPlayer);
                        int skillLevel = playerSkills.getSkillLevel(skillType);
                        messenger.confirm(player, "Poziom umiejętności " + skillPlayer.getName() + " " + ChatColor.WHITE + skillType.name() + ChatColor.GREEN + " to " + ChatColor.WHITE + skillLevel);
                    }
                }
            } else if(args.length == 4) {
                Player skillPlayer = Bukkit.getPlayer(args[1]);
                if(skillPlayer == null) {
                    messenger.warning(player, "Podany gracz nie istnieje");
                    return true;
                }
                SkillType skillType = SkillType.fromString(args[2]);
                if(skillType == null) {
                    messenger.warning(player, "Podany typ umiejętności nie istnieje");
                    return true;
                }
                int skillLevel;
                try {
                    skillLevel = Integer.parseInt(args[3]);
                } catch(NumberFormatException e) {
                    messenger.warning(player, "Podany poziom musi być liczą");
                    return true;
                }
                Skill skill = BlackSkills.getInstance().skillsManager.getSkill(skillType);
                if(skillLevel < 0) {
                    messenger.warning(player, "Nie możesz ustawić poziomu umiejętności poniżej 0");
                    return true;
                } else if(skillLevel > skill.getMaxLevel()) {
                    messenger.warning(player, "Nie możesz ustawić poziomu umiejętności powyżej maksymalnego poziomu umiejętności");
                    return true;
                }
                PlayerSkills playerSkills = playerDataManager.getPlayerSkills(skillPlayer);
                switch(args[0]) {
                    case "set" -> playerSkills.setSkillLevel(skillType, skillLevel);
                    case "add" -> playerSkills.setSkillLevel(skillType, playerSkills.getSkillLevel(skillType) + skillLevel);
                    case "remove" -> playerSkills.setSkillLevel(skillType, playerSkills.getSkillLevel(skillType) - skillLevel);
                }
                messenger.confirm(player, "Ustawiłeś poziom " + ChatColor.WHITE + skillType.name() + ChatColor.GREEN + " na " + ChatColor.WHITE + skillLevel);
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        List<String> completer = new ArrayList<>();

        if(args.length == 1) {
            if("get".startsWith(args[0])) completer.add("get");
            if("set".startsWith(args[0])) completer.add("set");
            if("add".startsWith(args[0])) completer.add("add");
            if("remove".startsWith(args[0])) completer.add("remove");
            if("reset".startsWith(args[0])) completer.add("reset");
        } else if(args.length == 2) {
            Bukkit.matchPlayer(args[1]).stream().map(Player::getName).forEach(playerName -> {
                if(playerName.toLowerCase().startsWith(args[1].toLowerCase())) completer.add(playerName);
            });
        } else if(args.length == 3) {
            Arrays.stream(SkillType.values()).map(SkillType::name).forEach(skillName -> {
                if(skillName.toLowerCase().startsWith(args[2].toLowerCase())) completer.add(skillName);
            });
        } else if(args.length == 4) {
            if(args[0].equals("get")) return completer;
            SkillType skillType = SkillType.fromString(args[2]);
            if(skillType == null) return completer;
            Skill skill = BlackSkills.getInstance().skillsManager.getSkill(skillType);
            IntStream.range(0, skill.getMaxLevel() + 1).forEach(skillLevel -> completer.add(String.valueOf(skillLevel)));
        }

        return completer;
    }
}
