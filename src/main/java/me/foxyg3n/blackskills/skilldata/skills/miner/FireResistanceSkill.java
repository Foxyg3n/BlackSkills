package me.foxyg3n.blackskills.skilldata.skills.miner;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.PotionEffectSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FireResistanceSkill extends PotionEffectSkill {

    public FireResistanceSkill() {
        super(SkillType.FIRE_RESISTANCE, new int[] { 1, 1, 2 }, PotionEffectType.FIRE_RESISTANCE);

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onFireDamage(EntityDamageEvent event) {
                if(!event.getCause().equals(EntityDamageEvent.DamageCause.FIRE)) return;
                if(event.getEntity() instanceof Player player) {
                    if(!isEnabled(player)) return;
                    int skillLevel = getPlayerData(player).getSkillLevel(getSkillType());
                    if(skillLevel == 3) event.setCancelled(true);
                }
            }
        }, BlackSkills.getInstance());
    }

    @Override
    protected void addSkillEffect(Player player, int skillLevel) {
        if(player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        switch(skillLevel) {
            case 1 -> player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 0, false, false));
            case 2 -> player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PotionEffect.INFINITE_DURATION, 1, false, false));
        }
    }

    @Override
    public void removeSkillEffect(Player player) {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
    }

    @Override
    public String getDescription() {
        return "Zmniejsza obrażenia od ognia";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("odp. I", "odp. II", "Brak obrażeń");
    }
}
