package me.foxyg3n.blackskills.skilldata.skills;

public enum SkillType {

    // Misc
    EXP_BOOST,
    JUMP_BOOST,
    FALL_DAMAGE,
    FOOD_REGENERATION,
    HEALTH_BOOST,

    // Miner
    HASTE,
    ORE_SMELT,
    MINE_MONEY,
    XRAY,
    FIRE_RESISTANCE,

    // Fighter
    POTION_SAVE,
    BOW_DAMAGE,
    PHYSICAL_DAMAGE,
    ARROW_REFLECTION,
    RESISTANCE;

    public static SkillType fromString(String skillTypeString) {
        try { return valueOf(skillTypeString); }
        catch(Exception e) { return null; }
    }
}
