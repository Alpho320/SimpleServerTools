package io.github.bilektugrul.simpleservertools.stuff.teleporting;

import io.github.bilektugrul.simpleservertools.stuff.CancelMode;
import org.jetbrains.annotations.NotNull;

public class TeleportSettings {

    private int time;
    private CancelMode cancelMoveMode, cancelDamageMode, cancelCommandsMode;
    private boolean blockMove, cancelTeleportOnMove, blockDamage, cancelTeleportOnDamage, staffBypassTime, blockCommands;

    public @NotNull TeleportSettings setTime(int time) {
        this.time = time;
        return this;
    }

    public @NotNull TeleportSettings setCancelMoveMode(CancelMode cancelMoveMode) {
        this.cancelMoveMode = cancelMoveMode;
        return this;
    }

    public @NotNull TeleportSettings setCancelDamageMode(CancelMode cancelDamageMode) {
        this.cancelDamageMode = cancelDamageMode;
        return this;
    }

    public @NotNull TeleportSettings setCancelCommandsMode(CancelMode cancelCommandsMode) {
        this.cancelCommandsMode = cancelCommandsMode;
        return this;
    }

    public @NotNull TeleportSettings setBlockMove(boolean blockMove) {
        this.blockMove = blockMove;
        return this;
    }

    public @NotNull TeleportSettings setCancelTeleportOnMove(boolean cancelTeleportOnMove) {
        this.cancelTeleportOnMove = cancelTeleportOnMove;
        return this;
    }

    public @NotNull TeleportSettings setBlockDamage(boolean blockDamage) {
        this.blockDamage = blockDamage;
        return this;
    }

    public @NotNull TeleportSettings setCancelTeleportOnDamage(boolean cancelTeleportOnDamage) {
        this.cancelTeleportOnDamage = cancelTeleportOnDamage;
        return this;
    }

    public @NotNull TeleportSettings setStaffBypassTime(boolean staffBypassTime) {
        this.staffBypassTime = staffBypassTime;
        return this;
    }

    public @NotNull TeleportSettings setBlockCommands(boolean blockCommands) {
        this.blockCommands = blockCommands;
        return this;
    }

    public int getTime() {
        return time;
    }

    public boolean doesBlockMove() {
        return blockMove;
    }

    public boolean doesCancelTeleportOnMove() {
        return cancelTeleportOnMove;
    }

    public @NotNull CancelMode getCancelMoveMode() {
        return cancelMoveMode;
    }

    public boolean doesBlockDamage() {
        return blockDamage;
    }

    public boolean doesCancelTeleportOnDamage() {
        return cancelTeleportOnDamage;
    }

    public @NotNull CancelMode getCancelDamageMode() {
        return cancelDamageMode;
    }

    public boolean doesStaffBypassTime() {
        return staffBypassTime;
    }

    public boolean doesBlockCommands() {
        return blockCommands;
    }

    public @NotNull CancelMode getCancelCommandsMode() {
        return cancelCommandsMode;
    }

}