package fr.aerwyn81.randomitem.handlers;

import fr.aerwyn81.randomitem.RandomItem;
import fr.aerwyn81.randomitem.utils.AssetsUtils;
import fr.aerwyn81.randomitem.utils.MaterialUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public record ItemHandler(RandomItem main) {

    public ItemStack giveRandomItem(Player player) {
        String message;
        ItemStack item = MaterialUtils.getRandomItemStack(main.getConfigHandler().getMaterialsAvailable());

        String itemName = AssetsUtils.getMaterialName(item.getType());

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation().clone().add(.0f, .1f, .0f), item);

            message = main.getLanguageHandler().getMessage("Messages.PlayerItemRewardFull")
                    .replaceAll("%item%", itemName);

            if (message.trim().length() > 0) {
                player.sendMessage(message);
            }

            return item;
        }

        player.getInventory().addItem(item);

        message = main.getLanguageHandler().getMessage("Messages.PlayerItemReward")
                .replaceAll("%item%", itemName);

        if (message.trim().length() > 0) {
            player.sendMessage(message);
        }

        return item;
    }
}
