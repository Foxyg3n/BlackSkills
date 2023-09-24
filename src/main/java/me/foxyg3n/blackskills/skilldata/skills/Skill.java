package me.foxyg3n.blackskills.skilldata.skills;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Skill {

    private final List<Player> involvedPlayers = new ArrayList<>();

    private final SkillType type;
    private final int[] levels;

    protected Skill(SkillType type, int[] levelCosts) {
        this.type = type;
        this.levels = levelCosts;
    }

    public void enableSkill(Player player) {
        addSkillEffect(player, BlackSkills.getInstance().playerDataManager.getPlayerSkills(player).getSkillLevel(getSkillType()));
        if(!involvedPlayers.contains(player)) involvedPlayers.add(player);
    }

    public void disableSkill(Player player) {
        removeSkillEffect(player);
        involvedPlayers.remove(player);
    }

    protected abstract void addSkillEffect(Player player, int skillLevel);
    protected abstract void removeSkillEffect(Player player);
    public abstract String getDescription();
    public abstract List<String> getLevelInfo();

    public SkillType getSkillType() {
        return type;
    }

    public int[] getLevels() {
        return levels;
    }

    public int getLevelCost(int level) {
        return levels[level - 1];
    }

    public int getMaxLevel() {
        return levels.length;
    }

    protected PlayerSkills getPlayerData(Player player) {
        return BlackSkills.getInstance().playerDataManager.getPlayerSkills(player);
    }

    public boolean isEnabled(Player player) {
        return involvedPlayers.contains(player);
    }
}
