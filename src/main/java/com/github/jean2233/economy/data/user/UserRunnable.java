package com.github.jean2233.economy.data.user;

import com.github.jean2233.economy.EconomyPlugin;

import java.util.Set;

public record UserRunnable(EconomyPlugin plugin) implements Runnable {
    @Override
    public void run() {
        final UserRegistry registry = plugin.getRegistry();
        final Set<User> users = registry.getUsers();

        users.stream().filter(user -> user.getWallet().isDirty()).forEach(user -> {
            plugin.getDatabase().update(user);
            user.getWallet().setDirty(false);
        });
    }
}
