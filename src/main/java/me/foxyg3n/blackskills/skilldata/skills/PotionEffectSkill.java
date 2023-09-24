package me.foxyg3n.blackskills.skilldata.skills;

import me.foxyg3n.blackskills.BlackSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public abstract class PotionEffectSkill extends Skill {

    private static final HashMap<PotionEffectType, SkillType> effectTypes = new HashMap<>();
    private static final Listener effectClearListener = new Listener() {
        @EventHandler
        public void onEffectClear(EntityPotionEffectEvent event) {
            if(event.getEntity() instanceof Player player) {
                PotionEffect potionEffect = event.getOldEffect();
                if(potionEffect == null) return;
                if(!effectTypes.containsKey(potionEffect.getType())) return;
                if(!BlackSkills.getInstance().playerDataManager.getPlayerSkills(player).hasSkillEnabled(effectTypes.get(potionEffect.getType()))) return;
                if(event.getCause().equals(EntityPotionEffectEvent.Cause.PLUGIN)) return;
                if(event.getAction().equals(EntityPotionEffectEvent.Action.ADDED)) return;
                event.setCancelled(true);
            }
        }
    };

    protected PotionEffectSkill(SkillType type, int[] levelCosts, PotionEffectType effectType) {
        super(type, levelCosts);
        registerEffectType(effectType);
    }

    private void registerEffectType(PotionEffectType effectType) {
        if(!HandlerList.getRegisteredListeners(BlackSkills.getInstance()).contains(effectClearListener))
            Bukkit.getPluginManager().registerEvents(effectClearListener, BlackSkills.getInstance());
        effectTypes.put(effectType, getSkillType());
    }

}
