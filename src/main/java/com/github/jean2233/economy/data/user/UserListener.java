package com.github.jean2233.economy.data.user;

import com.github.jean2233.economy.EconomyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record UserListener(EconomyPlugin plugin) implements Listener {
    @EventHandler
    public void handle(PlayerJoinEvent event) {
        plugin.getController().loadOrCreate(event.getPlayer());
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        plugin.getController().removeAndSave(event.getPlayer());
    }
}
