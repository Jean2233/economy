package com.github.jean2233.economy.data.user;

import com.github.jean2233.economy.data.wallet.Wallet;
import com.github.jean2233.economy.storage.IStorage;
import com.github.jean2233.economy.storage.service.DatabaseService;
import com.github.jean2233.economy.storage.util.SQLReader;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDatabase implements IStorage<User> {
    private final DatabaseService service;
    private final SQLReader reader;

    public UserDatabase(Plugin plugin) throws IOException {
        this.service = new DatabaseService(plugin, "economy");

        this.reader = new SQLReader();
        reader.loadFromResources();

        service.init(reader.getSql("create_table"));
    }

    @Override
    public void insert(@NotNull User user) {
        try (PreparedStatement statement = prepareStatement("insert")) {
            statement.setString(1, user.getUniqueId().toString());
            statement.setString(2, user.getName());
            statement.setDouble(3, user.getWallet().getBalance());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void update(@NotNull User user) {
        try (PreparedStatement statement = prepareStatement("update")) {
            statement.setDouble(1, user.getWallet().getBalance());
            statement.setString(2, user.getUniqueId().toString());

            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(@NotNull User user) { }

    @Override
    public User find(@NotNull String id) {
        try (PreparedStatement statement = prepareStatement("find")) {
            statement.setString(1, id);

            final ResultSet set = statement.executeQuery();
            if (set.next()) return adapt(set);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @SneakyThrows @Override
    public User adapt(@NotNull ResultSet set) {
        final UUID uniqueId = UUID.fromString(set.getString("user_id"));
        final String name = set.getString("user_name");

        final double balance = set.getDouble("wallet_balance");

        final User user = new User(uniqueId, name);
        user.setWallet(new Wallet(balance, false));

        return user;
    }

    @Override
    public List<User> findAll() {
        final List<User> userList = new ArrayList<>();

        try (PreparedStatement statement = prepareStatement("find_all")) {
            final ResultSet set = statement.executeQuery();
            while (set.next()) userList.add(adapt(set));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return userList;
    }

    public DatabaseService getService() {
        return service;
    }

    @SneakyThrows
    private PreparedStatement prepareStatement(String sqlFile) {
        return service.getSource().getConnection().prepareStatement(
          reader.getSql(sqlFile)
        );
    }
}
