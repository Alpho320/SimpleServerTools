package io.github.bilektugrul.simpleservertools.features.spawn;

import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.stuff.CancelMode;
import io.github.bilektugrul.simpleservertools.stuff.teleporting.Mode;
import io.github.bilektugrul.simpleservertools.stuff.teleporting.TeleportManager;
import io.github.bilektugrul.simpleservertools.stuff.teleporting.TeleportMode;
import io.github.bilektugrul.simpleservertools.stuff.teleporting.TeleportSettings;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import me.despical.commons.configuration.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

public class SpawnManager {

    private Spawn spawn;
    private final @NotNull SST plugin;
    private @NotNull FileConfiguration spawnFile;
    private final @NotNull TeleportManager teleportManager;

    public SpawnManager(@NotNull SST plugin) {
        this.plugin = plugin;
        this.teleportManager = plugin.getTeleportManager();
        reloadSpawn();
    }

    public Spawn getSpawn() {
        return spawn;
    }

    public void setSpawn(Location loc) {
        spawn = new Spawn(loc, getPermRequired(), getSettings());
        saveSpawn();
    }

    public void loadSpawn(boolean force) {
        Location loc = null;
        try {
            loc = (Location) spawnFile.get("spawn.location");
        } catch (NullPointerException ignored) {
            plugin.getLogger().log(Level.WARNING, "Spawn location does not exist. Spawn will not be created.");
        }
        if (loc != null) {
            if (force) {
                spawn = null;
            }
            setSpawn(loc);
        }
    }

    public void reloadSpawn() {
        this.spawnFile = ConfigUtils.getConfig(plugin, "spawn");
        loadSpawn(true);
    }

    public TeleportSettings getSettings() {
        if (!isPresent()) {
            final int time = spawnFile.getInt("spawn.command.teleport.time");

            final boolean blockMove = spawnFile.getBoolean("spawn.command.teleport.cancel-when-move.settings.block-move");
            final boolean blockCommands = spawnFile.getBoolean("spawn.command.teleport.block-commands.enabled");
            final boolean cancelTeleportOnMove = spawnFile.getBoolean("spawn.command.teleport.cancel-when-move.settings.cancel-teleport");
            final boolean blockDamage = spawnFile.getBoolean("spawn.command.teleport.cancel-damage.settings.block-damage");
            final boolean cancelTeleportOnDamage = spawnFile.getBoolean("spawn.command.teleport.cancel-damage.settings.cancel-teleport");
            final boolean staffBypassTime = spawnFile.getBoolean("spawn.command.teleport.staff-bypass-time");

            final CancelMode cancelMoveMode = CancelMode.valueOf(spawnFile.getString("spawn.command.teleport.cancel-when-move.mode"));
            final CancelMode cancelDamageMode = CancelMode.valueOf(spawnFile.getString("spawn.command.teleport.cancel-damage.mode"));
            final CancelMode cancelCommandsMode = CancelMode.valueOf(spawnFile.getString("spawn.command.teleport.block-commands.mode"));

            return new TeleportSettings()
                    .setTime(time)
                    .setBlockMove(blockMove)
                    .setBlockCommands(blockCommands)
                    .setCancelTeleportOnMove(cancelTeleportOnMove)
                    .setBlockDamage(blockDamage)
                    .setCancelTeleportOnDamage(cancelTeleportOnDamage)
                    .setStaffBypassTime(staffBypassTime)
                    .setCancelMoveMode(cancelMoveMode)
                    .setCancelDamageMode(cancelDamageMode)
                    .setCancelCommandsMode(cancelCommandsMode);
        }
        return spawn.getSettings();
    }

    public void saveSpawn() {
        if (isPresent()) {
            plugin.getLogger().info(ChatColor.GREEN + "Saving spawn...");
            spawnFile.set("spawn.location", spawn.getLocation());
            ConfigUtils.saveConfig(plugin, spawnFile, "spawn");
        }
    }

    public boolean getPermRequired() {
        return spawn == null
                ? spawnFile.getBoolean("spawn.command.permission-required")
                : spawn.getPermRequire();
    }

    public boolean isPresent() {
        return spawn != null;
    }

    public boolean isEnabled() {
        return spawnFile.getBoolean("spawn.enabled");
    }

    public void teleport(Player player, boolean direct) {
        if (isEnabled() && isPresent()) {
            Location loc = spawn.getLocation();
            if (direct) {
                Bukkit.getScheduler().runTask(plugin, () -> player.teleport(loc));
            } else {
                TeleportMode mode = new TeleportMode(Mode.SPAWN, spawn);
                teleportManager.teleport(player, loc, mode, getSettings());
            }
        }
    }

    public void sendWarnIfEnabled(CommandSender sender) {
        if (spawnFile.getBoolean("spawn.send-warning-if-not-enabled"))
            sender.sendMessage(Utils.getMessage("spawn.not-enabled", sender));
    }

    public @NotNull FileConfiguration getSpawnFile() {
        return spawnFile;
    }

}