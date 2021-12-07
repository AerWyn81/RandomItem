package fr.aerwyn81.randomitem.utils;

import com.google.gson.GsonBuilder;
import fr.aerwyn81.randomitem.RandomItem;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public record AssetsHelper(RandomItem main) {
    private static final Map<Material, String> cachedMaterials = new HashMap<>();

    public static void load(File assetFile) {

        try {
            HashMap<String, Object> map = new GsonBuilder().create().fromJson(new FileReader(assetFile), HashMap.class);
            for (Map.Entry<String, Object> en : map.entrySet()) {
                String key = en.getKey();
                if (key.startsWith("item.minecraft.")) {
                    String item = key.substring(15);

                    try {
                        cachedMaterials.put(Material.valueOf(item.toUpperCase()), (String) en.getValue());
                    } catch (Exception ignored) { }
                } else if (key.startsWith("block.minecraft.")) {
                    String item = key.substring(16);

                    try {
                        cachedMaterials.put(Material.valueOf(item.toUpperCase()), (String) en.getValue());
                    } catch (Exception ignored) { }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMaterialName(Material type) {
        String name = cachedMaterials.get(type);
        return name != null ? name : defaultFormat(type.name());
    }

    private static String defaultFormat(String value){
        return WordUtils.capitalize(value.toLowerCase().replace('_', ' '));
    }
}
