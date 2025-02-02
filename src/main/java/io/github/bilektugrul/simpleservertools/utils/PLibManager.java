package io.github.bilektugrul.simpleservertools.utils;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.listeners.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public class PLibManager {

    private static final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public static void loadPacketListener(@NotNull SST plugin) {
        if (Utils.getBoolean("vanish.remove-vanished-players", false) || Utils.getBoolean("one-more-slot.enabled", false)) {
            if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
                manager.removePacketListeners(plugin);
                manager.addPacketListener(new PacketListener(plugin));
            } else {
                plugin.getLogger().warning(ChatColor.RED + "SimpleServerTools requires ProtocolLib for removing players from client player " +
                        "list and changing max player - player count but you don't have ProtocolLib.");
            }
        }
    }

}