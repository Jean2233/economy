package com.github.jean2233.economy.data.user;

import com.github.jean2233.economy.data.wallet.Wallet;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
public class User {
    private final UUID uniqueId;
    private final String name;

    private Wallet wallet;

    @NotNull
    public Wallet getWallet() {
        if (this.wallet != null) return this.wallet;

        this.wallet = new Wallet(0, true);
        return this.wallet;
    }
}
