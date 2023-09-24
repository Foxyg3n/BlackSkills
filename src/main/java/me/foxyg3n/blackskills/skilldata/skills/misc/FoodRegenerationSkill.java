package me.foxyg3n.blackskills.skilldata.skills.misc;

import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FoodRegenerationSkill extends EventSkill<PlayerItemConsumeEvent> {

    public FoodRegenerationSkill() {
        super(SkillType.FOOD_REGENERATION, new int[] { 1, 1, 1 });
    }

    @Override
    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onEvent(PlayerItemConsumeEvent event) {
                executeListener(event);
            }
        };
    }

    @Override
    public void runEvent(int skillLevel, PlayerItemConsumeEvent event) {
        if(event.getItem().getType().isEdible()) {
            switch(skillLevel) {
                case 1 -> event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 0, false, false));
                case 2 -> event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 0, false, false));
                case 3 -> event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1, false, false));
            }
        }
    }

    @Override
    public String getDescription() {
        return "Daje regenerację przy jedzeniu";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("reg. I 5sek.", "reg. I 10sek.", "reg. II 10sek.");
    }
}
