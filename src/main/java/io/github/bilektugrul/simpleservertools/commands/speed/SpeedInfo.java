package io.github.bilektugrul.simpleservertools.commands.speed;

import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpeedInfo {

    private Player player;
    private CommandSender executor;
    private float speed;
    private SpeedMode mode;

    public @NotNull SpeedInfo setExecutor(CommandSender executor) {
        this.executor = executor;
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public SpeedMode getMode() {
        return mode;
    }

    public @NotNull SpeedInfo setPlayer(Player player) {
        this.player = player;
        return this;
    }

    public @NotNull SpeedInfo setMode(SpeedMode mode) {
        this.mode = mode;
        return this;
    }

    public @NotNull SpeedInfo setSpeed(String speed) {
        float result = 0.2f;
        try {
            result = Float.parseFloat(speed);
        } catch (NumberFormatException ignored) {}
        if (result > 1) result = 1;
        this.speed = result;
        return this;
    }

    public @NotNull SpeedMode matchMode() {
        return player.isFlying() ? SpeedMode.FLY : SpeedMode.WALK;
    }

    public void apply() {
        if (player == null) {
            executor.sendMessage(Utils.getMessage("speed.not-found", executor));
            return;
        }

        boolean isSame = player.equals(executor);
        if (!isSame && !executor.hasPermission("sst.speed.others")) {
            executor.sendMessage(Utils.getMessage("no-permission", executor));
            return;
        }

        if (mode == null)
            mode = matchMode();
        if (mode == SpeedMode.WALK)
            player.setWalkSpeed(speed);
        else
            player.setFlySpeed(speed);

        String modeString = Utils.getMessage("speed.modes." + mode.name());
        String speedString = String.valueOf(speed);

        player.sendMessage(Utils.getMessage("speed.changed", player)
                .replace("%mode%", modeString)
                .replace("%newspeed%", speedString));
        if (!isSame) {
            executor.sendMessage(Utils.getMessage("speed.changed-other", player)
                    .replace("%mode%", modeString)
                    .replace("%newspeed%", speedString)
                    .replace("%other%", player.getName()));
        }
    }

}