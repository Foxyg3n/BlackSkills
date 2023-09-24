package me.foxyg3n.blackskills.skilldata.skills;

import me.foxyg3n.blackskills.BlackSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class EventSkill<T extends Event> extends Skill {

    protected EventSkill(SkillType type, int[] levelCosts) {
        super(type, levelCosts);
        Bukkit.getPluginManager().registerEvents(getListener(), BlackSkills.getInstance());
    }

    public abstract Listener getListener();
    public abstract void runEvent(int skillLevel, T event);

    protected void addSkillEffect(Player player, int skillLevel) {}
    protected void removeSkillEffect(Player player) {}

    public void executeListener(T event) {
        Player player = getPlayer(event);
        if(player == null) return;
        if(isEnabled(player)) runEvent(getPlayerData(player).getSkillLevel(getSkillType()), event);
    }

    protected Player getPlayer(T event) {
        Player player = null;
        if(event instanceof PlayerEvent playerEvent) player = playerEvent.getPlayer();
        else if(event instanceof EntityEvent entityEvent) {
            if(entityEvent instanceof Player eventPlayer) player = eventPlayer;
        } else player = checkForPlayerMethod(event);
        return player;
    }

    private Player checkForPlayerMethod(Event event) {
        try {
            Method getPlayerMethod = event.getClass().getMethod("getPlayer");
            return (Player) getPlayerMethod.invoke(event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            return null;
        }
    }
}
