package io.github.bilektugrul.simpleservertools.features.maintenance;

import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import me.despical.commons.configuration.ConfigUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class MaintenanceManager {

    private @NotNull FileConfiguration maintenanceFile;

    private String reason;
    private String fullyClosedMessage;
    private boolean isInMaintenance;
    private boolean isFullyClosed;

    private final @NotNull SST plugin;

    public MaintenanceManager(@NotNull SST plugin) {
        this.plugin = plugin;
        reload();
    }

    public boolean isInMaintenance() {
        return isInMaintenance;
    }

    public void toggleMaintenance() {
        isInMaintenance = !isInMaintenance;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setFullyClosed(boolean fullyClosed) {
        this.isFullyClosed = fullyClosed;
    }

    public boolean isFullyClosed() {
        return isFullyClosed;
    }

    public String getReason() {
        return reason;
    }

    public String getFullyClosedMessage() {
        return fullyClosedMessage;
    }

    public void reload() {
        maintenanceFile = ConfigUtils.getConfig(plugin, "maintenance");
        isInMaintenance = maintenanceFile.getBoolean("maintenance.in-maintenance");
        String lastReason = Utils.getString(maintenanceFile, "maintenance.last-reason", null, false);
        reason = lastReason.isEmpty()
                ? Utils.getString(maintenanceFile, "maintenance.default-reason", null)
                : lastReason;
        fullyClosedMessage = Utils.getString(maintenanceFile, "maintenance.fully-closed-message", null, false);
    }

    public void save() {
        maintenanceFile.set("maintenance.last-reason", reason);
        maintenanceFile.set("maintenance.in-maintenance", isInMaintenance);
        ConfigUtils.saveConfig(plugin, maintenanceFile, "maintenance");
    }

    public @NotNull FileConfiguration getMaintenanceFile() {
        return maintenanceFile;
    }

}