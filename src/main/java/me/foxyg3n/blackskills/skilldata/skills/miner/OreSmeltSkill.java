package me.foxyg3n.blackskills.skilldata.skills.miner;

import me.foxyg3n.blackskills.skilldata.skills.EventSkill;
import me.foxyg3n.blackskills.skilldata.skills.SkillType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.*;

public class OreSmeltSkill extends EventSkill<BlockBreakEvent> {

    public OreSmeltSkill() {
        super(SkillType.ORE_SMELT, new int[] { 1, 2 });
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
        if( !event.getPlayer().getInventory().getItemInMainHand().getType().toString().toLowerCase().contains("pickaxe") &&
            !event.getPlayer().getInventory().getItemInOffHand().getType().toString().toLowerCase().contains("pickaxe")) return;
        Block block = event.getBlock();
        Collection<ItemStack> drops = block.getDrops();
        block.getDrops().forEach(item -> {
            Optional<Recipe> smeltRecipe = Optional.empty();
            Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
            while(recipeIterator.hasNext()) {
                Recipe recipe = recipeIterator.next();
                if(recipe instanceof FurnaceRecipe && ((FurnaceRecipe) recipe).getInputChoice().test(item)) {
                    smeltRecipe = Optional.of(recipe);
                    break;
                }
            }
            smeltRecipe.ifPresent(recipe -> {
                drops.remove(item);
                ItemStack smeltedItem = recipe.getResult();
                if(skillLevel == 2) smeltedItem.setAmount(smeltedItem.getAmount() * 2);
                drops.add(smeltedItem);
            });
        });
        event.setDropItems(false);
        drops.forEach(drop -> {
            Location dropLocation = block.getLocation();
            dropLocation.getWorld().dropItemNaturally(dropLocation, drop);
        });
    }

    @Override
    public String getDescription() {
        return "Przetapia wykopane surowce jeżeli trzymasz w ręce kilof";
    }

    @Override
    public List<String> getLevelInfo() {
        return List.of("Przetapianie wykopanego surowca", "+podwaja ilość przetopionego surowca");
    }
}
