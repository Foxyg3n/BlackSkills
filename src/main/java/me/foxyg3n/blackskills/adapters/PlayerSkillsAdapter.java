package me.foxyg3n.blackskills.adapters;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import me.foxyg3n.blackskills.skilldata.PlayerSkills;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

public class PlayerSkillsAdapter implements JsonDeserializer<PlayerSkills>, JsonSerializer<PlayerSkills> {

    @Override
    public PlayerSkills deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        UUID playerUUID = UUID.fromString(obj.get("uuid").getAsString());
        HashMap<SkillType, Integer> skillLevels = context.deserialize(obj.get("skillLevels"), new TypeToken<HashMap<SkillType, Integer>>() {}.getType());
        PlayerSkills playerSkills = new PlayerSkills(playerUUID);
        playerSkills.loadSkillData(skillLevels);
        Bukkit.getLogger().info("player skill level: " + playerSkills.getSkillLevel(SkillType.EXP_BOOST));
        return playerSkills;
    }

    @Override
    public JsonElement serialize(PlayerSkills playerSkills, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("uuid", playerSkills.getPlayerUUID().toString());
        obj.add("skillLevels", context.serialize(playerSkills.getSkillData()));
        return obj;
    }

}
