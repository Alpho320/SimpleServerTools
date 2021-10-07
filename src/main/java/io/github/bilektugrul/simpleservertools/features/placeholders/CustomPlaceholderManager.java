package io.github.bilektugrul.simpleservertools.features.placeholders;

import io.github.bilektugrul.simpleservertools.SST;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class CustomPlaceholderManager {

    private final @NotNull SST plugin;
    private final @NotNull Set<CustomPlaceholder> placeholderList = new HashSet<>();

    public CustomPlaceholderManager(@NotNull SST plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        placeholderList.clear();
        FileConfiguration config = plugin.getConfig();

        if (config.isConfigurationSection("custom-placeholders")) {
            config.getConfigurationSection("custom-placeholders").getKeys(false)
                    .stream()
                    .map(key -> new CustomPlaceholder(key, colored(config.getString("custom-placeholders." + key))))
                    .forEach(placeholderList::add);
        } else {
            plugin.getLogger().warning("0 Custom placeholders found!");
        }
    }

    public String replacePlaceholders(String in) {
        for (CustomPlaceholder placeholder : placeholderList) {
            in = in.replace("%" + placeholder.name() + "%", placeholder.value());
        }
        return in;
    }

    public String colored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public Set<CustomPlaceholder> getPlaceholderList() {
        return new HashSet<>(placeholderList);
    }

    public CustomPlaceholder getPlaceholder(String name) {
        for (CustomPlaceholder placeholder : placeholderList) {
            if (placeholder.name().equalsIgnoreCase(name)) {
                return placeholder;
            }
        }
        return null;
    }

}