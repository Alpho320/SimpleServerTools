package io.github.bilektugrul.simpleservertools.stuff.teleporting;

import io.github.bilektugrul.simpleservertools.users.User;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportMessage {

    private final @NotNull MessageMode messageMode;
    private final @NotNull User user;

    private String teleportingChat, teleportingActionBar, teleportedChat, teleportedActionBar;
    private String teleportingTitle, teleportingSub, teleportedTitle, teleportedSub;

    public @NotNull TeleportMessage(Player player, @NotNull User user, String mode, @NotNull MessageMode messageMode) {
        this.messageMode = messageMode;
        this.user = user;

        if (messageMode == MessageMode.TELEPORTING) {
            this.teleportingChat = Utils.getMessage(mode + ".teleporting.chat", player);
            this.teleportingActionBar = Utils.getMessage(mode + ".teleporting.actionbar", player);
            this.teleportingTitle = Utils.getMessage(mode + ".teleporting.title.title", player);
            this.teleportingSub = Utils.getMessage(mode + ".teleporting.title.subtitle", player);
        } else {
            this.teleportedChat = Utils.getMessage(mode + ".teleported.chat", player);
            this.teleportedActionBar = Utils.getMessage(mode + ".teleported.actionbar", player);
            this.teleportedTitle = Utils.getMessage(mode + ".teleported.title.title", player);
            this.teleportedSub = Utils.getMessage(mode + ".teleported.title.subtitle", player);
        }

    }

    public @NotNull String getTeleportingChat() {
        return teleportingChat;
    }

    public @NotNull String getTeleportingActionBar() {
        return teleportingActionBar;
    }

    public @NotNull String getTeleportedChat() {
        return teleportedChat;
    }

    public @NotNull String getTeleportedActionBar() {
        return teleportedActionBar;
    }

    public @NotNull String getTeleportingTitle() {
        return teleportingTitle;
    }

    public @NotNull String getTeleportingSub() {
        return teleportingSub;
    }

    public @NotNull String getTeleportedTitle() {
        return teleportedTitle;
    }

    public @NotNull String getTeleportedSub() {
        return teleportedSub;
    }

    public @NotNull MessageMode getMessageMode() {
        return messageMode;
    }

}