package me.foxyg3n.blackskills.skilldata;

import me.foxyg3n.blackskills.BlackSkills;
import me.foxyg3n.blackskills.skilldata.skills.Skill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.foxyg3n.utils.FoxInventory;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillGUI {

    private final PlayerSkills playerSkills;
    private final FoxInventory skillsGui;

    public SkillGUI(PlayerSkills playerSkills) {
        this.playerSkills = playerSkills;

        skillsGui = FoxInventory.getBuilder(BlackSkills.getInstance(), 6, "Umiejętności")
                .setItem(Material.EXPERIENCE_BOTTLE, getSlot(0, 5), ChatColor.YELLOW + "" + ChatColor.BOLD + "Punkty umiejętności", null)
                .setFullPrevention()
                .build();
        update();
    }

    public void update() {
        Arrays.stream(SkillType.values()).forEach(this::updateSkillState);
        updatePoints();
    }

    public void updateSkill(SkillType skillType) {
        updateSkillState(skillType);
        updatePoints();
    }

    public void openGUI() {
        if(playerSkills.isPlayerOnline()) playerSkills.getPlayer().openInventory(skillsGui.getBukkitInventory());
    }

    private void updatePoints() {
        int pointsSlot = getSlot(0, 5);
        int availablePoints = playerSkills.getAvailablePoints();

        skillsGui.setLoreInItem(pointsSlot, List.of(ChatColor.WHITE + "Masz " + availablePoints + " nieużytych punktów umiejętności"));
    }

    private void updateSkillState(SkillType skillType) {
        int skillSlot = getSkillSlot(skillType);
        ItemStack skillItem = getSkillItem(skillType);
        ItemStack skillStateItem = getSkillStateItem(skillType);

        skillsGui.setItem(skillStateItem, skillSlot - 1, null, null,
                player -> {
                    BlackSkills.getInstance().skillsManager.getSkill(skillType).enableSkill(player);
                    updateSkill(skillType);
                },
                player -> {
                    BlackSkills.getInstance().skillsManager.getSkill(skillType).disableSkill(player);
                    updateSkill(skillType);
                }
        );
        skillsGui.setItem(skillItem, skillSlot, null, null, player -> {
            boolean success = playerSkills.upgradeSkill(skillType);
            if(success) BlackSkills.getMessenger().confirm(player, "Ulepszyłeś umiejętność");
            else BlackSkills.getMessenger().warning(player, "Nie masz wystarczająco punktów, aby ulepszyć tę umiejętność lub osiągnąłeś maksymalny poziom");
        });
        skillsGui.setItem(skillStateItem, skillSlot + 1, null, null,
                player -> {
                    BlackSkills.getInstance().skillsManager.getSkill(skillType).enableSkill(player);
                    updateSkill(skillType);
                },
                player -> {
                    BlackSkills.getInstance().skillsManager.getSkill(skillType).disableSkill(player);
                    updateSkill(skillType);
                }
        );
    }

    private int getSlot(int x, int y) {
        return (y * 9) + x;
    }

    private int getSkillSlot(SkillType skillType) {
        return switch(skillType) {
            case EXP_BOOST -> getSlot(1, 4);
            case JUMP_BOOST -> getSlot(1, 3);
            case FALL_DAMAGE -> getSlot(1, 2);
            case FOOD_REGENERATION -> getSlot(1, 1);
            case HEALTH_BOOST -> getSlot(1, 0);
            case HASTE -> getSlot(4, 4);
            case ORE_SMELT -> getSlot(4, 3);
            case MINE_MONEY -> getSlot(4, 2);
            case XRAY -> getSlot(4, 1);
            case FIRE_RESISTANCE -> getSlot(4, 0);
            case POTION_SAVE -> getSlot(7, 4);
            case BOW_DAMAGE -> getSlot(7, 3);
            case PHYSICAL_DAMAGE -> getSlot(7, 2);
            case ARROW_REFLECTION -> getSlot(7, 1);
            case RESISTANCE -> getSlot(7, 0);
        };
    }

    private ItemStack getSkillItem(SkillType skillType) {
        Skill skill = BlackSkills.getInstance().skillsManager.getSkill(skillType);
        ItemStack item = new ItemStack(switch(skillType) {
            case EXP_BOOST -> Material.ARMS_UP_POTTERY_SHERD;
            case JUMP_BOOST -> Material.HOWL_POTTERY_SHERD;
            case FALL_DAMAGE -> Material.SHEAF_POTTERY_SHERD;
            case FOOD_REGENERATION -> Material.HEARTBREAK_POTTERY_SHERD;
            case HEALTH_BOOST -> Material.HEART_POTTERY_SHERD;
            case HASTE -> Material.MINER_POTTERY_SHERD;
            case ORE_SMELT -> Material.PLENTY_POTTERY_SHERD;
            case MINE_MONEY -> Material.PRIZE_POTTERY_SHERD;
            case XRAY -> Material.EXPLORER_POTTERY_SHERD;
            case FIRE_RESISTANCE -> Material.BURN_POTTERY_SHERD;
            case POTION_SAVE -> Material.BREWER_POTTERY_SHERD;
            case BOW_DAMAGE -> Material.ARCHER_POTTERY_SHERD;
            case PHYSICAL_DAMAGE -> Material.BLADE_POTTERY_SHERD;
            case ARROW_REFLECTION -> Material.MOURNER_POTTERY_SHERD;
            case RESISTANCE -> Material.SKULL_POTTERY_SHERD;
        });
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + skill.getDescription());
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < skill.getLevels().length; i++) {
            ChatColor color = playerSkills.getSkillLevel(skillType) >= (i + 1) ? ChatColor.GREEN : ChatColor.RED;
            lore.add(color + "Poziom " + (i + 1) + ": " + skill.getLevelInfo().get(i) + " (koszt: " + skill.getLevelCost(i + 1) + "pkt.)");
        }
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack getSkillStateItem(SkillType skillType) {
        Material material;
        String skillState;
        if(playerSkills.getPlayerTier() >= BlackSkills.getInstance().skillsManager.getSkillTier(skillType)) {
            if(playerSkills.hasSkill(skillType)) {
                if(playerSkills.hasSkillEnabled(skillType)) {
                    material = Material.GREEN_STAINED_GLASS_PANE;
                    skillState = ChatColor.GREEN + "" + ChatColor.BOLD + "Umiejętność włączona";
                }
                else {
                    material = Material.RED_STAINED_GLASS_PANE;
                    skillState = ChatColor.RED + "" + ChatColor.BOLD + "Umiejętność wyłączona";
                }
            } else {
                material = Material.GRAY_STAINED_GLASS_PANE;
                int levelCost = BlackSkills.getInstance().skillsManager.getSkill(skillType).getLevelCost(1);
                skillState = ChatColor.GRAY + "" + ChatColor.BOLD + "Kup za " + levelCost + " punkt/ów umiejętności";
            }
        } else {
            material = Material.BLACK_STAINED_GLASS_PANE;
            int skillTier = BlackSkills.getInstance().skillsManager.getSkillTier(skillType);
            skillState = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Potrzebujesz " + (--skillTier * 5) + " poziomu";
        }

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(skillState);
        itemMeta.setLore(List.of(
                ChatColor.WHITE + "Kliknij lewym, aby włączyć umiejętność",
                ChatColor.WHITE + "Kliknij prawym, aby wyłączyć umiejętność"
        ));
        item.setItemMeta(itemMeta);
        return item;
    }

}
