package com.github.jean2233.economy.command;

import com.github.jean2233.economy.EconomyPlugin;
import com.github.jean2233.economy.data.user.User;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

public record BalanceCommand(EconomyPlugin plugin) {
    @Command(name = "balance", aliases = {"bal"})
    public void handle(Context<Player> context, @Optional String target) {
        final Player sender = context.getSender();

        if (target == null) {
            final User user = plugin.getRegistry().get(sender);
            if (user == null) return;

            context.sendMessage("§cYour balance is: §a" + user.getWallet().getFormattedBalance());
        } else {
            final User user = plugin.getRegistry().get(target);
            if (user == null) return;

            context.sendMessage("§c" + target + "'s balance: §a" + user.getWallet().getFormattedBalance());
        }
    }
}
