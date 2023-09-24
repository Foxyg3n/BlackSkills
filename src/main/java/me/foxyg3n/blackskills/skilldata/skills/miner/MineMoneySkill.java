package me.foxyg3n.blackskills.skilldata.skills.miner;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.foxyg3n.blackskills.utils.MoneyUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MineMoneySkill extends EventSkill<BlockBreakEvent> {

    private static final Set<Material> ores = new HashSet<>() {};

    static {
        ores.add(Material.COPPER_ORE);
        ores.add(Material.DEEPSLATE_COPPER_ORE);
        ores.add(Material.COAL_ORE);
        ores.add(Material.DEEPSLATE_COAL_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.DEEPSLATE_IRON_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.DEEPSLATE_GOLD_ORE);
        ores.add(Material.REDSTONE_ORE);
        ores.add(Material.DEEPSLATE_REDSTONE_ORE);
        ores.add(Material.LAPIS_ORE);
        ores.add(Material.DEEPSLATE_LAPIS_ORE);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.DEEPSLATE_DIAMOND_ORE);
        ores.add(Material.EMERALD_ORE);
        ores.add(Material.DEEPSLATE_EMERALD_ORE);
        ores.add(Material.NETHER_GOLD_ORE);
        ores.add(Material.NETHER_QUARTZ_ORE);
    }

    public MineMoneySkill() {
        super(SkillType.MINE_MONEY, new int[] { 1, 1, 3 });
    }

    @Override
    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onEvent(BlockBreakEvent event) {
                executeListener(event);
            }
        };
    }

    @Override
    public void runEvent(int skillLevel, BlockBreakEvent event) {
        Economy economy = BlackSkills.getInstance().economy;
        if(economy == null) return;
        Player player = event.getPlayer();
        if( !player.getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe") &&
                !player.getInventory().getItemInOffHand().getType().toString().toLowerCase().contains("pickaxe")) return;
        Block block = event.getBlock();
        if(!ores.contains(block.getType())) return;
        switch(skillLevel) {
            case 1 -> MoneyUtils.addMoney(player, 1);
            case 2 -> MoneyUtils.addMoney(player, 2);
            case 3 -> MoneyUtils.addMoney(player, 5);
        }
    }

    @Override
    public String getDescription() {
        return "Dodaje monety za każdy wykopany surowiec";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("+1 za surowiec", "+2 za surowiec", "+5 za surowiec");
    }
}
