package io.github.bilektugrul.simpleservertools.features.announcements;

import io.github.bilektugrul.simpleservertools.SST;
import me.despical.commons.configuration.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementManager {

    private final @NotNull SST plugin;
    private @Nullable AsyncAnnouncementTask announcementTask;
    private @Nullable FileConfiguration announcementsFile;
    private final @NotNull List<Announcement> announcements = new ArrayList<>();

    public AnnouncementManager(@NotNull SST plugin) {
        this.plugin = plugin;
        reload();
    }

    public void load() {
        announcements.clear();
        String start = "announcements.announcements";
        for (String key : announcementsFile.getConfigurationSection(start).getKeys(false)) {
            String full = start + "." + key + ".";
            String type = announcementsFile.getString(full + "filter.type", "NONE");
            if (type.equalsIgnoreCase("GROUP")) {
                String group = announcementsFile.getString(full + "filter.group");
                String content = announcementsFile.getString(full + "content");
                announcements.add(new Announcement(AnnouncementType.GROUP, group, content));
            } else if (type.equalsIgnoreCase("PERMISSION")) {
                String permission = announcementsFile.getString(full + "filter.permission");
                String content = announcementsFile.getString(full + "content");
                announcements.add(new Announcement(content, permission, AnnouncementType.PERMISSION));
            } else if (type.equalsIgnoreCase("NONE")) {
                String content = announcementsFile.getString(full + "content");
                announcements.add(new Announcement(content));
            }
        }
    }

    public void reload() {
        announcementsFile = ConfigUtils.getConfig(plugin, "announcements");
        load();
        if (announcementTask != null && announcementTask.isStarted()) {
            announcementTask.cancel();
            announcementTask = null;
        }
        check();
    }

    public void check() {
        boolean enabled = announcementsFile.getBoolean("announcements.enabled");
        if (enabled) {
            announcementTask = new AsyncAnnouncementTask(this, plugin, matchMode());
            announcementTask.start();
        } else if (announcementTask != null && announcementTask.isStarted()) {
            announcementTask.cancel();
            announcementTask = null;
        }
    }

    public AnnouncementMode matchMode() {
        return (announcementsFile.getBoolean("announcements.random")) ? AnnouncementMode.RANDOM : AnnouncementMode.ORDERED;
    }

    public List<Announcement> getAnnouncements() {
        return new ArrayList<>(announcements);
    }

    public FileConfiguration getAnnouncementsFile() {
        return announcementsFile;
    }

    public @Nullable AsyncAnnouncementTask getAnnouncementTask() {
        return announcementTask;
    }

}