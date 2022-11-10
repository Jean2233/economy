package com.github.jean2233.economy.storage.service;

import com.github.jean2233.economy.storage.config.DatabaseConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseService {
    @Getter private HikariDataSource source;

    public DatabaseService(Plugin plugin, String poolId) {
        final FileConfiguration config = plugin.getConfig();
        final ConfigurationSection section = config.getConfigurationSection("mysql");
        if (section == null) return;

        this.source = new DatabaseConfig().getHikariDataSource(poolId, section);
    }

    public void init(String query) {
        try (PreparedStatement statement = source.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void disconnect() {
        if (source != null && !source.isClosed()) {
            source.close();
        }
    }
}
