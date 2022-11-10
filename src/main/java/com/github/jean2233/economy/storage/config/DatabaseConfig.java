package com.github.jean2233.economy.storage.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConfig {
    private static final Map<String, String> PROPERTIES = new HashMap() {{
        put("autoReconnect", "true");
        put("useSSL", "false");
        put("useUnicode", "true");
        put("rewriteBatchedStatements", "true");
        put("jdbcCompliantTruncation", "false");
        put("cachePrepStmts", "true");
        put("prepStmtCacheSize", "275");
        put("prepStmtCacheSqlLimit", "2048");
        put("characterEncoding", "utf8");
        put("encoding", "UTF-8");
    }};

    private static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 1;

    @SneakyThrows
    public HikariDataSource getHikariDataSource(String poolId, ConfigurationSection section) {
        final HikariConfig config = new HikariConfig();

        config.setJdbcUrl(section.getString("connection-url"));

        config.setUsername(section.getString("username"));
        config.setPassword(section.getString("password"));

        config.setPoolName(poolId);

        return new HikariDataSource(buildProperties(config));
    }

    private HikariConfig buildProperties(HikariConfig config) {
        config.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
        config.setConnectionTestQuery("SELECT 1");

        PROPERTIES.forEach(config::addDataSourceProperty);

        return config;
    }
}
