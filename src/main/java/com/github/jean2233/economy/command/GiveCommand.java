package com.github.jean2233.economy.command;

import com.github.jean2233.economy.EconomyPlugin;
import com.github.jean2233.economy.data.transaction.TransactionResponse;
import com.github.jean2233.economy.data.user.User;
import com.github.jean2233.economy.util.Formats;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public record GiveCommand(EconomyPlugin plugin) {
    @Command(name = "give", usage = "give <target> <amount>")
    public void handle(Context<Player> context, String target, double value) {
        final Player sender = context.getSender();

        final User senderUser = plugin.getRegistry().get(sender);
        if (senderUser == null) return;

        final TransactionResponse response = senderUser.getWallet().withdraw(value);
        if (response != TransactionResponse.SUCCESS) {
            context.sendMessage(response.getMessage());
            return;
        }

        final User targetUser = plugin.getRegistry().get(target);
        if (targetUser == null) return;

        targetUser.getWallet().deposit(value);

        sender.sendMessage("§cYou gave §a" + Formats.apply(value) + " §cto §a" + target);

        final Player player = Bukkit.getPlayer(target);
        if (player != null) {
            player.sendMessage("§cYou received §a" + Formats.apply(value) + " §cfrom §a" + sender.getName());
        }
    }
}
