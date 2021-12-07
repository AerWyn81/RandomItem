package fr.aerwyn81.randomitem.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MaterialUtils {

    public static ItemStack getRandomItemStack(List<Material> materials) {
        return new ItemStack(materials.get(ThreadLocalRandom.current().nextInt(materials.size())), 1);
    }
}
