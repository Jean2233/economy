package com.github.jean2233.economy.command;

import com.github.jean2233.economy.EconomyPlugin;
import com.github.jean2233.economy.data.user.User;
import com.github.jean2233.economy.util.Formats;
import com.github.jean2233.economy.util.RandomGenerator;
import com.google.common.collect.Maps;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public record EarnCommand(EconomyPlugin plugin) {
    private static final Map<UUID, Long> DELAY_MAP = Maps.newHashMap();

    @Command(name = "earn")
    public void handle(Context<Player> context) {
        final Player sender = context.getSender();

        final long delay = DELAY_MAP.getOrDefault(sender.getUniqueId(), 0L);
        if (System.currentTimeMillis() < delay) {
            sender.sendMessage("§cYou need to wait to earn again.");
            return;
        }

        final User user = plugin.getRegistry().get(sender);
        if (user == null) return;

        final double generate = RandomGenerator.generate(1, 5);
        user.getWallet().deposit(generate);

        sender.sendMessage("§cYou earned §a" + Formats.apply(generate) + "§c.");

        DELAY_MAP.put(sender.getUniqueId(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1));
    }
}
