package me.foxyg3n.blackskills.skilldata.skills.miner;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HasteSkill extends EventSkill<PlayerInteractEvent> {

    private final Set<Player> hasteCooldown = new HashSet<>();

    public HasteSkill() {
        super(SkillType.HASTE, new int[] { 1, 1 });
    }

    @Override
    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onEvent(PlayerInteractEvent event) {
                executeListener(event);
            }
        };
    }

    @Override
    public void runEvent(int skillLevel, PlayerInteractEvent event) {
        if( !event.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe") &&
                !event.getPlayer().getInventory().getItemInOffHand().getType().toString().toLowerCase().contains("pickaxe")) return;
        if(event.getHand() == null) return;
        if(!event.getHand().equals(EquipmentSlot.HAND)) return;
        if(!event.getAction().isRightClick()) return;
        if(event.getPlayer().isSneaking()) return;
        Player player = event.getPlayer();
        if(hasteCooldown.contains(player)) {
            BlackSkills.getMessenger().warning(player, "Nie możesz jeszcze skorzystać z umiejętności przyspieszenia kopania");
        } else {
            hasteCooldown.add(player);
            PotionEffect hasteEffect = player.getPotionEffect(PotionEffectType.FAST_DIGGING);
            int addDigTime = hasteEffect != null && hasteEffect.getAmplifier() == 0 ? hasteEffect.getDuration() : 0;
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 10 + addDigTime, skillLevel == 1 ? 0: 1, false, false));
            Bukkit.getScheduler().runTaskLater(BlackSkills.getInstance(), () -> hasteCooldown.remove(player), 20 * 30);
        }
    }

    @Override
    public String getDescription() {
        return "Dodaje umiejętność szybkiego kopania (prawy)";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("Prędkość I", "Prędkość II");
    }
}
