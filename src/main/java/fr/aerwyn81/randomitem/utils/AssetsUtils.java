package fr.aerwyn81.randomitem.utils;

import com.google.gson.GsonBuilder;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class AssetsUtils {
    private static final Map<Material, String> cachedMaterials = new HashMap<>();
    private static final Map<String, String> cachedEntities = new HashMap<>();

    public static void load(File assetFile) {

        try {
            HashMap<String, Object> map = new GsonBuilder().create().fromJson(new FileReader(assetFile), HashMap.class);
            for (Map.Entry<String, Object> fr : map.entrySet()) {
                String key = fr.getKey();
                if (key.startsWith("item.minecraft.")) {
                    String item = key.substring(15);

                    try {
                        cachedMaterials.put(Material.valueOf(item.toUpperCase()), (String) fr.getValue());
                    } catch (Exception ignored) {
                    }
                } else if (key.startsWith("block.minecraft.")) {
                    String item = key.substring(16);

                    try {
                        cachedMaterials.put(Material.valueOf(item.toUpperCase()), (String) fr.getValue());
                    } catch (Exception ignored) {
                    }
                } else if (key.startsWith("entity.minecraft.")) {
                    String item = key.substring(17);

                    try {
                        cachedEntities.put(item.toUpperCase(), (String) fr.getValue());
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMaterialName(Material type) {
        String name = cachedMaterials.get(type);
        return name != null ? name : defaultFormat(type.name());
    }

    public static String getFullSpawnerName(String name) {
        return FormatUtils.translate("&d" + cachedMaterials.get(Material.SPAWNER) + " à " + cachedEntities.get(name));
    }

    private static String defaultFormat(String value) {
        return WordUtils.capitalize(value.toLowerCase().replace('_', ' '));
    }
}
