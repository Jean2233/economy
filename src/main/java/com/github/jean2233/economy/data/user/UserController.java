package com.github.jean2233.economy.data.user;

import com.github.jean2233.economy.EconomyPlugin;
import org.bukkit.entity.Player;

public record UserController(EconomyPlugin plugin) {
    public void loadOrCreate(Player player) {
        final UserRegistry registry = plugin.getRegistry();
        final UserDatabase database = plugin.getDatabase();

        final User find = database.find(player.getUniqueId().toString());
        if (find != null) {
            registry.register(find);
            return;
        }

        final User user = new User(player.getUniqueId(), player.getName());

        database.insert(user);
        registry.register(user);
    }

    public void removeAndSave(Player player) {
        final UserRegistry registry = plugin.getRegistry();
        final UserDatabase database = plugin.getDatabase();

        final User user = registry.get(player);
        if (user == null) return;

        database.update(user);
        registry.unregister(user);
    }
}
