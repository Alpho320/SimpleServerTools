package io.github.bilektugrul.simpleservertools.features.announcements;

public class Announcement {

    private final AnnouncementType type;
    private final String permission;
    private final String group;
    private final String content;

    public Announcement(AnnouncementType type, String permission, String group, String content) {
        this.type = type;
        this.permission = permission;
        this.group = group;
        this.content = content;
    }

    public Announcement(AnnouncementType type, String group, String content) {
        this(type, null, group, content);
    }

    public Announcement(String content, String permission, AnnouncementType type) {
        this(type, permission, null, content);
    }

    public Announcement(String content) {
        this(AnnouncementType.NONE, null, null, content);
    }

    public AnnouncementType getType() {
        return type;
    }

    public String getPermission() {
        return permission;
    }

    public String getGroup() {
        return group;
    }

    public String getContent() {
        return content;
    }

}
