package fr.aerwyn81.randomitem.commands;

import fr.aerwyn81.randomitem.RandomItem;
import fr.aerwyn81.randomitem.utils.AssetsUtils;
import fr.aerwyn81.randomitem.utils.FormatUtils;
import fr.aerwyn81.randomitem.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public record RICommands(RandomItem main) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!PlayerUtils.hasPermission(sender, "randomitem.admin")) {
            sender.sendMessage(main.getLanguageHandler().getMessage("Messages.MissingPermission"));
            return true;
        }

        if (args.length == 0) {
            helpCommand(sender);
            return true;
        }

        switch (args[0]) {
            case "give" -> {
                giveCommand(sender, args);
                return true;
            }
            case "reload" -> {
                reloadCommand(sender);
                return true;
            }
            case "version" -> {
                versionCommand(sender);
                return true;
            }
            default -> {
                helpCommand(sender);
                return true;
            }
        }
    }

    private void helpCommand(CommandSender sender) {
        sender.sendMessage(FormatUtils.translate("&7--------------------[ &b&lAide &7]--------------------"));
        sender.sendMessage(FormatUtils.translate("&b/ri help &8: &7&oAffiche ce message"));
        sender.sendMessage(FormatUtils.translate("&b/ri reload &8: &7&oRecharge la configuration"));
        sender.sendMessage(FormatUtils.translate("&b/ri give <player> <nombre> &8: &7&oDonne un <nombre> d'objet aléatoire au <joueur>"));
        sender.sendMessage(FormatUtils.translate("&b/ri version &8: &7&oAffiche la version du plugin"));
    }

    private void giveCommand(CommandSender sender, String[] args) {
        if (args.length > 3 || args.length < 2) {
            helpCommand(sender);
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);

        if (player == null) {
            sender.sendMessage(main.getLanguageHandler().getMessage("Messages.PlayerNotFound")
                    .replaceAll("%player%", player.getName()));
            return;
        }

        int nbItems = args.length == 2 ? 1 : FormatUtils.parseWithDefault(args[2], 1);

        for (int i = 0; i < nbItems; i++) {
            ItemStack item = main.getItemHandler().giveRandomItem(player);
            sender.sendMessage(main.getLanguageHandler().getMessage("Messages.SenderItemReward")
                    .replaceAll("%player%", player.getName())
                    .replaceAll("%item%", AssetsUtils.getMaterialName(item.getType())));
        }
    }

    private void reloadCommand(CommandSender sender) {
        main.reloadConfig();
        main.getConfigHandler().loadConfiguration();

        main.getLanguageHandler().setLanguage("fr");
        main.getLanguageHandler().pushMessages();

        AssetsUtils.load(new File(main.getDataFolder(), main.getConfigHandler().getAssetFile()));

        sender.sendMessage(main.getLanguageHandler().getMessage("Messages.ReloadSuccess"));
    }

    private void versionCommand(CommandSender sender) {
        sender.sendMessage(main.getLanguageHandler().getMessage("Messages.ShowVersion")
                .replaceAll("%version%", main.getDescription().getVersion()));
    }
}
