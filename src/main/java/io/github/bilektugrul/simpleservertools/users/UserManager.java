package io.github.bilektugrul.simpleservertools.users;

import io.github.bilektugrul.simpleservertools.SST;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserManager {

    private final SST plugin;
    private final Set<User> userList = new HashSet<>();

    public UserManager(SST plugin) {
        this.plugin = plugin;
    }

    public User loadUser(Player p) {
        return loadUser(p.getUniqueId(), true);
    }

    public User loadUser(UUID uuid, boolean keep) {
        YamlConfiguration dataFile = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/players/" + uuid + ".yml"));
        User user = new User(dataFile, uuid, plugin);
        if (keep) userList.add(user);
        return user;
    }

    public User getUser(Player p) {
        return getUser(p.getUniqueId(), false);
    }

    public User getUser(Player p, boolean keep) {
        UUID uuid = p.getUniqueId();
        return getUser(uuid, keep);
    }

    public User getUser(UUID uuid, boolean keep) {
        for (User user : userList) {
            if (user.getUUID().equals(uuid)) {
                return user;
            }
        }
        return loadUser(uuid, keep);
    }

    public boolean isTeleporting(User user) {
        UserState state = user.getState();
        return state == UserState.TELEPORTING || state == UserState.TELEPORTING_SPAWN || state == UserState.TELEPORTING_PLAYER || state == UserState.TELEPORTING_HOME;
    }

    public boolean isTeleporting(Player player) {
        User user = getUser(player, false);
        if (user != null) {
            return isTeleporting(user);
        }
        return false;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void saveUsers() throws IOException {
        for (User user : userList) {
            user.save();
        }
    }

}
