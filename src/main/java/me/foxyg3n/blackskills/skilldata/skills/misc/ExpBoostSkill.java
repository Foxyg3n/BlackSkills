package me.foxyg3n.blackskills.skilldata.skills.misc;

import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.sharyxxx.levelsystem.Events.PreEXPGainEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class ExpBoostSkill extends EventSkill<PreEXPGainEvent> {

    public ExpBoostSkill() {
        super(SkillType.EXP_BOOST, new int[] { 1, 1, 2 });
    }

    @Override
    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onEvent(PreEXPGainEvent event) {
                executeListener(event);
            }
        };
    }

    @Override
    public void runEvent(int skillLevel, PreEXPGainEvent event) {
        switch(skillLevel) {
            case 1 -> event.setGainedEXP(event.gainedExp * 1.1d);
            case 2 -> event.setGainedEXP(event.gainedExp * 1.2d);
            case 3 -> event.setGainedEXP(event.gainedExp * 1.5d);
        }
    }

    @Override
    public String getDescription() {
        return "Przyspiesza zdobywanie doświadczenia";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("+10% doświadczenia", "+20% doświadczenia", "+50% doświadczenia");
    }
}
