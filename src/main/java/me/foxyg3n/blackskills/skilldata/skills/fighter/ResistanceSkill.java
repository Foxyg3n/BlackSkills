package me.foxyg3n.blackskills.skilldata.skills.fighter;

import me.foxyg3n.blackskills.skilldata.skills.PotionEffectSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ResistanceSkill extends PotionEffectSkill {

    public ResistanceSkill() {
        super(SkillType.RESISTANCE, new int[] { 1, 1 }, PotionEffectType.DAMAGE_RESISTANCE);
    }

    @Override
    protected void addSkillEffect(Player player, int skillLevel) {
        if(player.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)) player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        switch(skillLevel) {
            case 1 -> player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PotionEffect.INFINITE_DURATION, 0, false, false));
            case 2 -> player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PotionEffect.INFINITE_DURATION, 1, false, false));
        }
    }

    @Override
    public void removeSkillEffect(Player player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }

    @Override
    public String getDescription() {
        return "Zwiększa odporność na wszelkie obrażenia";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("Odporność I", "Odporność II");
    }

}
