package fr.aerwyn81.randomitem.handlers;

import fr.aerwyn81.randomitem.RandomItem;
import fr.aerwyn81.randomitem.utils.FormatUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigHandler {

    private final File configFile;

    private final List<Material> blockBlacklist;
    private final List<Material> materials;

    private String assetFile;

    public ConfigHandler(File configFile) {
        this.configFile = configFile;

        this.blockBlacklist = new ArrayList<>();
        this.materials = new ArrayList<>();
    }

    public void loadConfiguration() {
        blockBlacklist.clear();
        materials.clear();

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        assetFile = config.getString("assetFile", "");

        if (config.contains("blockBlacklist")) {
            config.getStringList("blockBlacklist").forEach(material -> {
                try {
                    blockBlacklist.add(Material.valueOf(material));
                } catch (Exception ex) {
                    RandomItem.log.sendMessage(FormatUtils.translate("&cCannot parse material name of &e" + material));
                }
            });

            updateMaterialsAvailable();
        }
    }

    private void updateMaterialsAvailable() {
        materials.addAll(Arrays.stream(Material.values()).filter(m -> !blockBlacklist.contains(m)).collect(Collectors.toSet()));
    }

    public ArrayList<Material> getMaterialsAvailable() {
        return materials.stream().filter(m -> !m.isAir() && m.isBlock() && m.isItem()).collect(Collectors.toCollection(ArrayList::new));
    }

    public String getAssetFile() {
        return assetFile;
    }
}
