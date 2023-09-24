package me.foxyg3n.blackskills.skilldata.skills.fighter;

import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.List;
import java.util.Random;

public class PotionSaveSkill extends EventSkill<PlayerItemConsumeEvent> {

    public PotionSaveSkill() {
        super(SkillType.POTION_SAVE, new int[] { 1, 2 });
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
        ItemStack item = event.getItem();
        int chance = 5; //  5 / 10  (50%)
        int roll = new Random().nextInt(10);
        if(item.getItemMeta() instanceof PotionMeta potionMeta) {
            boolean shouldSavePotion = !(skillLevel == 1 && potionMeta.getBasePotionData().isUpgraded());
            if(shouldSavePotion && roll <= chance) event.setReplacement(item);
        }
    }

    @Override
    public String getDescription() {
        return "50% szans na zachowanie mikstury";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("Mikstura I poziomu", "Mikstura II poziomu");
    }
}
