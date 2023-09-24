package me.foxyg3n.blackskills.skilldata.skills.fighter;

import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class ArrowReflectionSkill extends EventSkill<EntityDamageByEntityEvent> {

    public ArrowReflectionSkill() {
        super(SkillType.ARROW_REFLECTION, new int[] { 3 });
    }

    @Override
    protected Player getPlayer(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player player) return player;
        return null;
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
    public void runEvent(int skillLevel, EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Arrow) event.setCancelled(true);
    }

    @Override
    public String getDescription() {
        return "Odbijasz wszlekie strzały";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("Pełne odbicie pocisków");
    }
}
