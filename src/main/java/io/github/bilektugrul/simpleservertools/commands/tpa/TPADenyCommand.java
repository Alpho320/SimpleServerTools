package io.github.bilektugrul.simpleservertools.commands.tpa;

import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.features.tpa.TPAManager;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPADenyCommand implements CommandExecutor {

    private final @NotNull TPAManager tpaManager;

    public TPADenyCommand(@NotNull SST plugin) {
        this.tpaManager = plugin.getTPAManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("sst.tpa") && sender instanceof Player p) {
            if (args.length == 1 && tpaManager.isPresent(p)) {
                Player reqSender = Bukkit.getPlayer(args[0]);
                if (reqSender != null && !reqSender.equals(sender) && tpaManager.isPresent(p, reqSender)) {
                    tpaManager.remove(reqSender, p);
                    reqSender.sendMessage(Utils.getMessage("tpa.request-denied", reqSender)
                            .replace("%teleporting%", p.getName()));
                    p.sendMessage(Utils.getMessage("tpa.request-denied-2", p)
                            .replace("%requester%", reqSender.getName()));
                } else {
                    p.sendMessage(Utils.getMessage("tpa.went-wrong", p));
                }
            } else {
                p.sendMessage(Utils.getMessage("tpa.no-request", p));
            }
        } else {
            Utils.noPermission(sender);
        }
        return true;
    }
}