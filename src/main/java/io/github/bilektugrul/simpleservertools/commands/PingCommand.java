package io.github.bilektugrul.simpleservertools.commands;

import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("sst.ping")) {
            Player pingPlayer = null;
            if (args.length >= 1) {
                pingPlayer = Bukkit.getPlayer(args[0]);
            } else if (sender instanceof Player) {
                pingPlayer = (Player) sender;
            }
            if (pingPlayer != null) {
                if (pingPlayer.equals(sender)) {
                    pingPlayer.sendMessage(Utils.getString("other-messages.ping.message", pingPlayer)
                            .replace("%ping%", Integer.toString(pingPlayer.spigot().getPing())));
                } else {
                    sender.sendMessage(Utils.getString("other-messages.ping.message-other", sender)
                            .replace("%ping%", Integer.toString(pingPlayer.spigot().getPing())));
                }
            } else {
                sender.sendMessage(Utils.getString("other-messages.ping.not-found", sender));
            }
        } else {
            sender.sendMessage(Utils.getString("no-permission", sender));
        }
        return true;
    }

}
