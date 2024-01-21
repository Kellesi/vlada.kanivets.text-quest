package ua.javarush.textquest.repository;

import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.UserNotFoundRuntimeException;

import java.util.*;

public class AccountInMemoryRepository implements Repository<Account> {
    private final Map<String, Account> accounts = new HashMap<>(
            Map.of(
                    "Adam", new Account("Adam", "123"),
                    "Tod", new Account("Tod", "qwerty")));

    @Override
    public Map<String, Account> findAll() {
        return accounts;
    }

    @Override
    public Optional<Account> findByName(String name) {
        return Optional.ofNullable(accounts.get(name));
    }

    @Override
    public void add(Account account) {
        accounts.put(account.getName(), account);
    }

    @Override
    public void update(Account account) {
        if (findByName(account.getName()).isPresent()) {
            accounts.put(account.getName(), account);
        } else {
            throw new UserNotFoundRuntimeException("User not found");
        }
    }

    @Override
    public void delete(String name) {
        accounts.remove(name);
    }
}
