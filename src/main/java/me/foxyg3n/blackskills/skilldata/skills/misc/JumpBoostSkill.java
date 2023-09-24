package me.foxyg3n.blackskills.skilldata.skills.misc;

import me.foxyg3n.blackskills.skilldata.skills.PotionEffectSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class JumpBoostSkill extends PotionEffectSkill {

    public JumpBoostSkill() {
        super(SkillType.JUMP_BOOST, new int[] { 1, 1 }, PotionEffectType.JUMP);
    }

    @Override
    protected void addSkillEffect(Player player, int skillLevel) {
        if(player.hasPotionEffect(PotionEffectType.JUMP)) player.removePotionEffect(PotionEffectType.JUMP);
        switch(skillLevel) {
            case 1 -> player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, PotionEffect.INFINITE_DURATION, 0, false, false));
            case 2 -> player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, PotionEffect.INFINITE_DURATION, 1, false, false));
        }
    }

    @Override
    public void removeSkillEffect(Player player) {
        player.removePotionEffect(PotionEffectType.JUMP);
    }

    @Override
    public String getDescription() {
        return "Zwiększa wysokość skoku";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("+0.5 kratki", "+1 kratka");
    }
}
