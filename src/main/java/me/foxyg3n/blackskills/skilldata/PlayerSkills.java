package me.foxyg3n.blackskills.skilldata;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.Skill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.foxyg3n.blackskills.utils.LevelUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

public class PlayerSkills {

    private final UUID playerUUID;
    private Map<SkillType, Integer> skills = new HashMap<>();
    public final SkillGUI gui = new SkillGUI(this);

    public PlayerSkills(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    private SkillsManager getSkillsManager() {
        return BlackSkills.getInstance().skillsManager;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public boolean isPlayerOnline() {
        return Bukkit.getOfflinePlayer(playerUUID).isOnline();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    public boolean hasAvailablePoints() {
        return getAvailablePoints() > 0;
    }

    public int getAvailablePoints() {
        return Math.max(0, getPlayerLevel() - skills.entrySet().stream()
                .mapToInt(entry -> IntStream.range(1, entry.getValue() + 1).map(level -> getSkillsManager().getSkill(entry.getKey()).getLevelCost(level)).sum())
                .sum());
    }

    public int getPlayerLevel() {
        return LevelUtils.getLevel(getPlayer());
    }

    public int getPlayerTier() {
        return getPlayerLevel() / 5 + 1;
    }

    public boolean hasSkill(SkillType skillType) {
        return skills.containsKey(skillType);
    }

    public int getSkillLevel(SkillType skillType) {
        return hasSkill(skillType) ? skills.get(skillType) : 0;
    }

    public void setSkillLevel(SkillType skillType, int value) {
        if(value < 0) throw new IllegalArgumentException("Value of the skill level must be 0 or greater");
        Skill skill = getSkillsManager().getSkill(skillType);
        if(value > 0) {
            skills.put(skillType, Math.min(value, skill.getMaxLevel()));
            skill.enableSkill(getPlayer());
        } else {
            skills.remove(skillType);
            skill.disableSkill(getPlayer());
        }
    }

    public boolean upgradeSkill(SkillType skillType) {
        Skill skill = getSkillsManager().getSkill(skillType);
        if(getSkillLevel(skillType) >= skill.getMaxLevel()) return false;
        int skillCost = getSkillsManager().getSkill(skillType).getLevelCost(getSkillLevel(skillType) + 1);
        if(getAvailablePoints() < skillCost) return false;
        setSkillLevel(skillType, getSkillLevel(skillType) + 1);
        gui.updateSkill(skillType);
        return true;
    }

    public Map<SkillType, Integer> getSkillData() {
        return skills;
    }

    public void loadSkillData(HashMap<SkillType, Integer> skillData) {
        disableAllSkills();
        skills = skillData;
        enableAllSkills();
        gui.update();
    }

    public void enableAllSkills() {
        skills.keySet().forEach(skillType -> { if(isPlayerOnline()) getSkillsManager().getSkill(skillType).enableSkill(getPlayer()); });
    }

    public void disableAllSkills() {
        skills.keySet().forEach(skillType -> { if(isPlayerOnline()) getSkillsManager().getSkill(skillType).disableSkill(getPlayer()); });
    }

    public boolean hasSkillEnabled(SkillType skillType) {
        if(skillType == null) {
            throw new NullPointerException("SkillType cannot be null");
        }
        return isPlayerOnline() && getSkillsManager().getSkill(skillType).isEnabled(getPlayer());
    }

    public void resetSkills() {
        disableAllSkills();
        skills = new HashMap<>();
        gui.update();
    }
}
