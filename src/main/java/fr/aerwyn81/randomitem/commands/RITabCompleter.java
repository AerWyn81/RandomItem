package fr.aerwyn81.randomitem.commands;

import fr.aerwyn81.randomitem.RandomItem;
import fr.aerwyn81.randomitem.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record RITabCompleter(RandomItem main) implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player player)) {
            return null;
        }

        List<String> list = new ArrayList<>();
        List<String> auto = new ArrayList<>();

        if (args.length == 1) {
            if (PlayerUtils.hasPermission(player, "randomitem.admin")) {
                list.add("help");
                list.add("give");
                list.add("reload");
                list.add("version");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            for (Player target : main.getServer().getOnlinePlayers()) {
                list.add(target.getName());
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            list.add("<number>");
        }

        for (String s : list) {
            if (s.startsWith(args[args.length - 1])) {
                auto.add(s);
            }
        }

        return auto.isEmpty() ? Collections.emptyList() : auto;
    }
}
