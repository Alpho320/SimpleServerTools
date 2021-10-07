package io.github.bilektugrul.simpleservertools;

import io.github.bilektugrul.simpleservertools.users.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Logger;

public class AsyncUserSaveThread extends BukkitRunnable {

    private final @NotNull SST plugin;
    private final @NotNull Logger logger;
    private final @NotNull UserManager userManager;

    public AsyncUserSaveThread(@NotNull SST plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.userManager = plugin.getUserManager();
        start();
    }

    public void start() {
        logger.info(ChatColor.GREEN + "Async user saving thread is starting...");
        int i = plugin.getConfig().getInt("auto-save-interval");
        runTaskTimerAsynchronously(plugin, 2400, (i * 60L) * 20);
    }

    @Override
    public void run() {
        try {
            userManager.saveUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}