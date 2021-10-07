package io.github.bilektugrul.simpleservertools.commands.gamemode;

import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class GamemodeInfo {

    public @Nullable Player player;
    public @Nullable GameMode gameMode;

    public GamemodeInfo(@Nullable Player player, @Nullable GameMode gameMode) {
        this.player = player;
        this.gameMode = gameMode;
    }

    public GamemodeInfo() {
        this(null, null);
    }

    public @Nullable Player getPlayer() {
        return player;
    }

    public @Nullable GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(@Nullable GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void setPlayer(@Nullable Player player) {
        this.player = player;
    }

    public boolean isCompleted() {
        return player != null && gameMode != null;
    }

    public void apply(CommandSender from) {
        if (isCompleted()) {
            player.setGameMode(gameMode);
            sendMessage(from);
        } else {
            from.sendMessage(Utils.getMessage("gamemode.wrong-arguments", from));
        }
    }

    public void sendMessage(CommandSender from) {
        player.sendMessage(Utils.getMessage("gamemode.changed-other-2", player)
                .replace("%gamemode%", Utils.getMessage("gamemode." + gameMode.name(), from)));
        if (!from.equals(player)) {
            String changedOther = Utils.getMessage("gamemode.changed-other", from)
                    .replace("%gamemode%", Utils.getMessage("gamemode." + gameMode.name(), from))
                    .replace("%other%", player.getName());
            from.sendMessage(changedOther);
        }
    }

}