package io.github.bilektugrul.simpleservertools.users;

import io.github.bilektugrul.simpleservertools.SST;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserManager {

    private final @NotNull SST plugin;
    private final @NotNull Set<User> userList = new HashSet<>();

    public UserManager(@NotNull SST plugin) {
        this.plugin = plugin;
    }

    public User loadUser(Player p) {
        return loadUser(p.getUniqueId(), p.getName(), true);
    }

    public User loadUser(UUID uuid, String name, boolean keep) {
        YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/players/" + uuid + ".yml"));
        User user = new User(dataFile, uuid, name, plugin);
        if (keep) userList.add(user);
        return user;
    }

    public @Nullable User getUser(Player p) {
        UUID uuid = p.getUniqueId();
        return getUser(uuid);
    }

    public @Nullable User getUser(UUID uuid) {
        return userList.stream()
                .filter(user -> user.getUUID().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public boolean isTeleporting(User user) {
        UserState state = user.getState();
        return state == UserState.TELEPORTING_WARP || state == UserState.TELEPORTING_SPAWN || state == UserState.TELEPORTING_PLAYER || state == UserState.TELEPORTING_HOME;
    }

    public boolean isTeleporting(Player player) {
        User user = getUser(player);
        return user != null && isTeleporting(user);
    }

    public @NotNull Set<User> getUserList() {
        return new HashSet<>(userList);
    }

    public void saveUsers() throws IOException {
        for (User user : userList) {
            user.save();
        }
    }

}