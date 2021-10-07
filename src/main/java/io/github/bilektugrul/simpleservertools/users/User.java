package io.github.bilektugrul.simpleservertools.users;

import io.github.bilektugrul.simpleservertools.SST;
import io.github.bilektugrul.simpleservertools.features.homes.Home;
import io.github.bilektugrul.simpleservertools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class User {

    private final @NotNull SST plugin;
    private final @NotNull UUID uuid;
    private final @NotNull String name;
    private @NotNull UserState state;
    private boolean isGod;
    private boolean isAfk;
    private final @NotNull YamlConfiguration data;

    private final @NotNull List<String> tpaBlockedPlayers = new ArrayList<>();
    private final @NotNull List<String> msgBlockedPlayers = new ArrayList<>();
    private final @NotNull Set<Home> homes = new HashSet<>();

    public User(YamlConfiguration data, @NotNull UUID uuid, @NotNull String name, @NotNull SST plugin) {
        this.data = data;
        this.uuid = uuid;
        this.name = name;
        this.state = UserState.PLAYING;

        data.set("lastKnownName", name);

        if (!data.contains("accepting-tpa")) data.set("accepting-tpa", true);
        if (!data.contains("accepting-msg")) data.set("accepting-msg", true);

        tpaBlockedPlayers.addAll(data.getStringList("tpa-blocked-players"));
        msgBlockedPlayers.addAll(data.getStringList("msg-blocked-players"));

        if (data.isConfigurationSection("homes")) {
            data.getConfigurationSection("homes").getKeys(false)
                    .stream()
                    .map(homeName -> new Home(homeName, Utils.getLocation(data, "homes." + homeName + ".location")))
                    .forEach(homes::add);
        }

        this.plugin = plugin;
    }

    public UUID getUUID() {
        return uuid;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull UserState getState() {
        return state;
    }

    public void setState(@NotNull UserState newState) {
        state = newState;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isGod() {
        return isGod;
    }

    public boolean isAfk() {
        return isAfk;
    }

    public List<String> getMsgBlockedPlayers() {
        return new ArrayList<>(msgBlockedPlayers);
    }

    public List<String> getTPABlockedPlayers() {
        return new ArrayList<>(tpaBlockedPlayers);
    }

    public Set<Home> getHomes() {
        return new HashSet<>(homes);
    }

    public Home getHomeByName(String name) {
        return homes.stream()
                .filter(home -> home.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean createHome(Home home) {
        if (getHomeByName(home.name()) == null) {
            homes.add(home);
            return true;
        }
        return false;
    }

    public void deleteHome(String name) {
        homes.removeIf(home -> home.name().equalsIgnoreCase(name));
        data.set("homes." + name, null);
    }

    public String readableHomeList() {
        if (!homes.isEmpty()) {
            List<String> homes = this.homes.stream()
                    .map(Home::name)
                    .collect(Collectors.toList());
            return String.join(", ", homes);
        } else {
            return Utils.getMessage("homes.no-home", null);
        }
    }

    public boolean toggleTPABlock(String name) {
        if (tpaBlockedPlayers.contains(name)) {
            tpaBlockedPlayers.remove(name);
            return false;
        } else {
            tpaBlockedPlayers.add(name);
            return true;
        }
    }

    public boolean toggleMsgBlock(String name) {
        if (msgBlockedPlayers.contains(name)) {
            msgBlockedPlayers.remove(name);
            return false;
        } else {
            msgBlockedPlayers.add(name);
            return true;
        }
    }

    public void setGod(boolean isGod) {
        this.isGod = isGod;
    }

    public void setAfk(boolean isAfk) {
        this.isAfk = isAfk;
    }

    public boolean isAvailable() {
        return state == UserState.PLAYING;
    }

    public boolean isAcceptingTPA() {
        return data.getBoolean("accepting-tpa");
    }

    public boolean isBlockedTPAsFrom(String name) {
        return tpaBlockedPlayers.contains(name);
    }

    public void setAcceptingTPA(boolean acceptingTPA) {
        data.set("accepting-tpa", acceptingTPA);
    }

    public boolean isAcceptingMsg() {
        return data.getBoolean("accepting-msg");
    }

    public boolean isBlockedMsgsFrom(String name) {
        return msgBlockedPlayers.contains(name);
    }

    public void setAcceptingMsg(boolean acceptingMsg) {
        data.set("accepting-msg", acceptingMsg);
    }

    public void save() throws IOException {
        data.set("msg-blocked-players", msgBlockedPlayers);
        data.set("tpa-blocked-players", tpaBlockedPlayers);
        homes.forEach(home -> data.set("homes." + home.name() + ".location", home.location().clone()));

        data.save(new File(plugin.getDataFolder() + "/players/" + uuid + ".yml"));
    }

}