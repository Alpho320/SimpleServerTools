package io.github.bilektugrul.simpleservertools.features.announcements;

import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import io.github.bilektugrul.simpleservertools.utils.VaultManager;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AsyncAnnouncementTask extends BukkitRunnable {

    private final @NotNull AnnouncementManager announcementManager;
    private @Nullable VaultManager vaultManager = null;
    private final @NotNull SST plugin;
    private final @NotNull AnnouncementMode mode;
    private int next = 0;

    private boolean started = false;

    private final @NotNull List<Announcement> list;

    public AsyncAnnouncementTask(@NotNull AnnouncementManager announcementManager, SST plugin, @NotNull AnnouncementMode mode) {
        this.announcementManager = announcementManager;
        if (plugin.isPermManagerReady()) {
            this.vaultManager = plugin.getVaultManager();
        } else {
            plugin.getLogger().warning("Vault is not installed. Group based announcements will not work.");
        }
        this.plugin = plugin;
        this.mode = mode;
        this.list = announcementManager.getAnnouncements();
    }

    public void start() {
        plugin.getLogger().info("Async announcement thread starting...");
        started = true;
        int i = announcementManager.getAnnouncementsFile().getInt("announcements.time", 120);
        long time = i * 20L;
        runTaskTimerAsynchronously(plugin, time, time);
    }

    @Override
    public void run() {
        if (mode == AnnouncementMode.ORDERED) {
            announce(list.get(next++));
            if (next >= list.size()) {
                next = 0;
            }
        } else {
            int random = new Random().nextInt(list.size());
            announce(list.get(random));
        }
    }

    public void announce(Announcement announcement) {
        String content = announcement.content();
        AnnouncementType type = announcement.type();
        if (type == AnnouncementType.NONE) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(Utils.replacePlaceholders(content, p, true));
            }
        } else if (type == AnnouncementType.GROUP && plugin.isPermManagerReady()) {
            Permission provider = vaultManager.getPermissionProvider();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Arrays.stream(provider.getPlayerGroups(p)).anyMatch(announcement.group()::equalsIgnoreCase)) {
                    p.sendMessage(Utils.replacePlaceholders(content, p, true));
                }
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(announcement.permission())) {
                    p.sendMessage(Utils.replacePlaceholders(content, p, true));
                }
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

}