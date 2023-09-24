package me.foxyg3n.blackskills.skilldata.skills.misc;

import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class FallDamageSkill extends EventSkill<EntityDamageEvent> {

    public FallDamageSkill() {
        super(SkillType.FALL_DAMAGE, new int[] { 1, 1, 2 });
    }

    @Override
    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onEvent(EntityDamageEvent event) {
                executeListener(event);
            }
        };
    }

    @Override
    public void runEvent(int skillLevel, EntityDamageEvent event) {
        if(!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        switch(skillLevel) {
            case 1 -> event.setDamage(event.getDamage() / 1.5);
            case 2 -> event.setDamage(event.getDamage() / 3);
            case 3 -> event.setCancelled(true);
        }
    }

    @Override
    public String getDescription() {
        return "Zmniejsza obrażenia od upadku";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("2/3 obrażeń", "1/3 obrażeń", "Brak obrażeń");
    }

}
