package com.github.jean2233.economy.command;

import com.github.jean2233.economy.EconomyPlugin;
import com.github.jean2233.economy.data.user.User;
import com.github.jean2233.economy.util.Formats;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.entity.Player;

public record SetCommand(EconomyPlugin plugin) {
    @Command(name = "setbalance", aliases = {"setbal"}, usage = "setbalance <target> <amount>")
    public void handle(Context<Player> context, String target, double value) {
        final Player sender = context.getSender();
        if (!sender.isOp()) {
            sender.sendMessage("§cYou must be operator to do this.");
            return;
        }

        final User user = plugin.getRegistry().get(sender);
        if (user == null) return;

        user.getWallet().set(value);
        context.sendMessage("§cYou set §a" + Formats.apply(value) + " §cto §a" + target);
    }
}
