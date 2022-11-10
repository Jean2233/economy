package com.github.jean2233.economy;

import com.github.jean2233.economy.command.BalanceCommand;
import com.github.jean2233.economy.command.EarnCommand;
import com.github.jean2233.economy.command.GiveCommand;
import com.github.jean2233.economy.command.SetCommand;
import com.github.jean2233.economy.data.user.*;
import lombok.Getter;
import lombok.SneakyThrows;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

@Getter
public final class EconomyPlugin extends JavaPlugin {
    private UserRegistry registry;
    private UserController controller;
    private UserDatabase database;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.createDefaultRegistry();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> controller.removeAndSave(player));

        database.getService().disconnect();
    }

    @SneakyThrows
    private void createDefaultRegistry() {
        this.registry = new UserRegistry();
        this.controller = new UserController(this);
        this.database = new UserDatabase(this);

        final PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new UserListener(this), this);

        final BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new UserRunnable(this), 20L * 60L * 10L, 20L * 60L * 10L);

        new BukkitFrame(this).registerCommands(
          new BalanceCommand(this),
          new EarnCommand(this),
          new GiveCommand(this),
          new SetCommand(this)
        );
    }
}
