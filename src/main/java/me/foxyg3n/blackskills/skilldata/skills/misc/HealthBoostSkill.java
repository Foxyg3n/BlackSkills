package me.foxyg3n.blackskills.skilldata.skills.misc;

import me.foxyg3n.blackskills.skilldata.skills.Skill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.util.List;

public class HealthBoostSkill extends Skill {

    public HealthBoostSkill() {
        super(SkillType.HEALTH_BOOST, new int[] { 1, 1, 1 });
    }

    @Override
    protected void addSkillEffect(Player player, int skillLevel) {
        AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if(playerMaxHealth == null) return;
        removeSkillEffect(player);
        switch(skillLevel) {
            case 1 -> playerMaxHealth.setBaseValue(22);
            case 2 -> playerMaxHealth.setBaseValue(24);
            case 3 -> playerMaxHealth.setBaseValue(28);
        }
    }

    @Override
    protected void removeSkillEffect(Player player) {
        AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if(playerMaxHealth == null) return;
        playerMaxHealth.setBaseValue(20);
    }

    @Override
    public String getDescription() {
        return "Dodaje maksymalne serduszka";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("+1 serduszko", "+2 serduszka", "+4 serduszka");
    }

}
