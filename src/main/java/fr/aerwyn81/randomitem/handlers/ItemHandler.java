package fr.aerwyn81.randomitem.handlers;

import fr.aerwyn81.randomitem.RandomItem;
import fr.aerwyn81.randomitem.utils.AssetsUtils;
import fr.aerwyn81.randomitem.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public record ItemHandler(RandomItem main) {

    public String giveRandomItem(Player player) {
        String message;
        ItemStack item = ItemUtils.getRandomItemStack(main.getConfigHandler().getMaterialsAvailable());

        String itemName = AssetsUtils.getMaterialName(item.getType());

        if (item.getType() == Material.SPAWNER) {
            itemName = convertToRandomSpawner(item);
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation().clone().add(.0f, .1f, .0f), item);

            message = main.getLanguageHandler().getMessage("Messages.PlayerItemRewardFull")
                    .replaceAll("%item%", itemName);

            if (message.trim().length() > 0) {
                player.sendMessage(message);
            }

            return itemName;
        }

        player.getInventory().addItem(item);

        message = main.getLanguageHandler().getMessage("Messages.PlayerItemReward")
                .replaceAll("%item%", itemName);

        if (message.trim().length() > 0) {
            player.sendMessage(message);
        }

        return itemName;
    }

    private String convertToRandomSpawner(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        BlockStateMeta blockState = (BlockStateMeta) itemMeta;
        CreatureSpawner spawner = (CreatureSpawner) blockState.getBlockState();

        EntityType entity = ItemUtils.getRandomEntityType();
        String spawnerName = AssetsUtils.getFullSpawnerName(entity.name());

        spawner.setSpawnedType(entity);

        blockState.setBlockState(spawner);
        item.setItemMeta(blockState);

        itemMeta.setDisplayName(spawnerName);
        item.setItemMeta(itemMeta);

        return spawnerName;
    }
}
