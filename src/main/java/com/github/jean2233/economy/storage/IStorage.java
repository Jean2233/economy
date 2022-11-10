package com.github.jean2233.economy.storage;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.List;

public interface IStorage<T> {
    void insert(@NotNull T t);
    void update(@NotNull T t);
    void delete(@NotNull T t);

    T find(@NotNull String id);
    T adapt(@NotNull ResultSet set);

    List<T> findAll();
}