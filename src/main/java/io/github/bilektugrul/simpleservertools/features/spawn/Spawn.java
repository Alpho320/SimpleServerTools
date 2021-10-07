package io.github.bilektugrul.simpleservertools.features.spawn;

import io.github.bilektugrul.simpleservertools.stuff.teleporting.TeleportSettings;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Spawn {

    private Location location;
    private final boolean permRequired;
    private final @NotNull TeleportSettings settings;

    public Spawn(Location location, boolean permRequired, TeleportSettings settings) {
        this.location = location;
        this.permRequired = permRequired;
        this.settings = settings;
    }

    public Location getLocation() {
        return location;
    }

    public boolean getPermRequire() {
        return permRequired;
    }

    public @NotNull TeleportSettings getSettings() {
        return settings;
    }

    public void setLocation(Location loc) {
        this.location = loc;
    }

}