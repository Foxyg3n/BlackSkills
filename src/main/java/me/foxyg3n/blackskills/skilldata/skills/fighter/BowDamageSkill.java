package me.foxyg3n.blackskills.skilldata.skills.fighter;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class BowDamageSkill extends EventSkill<EntityDamageByEntityEvent> {

    public BowDamageSkill() {
        super(SkillType.BOW_DAMAGE, new int[] { 1, 1 });
    }

    @Override
    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onEvent(EntityDamageByEntityEvent event) {
                executeListener(event);
            }
        };
    }

    @Override
    protected Player getPlayer(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Arrow && ((Arrow) event.getDamager()).getShooter() instanceof Player damager)
            return damager;
        return null;
    }

    @Override
    public void runEvent(int skillLevel, EntityDamageByEntityEvent event) {
        switch(skillLevel) {
            case 1 -> event.setDamage(event.getDamage() + 1);
            case 2 -> event.setDamage(event.getDamage() + 2);
        }
    }

    @Override
    public String getDescription() {
        return "Zwiększa obrażenia od łuku";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("+0.5 serduszka", "+1 serduszko");
    }
    
}
