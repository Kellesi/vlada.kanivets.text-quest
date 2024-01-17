package ua.javarush.textquest.service;

import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.InvalidPasswordException;
import ua.javarush.textquest.exception.UserNotFoundException;
import ua.javarush.textquest.repository.Repository;

import java.util.Optional;

public class AccountService {
    private final Repository<Account> repository;

    public AccountService(Repository<Account> repository) {
        this.repository = repository;
    }

    public Account login(String login, String password) {
        Optional<Account> account = repository.findByName(login);
        if (account.isPresent()) {
            return checkPassword(account.get(), password);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private Account checkPassword(Account account, String password) {
        if (account.getPassword().equals(password)) {
            return account;
        } else throw new InvalidPasswordException("Invalid password");
    }
}
