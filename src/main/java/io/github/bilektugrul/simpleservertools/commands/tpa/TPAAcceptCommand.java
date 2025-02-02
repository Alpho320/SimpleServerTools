package io.github.bilektugrul.simpleservertools.commands.tpa;

import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.features.tpa.TPAInfo;
import io.github.bilektugrul.simpleservertools.features.tpa.TPAManager;
import io.github.bilektugrul.simpleservertools.stuff.teleporting.Mode;
import io.github.bilektugrul.simpleservertools.stuff.teleporting.TeleportMode;
import io.github.bilektugrul.simpleservertools.users.UserManager;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPAAcceptCommand implements CommandExecutor {

    private final @NotNull TPAManager tpaManager;
    private final @NotNull UserManager userManager;

    public TPAAcceptCommand(@NotNull SST plugin) {
        this.tpaManager = plugin.getTPAManager();
        this.userManager = plugin.getUserManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("sst.tpa") && sender instanceof Player p) {
            if (args.length == 1 && tpaManager.isPresent(p)) {
                Player reqSender = Bukkit.getPlayer(args[0]);
                if (reqSender != null && !reqSender.equals(sender) && tpaManager.isPresent(p, reqSender) && userManager.getUser(reqSender).isAvailable()) {
                    TPAInfo info = new TPAInfo(reqSender, p);
                    TeleportMode mode = new TeleportMode(Mode.TPA, info);
                    reqSender.sendMessage(Utils.getMessage("tpa.request-accepted", reqSender)
                            .replace("%teleporting%", p.getName()));
                    p.sendMessage(Utils.getMessage("tpa.request-accepted-2", p)
                            .replace("%requester%", reqSender.getName()));
                    tpaManager.teleport(reqSender, p, p.getLocation(), mode);
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