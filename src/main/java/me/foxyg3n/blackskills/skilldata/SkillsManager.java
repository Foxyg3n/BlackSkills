package me.foxyg3n.blackskills.skilldata;

import me.foxyg3n.blackskills.skilldata.skills.Skill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import me.foxyg3n.blackskills.skilldata.skills.fighter.*;
import me.foxyg3n.blackskills.skilldata.skills.miner.*;
import me.foxyg3n.blackskills.skilldata.skills.misc.*;

import java.util.*;
import java.util.stream.Collectors;

public class SkillsManager {

    private final List<HashMap<SkillType, Skill>> skillBranches = new ArrayList<>();

    public SkillsManager() {
        HashMap<SkillType, Skill> miscBranch = new LinkedHashMap<>();
        HashMap<SkillType, Skill> minerBranch = new LinkedHashMap<>();
        HashMap<SkillType, Skill> fighterBranch = new LinkedHashMap<>();

        miscBranch.put(SkillType.EXP_BOOST, new ExpBoostSkill());
        miscBranch.put(SkillType.JUMP_BOOST, new JumpBoostSkill());
        miscBranch.put(SkillType.FALL_DAMAGE, new FallDamageSkill());
        miscBranch.put(SkillType.FOOD_REGENERATION, new FoodRegenerationSkill());
        miscBranch.put(SkillType.HEALTH_BOOST, new HealthBoostSkill());

        minerBranch.put(SkillType.HASTE, new HasteSkill());
        minerBranch.put(SkillType.ORE_SMELT, new OreSmeltSkill());
        minerBranch.put(SkillType.MINE_MONEY, new MineMoneySkill());
        minerBranch.put(SkillType.XRAY, new XRaySkill());
        minerBranch.put(SkillType.FIRE_RESISTANCE, new FireResistanceSkill());

        fighterBranch.put(SkillType.POTION_SAVE, new PotionSaveSkill());
        fighterBranch.put(SkillType.BOW_DAMAGE, new BowDamageSkill());
        fighterBranch.put(SkillType.PHYSICAL_DAMAGE, new PhysicalDamageSkill());
        fighterBranch.put(SkillType.ARROW_REFLECTION, new ArrowReflectionSkill());
        fighterBranch.put(SkillType.RESISTANCE, new ResistanceSkill());

        skillBranches.add(miscBranch);
        skillBranches.add(minerBranch);
        skillBranches.add(fighterBranch);
    }

    public List<HashMap<SkillType, Skill>> getSkillBranches() {
        return skillBranches;
    }

    public HashMap<SkillType, Skill> getSkillBranch(int skillTier) {
        return skillBranches.get(skillTier - 1);
    }

    public Map<SkillType, Skill> getSkills() {
        return skillBranches.stream()
                .flatMap(hashMap -> hashMap.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Skill getSkill(SkillType skillType) {
        return getSkills().get(skillType);
    }

    public int getSkillTier(SkillType skillType) {
        for(HashMap<SkillType, Skill> skillBranch : getSkillBranches()) {
            int skillTier = 1;
            for(SkillType type : skillBranch.keySet()) {
                if(type == skillType) return skillTier;
                skillTier++;
            }
        }
        return 0;
    }
}
