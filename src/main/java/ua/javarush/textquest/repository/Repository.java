package ua.javarush.textquest.repository;

import java.util.Map;
import java.util.Optional;

public interface Repository<T> {
    Map<String, T> findAll();

    Optional<T> findByName(String name);

    void add(T account);

    void update(T account);

    void delete(String name);
}
