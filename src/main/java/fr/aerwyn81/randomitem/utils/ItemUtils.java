package fr.aerwyn81.randomitem.utils;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemStack getRandomItemStack(ArrayList<Material> materials) {
        return new ItemStack(materials.get(ThreadLocalRandom.current().nextInt(materials.size())), 1);
    }

    public static EntityType getRandomEntityType() {
        ArrayList<EntityType> entitiesMonster = Arrays.stream(EntityType.values())
                .filter(EntityType::isAlive)
                .filter(eT -> eT != EntityType.PLAYER &&
                        eT != EntityType.GIANT &&
                        eT != EntityType.WITHER &&
                        eT != EntityType.ELDER_GUARDIAN &&
                        eT != EntityType.ENDER_DRAGON &&
                        eT != EntityType.ARMOR_STAND)
                .collect(Collectors.toCollection(ArrayList::new));

        return entitiesMonster.get(ThreadLocalRandom.current().nextInt(entitiesMonster.size()));
    }
}
