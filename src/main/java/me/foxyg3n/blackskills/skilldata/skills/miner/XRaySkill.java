package me.foxyg3n.blackskills.skilldata.skills.miner;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XRaySkill extends EventSkill<PlayerInteractEvent> {

    private final Set<Player> xrayCooldown = new HashSet<>();
    private static final Set<Material> mask = new HashSet<>() {};

    static {
        mask.add(Material.AIR);
        mask.add(Material.CAVE_AIR);
        mask.add(Material.VOID_AIR);
        mask.add(Material.COPPER_ORE);
        mask.add(Material.DEEPSLATE_COPPER_ORE);
        mask.add(Material.COAL_ORE);
        mask.add(Material.DEEPSLATE_COAL_ORE);
        mask.add(Material.IRON_ORE);
        mask.add(Material.DEEPSLATE_IRON_ORE);
        mask.add(Material.GOLD_ORE);
        mask.add(Material.DEEPSLATE_GOLD_ORE);
        mask.add(Material.REDSTONE_ORE);
        mask.add(Material.DEEPSLATE_REDSTONE_ORE);
        mask.add(Material.LAPIS_ORE);
        mask.add(Material.DEEPSLATE_LAPIS_ORE);
        mask.add(Material.DIAMOND_ORE);
        mask.add(Material.DEEPSLATE_DIAMOND_ORE);
        mask.add(Material.EMERALD_ORE);
        mask.add(Material.DEEPSLATE_EMERALD_ORE);
        mask.add(Material.NETHER_GOLD_ORE);
        mask.add(Material.NETHER_QUARTZ_ORE);
    }

    public XRaySkill() {
        super(SkillType.XRAY, new int[] { 1, 1 });
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
        if(!event.getPlayer().isSneaking()) return;
        Player player = event.getPlayer();
        if(xrayCooldown.contains(player)) {
            BlackSkills.getMessenger().warning(player, "Nie możesz jeszcze skorzystać z umiejętności xray");
        } else {
            xrayCooldown.add(player);
            switch(skillLevel) {
                case 1 -> disguiseBlocks(player, 10);
                case 2 -> disguiseBlocks(player, 20);
            }
            Bukkit.getScheduler().runTaskLater(BlackSkills.getInstance(), () -> xrayCooldown.remove(player), 20 * 20);
        }

    }

    private void disguiseBlocks(Player player, int radius) {
        HashMap<Location, BlockData> originBlocks = new HashMap<>();
        HashMap<Location, BlockData> blockChanges = new HashMap<>();

        BlockData blockData = Material.BARRIER.createBlockData();
        Location origin = player.getLocation();
        for(int x = -radius; x < radius; x++) {
            for(int y = -radius; y < radius; y++) {
                for(int z = -radius; z < radius; z++) {
                    Location location = origin.clone().add(x, y, z);
                    if(!mask.contains(location.getBlock().getType())) {
                        originBlocks.put(location, location.getBlock().getBlockData());
                        blockChanges.put(location, blockData);
                    }
                }
            }
        }

        loadChunkAndSendBlockChange(player, blockChanges);
        Bukkit.getScheduler().runTaskLater(BlackSkills.getInstance(), () -> loadChunkAndSendBlockChange(player, originBlocks), 20 * 5);
    }

    private void loadChunkAndSendBlockChange(Player player, HashMap<Location, BlockData> blockChanges) {
        if(blockChanges.isEmpty()) return;
        Location location = blockChanges.keySet().toArray(Location[]::new)[0];
        if(!location.isChunkLoaded() && location.getChunk().load()) player.sendMultiBlockChange(blockChanges);
    }

    @Override
    public String getDescription() {
        return "Dodaje umiejętność widzenia surowców przez bloki (prawy + shift)";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("10 kratek widzenia", "20 kratek widzenia");
    }
}
