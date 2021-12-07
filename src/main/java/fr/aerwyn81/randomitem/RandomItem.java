package fr.aerwyn81.randomitem;

import fr.aerwyn81.randomitem.commands.RICommands;
import fr.aerwyn81.randomitem.commands.RITabCompleter;
import fr.aerwyn81.randomitem.handlers.ConfigHandler;
import fr.aerwyn81.randomitem.handlers.ItemHandler;
import fr.aerwyn81.randomitem.handlers.LanguageHandler;
import fr.aerwyn81.randomitem.utils.AssetsUtils;
import fr.aerwyn81.randomitem.utils.ConfigUpdater;
import fr.aerwyn81.randomitem.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public final class RandomItem extends JavaPlugin {

    private static RandomItem instance;
    public static ConsoleCommandSender log;

    private ConfigHandler configHandler;
    private LanguageHandler languageHandler;
    private ItemHandler itemHandler;

    @Override
    public void onEnable() {
        instance = this;
        log = Bukkit.getConsoleSender();

        log.sendMessage(FormatUtils.translate("&bRandomItem &einitializing..."));

        File configFile = new File(getDataFolder(), "config.yml");
        saveDefaultConfig();
        try {
            ConfigUpdater.update(this, "config.yml", configFile, Collections.emptyList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();

        this.configHandler = new ConfigHandler(configFile);
        this.configHandler.loadConfiguration();

        this.languageHandler = new LanguageHandler(this, "fr");
        this.languageHandler.pushMessages();

        this.itemHandler = new ItemHandler(this);

        AssetsUtils.load(new File(getDataFolder(), configHandler.getAssetFile()));

        registerCommands();

        log.sendMessage(FormatUtils.translate("&bRandomItem &einitialised!"));
    }

    private void registerCommands() {
        getCommand("RandomItem").setExecutor(new RICommands(this));
        getCommand("RandomItem").setTabCompleter(new RITabCompleter(this));
    }

    public static RandomItem getInstance() {
        return instance;
    }

    public ConfigHandler getConfigHandler() { return configHandler; }

    public LanguageHandler getLanguageHandler() { return languageHandler; }

    public ItemHandler getItemHandler() {
        return itemHandler;
    }


}
