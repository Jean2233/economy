package com.github.jean2233.economy.data.user;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class UserRegistry {
    @Getter private final Set<User> users = Sets.newHashSet();

    public User get(@NotNull String name) {
        return this.find($ -> $.getName().equalsIgnoreCase(name)).orElse(null);
    }

    public User get(@NotNull UUID id) {
        return this.find($ -> $.getUniqueId().equals(id)).orElse(null);
    }

    public User get(@NotNull Player player) {
        return this.get(player.getUniqueId());
    }

    public void register(@NotNull User user) {
        this.users.add(user);
    }

    public void unregister(@NotNull User user) {
        this.users.remove(user);
    }

    public Optional<User> find(@NotNull Predicate<User> $) {
        return this.users.stream().filter($).findFirst();
    }
}
