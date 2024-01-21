package ua.javarush.textquest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.exception.StageNotFoundRuntimeException;
import ua.javarush.textquest.exception.UserNotFoundRuntimeException;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountInMemoryRepositoryTest {
    private static final String EXISTING_NAME = "Adam";
    private static final String NOT_EXISTING_NAME = "Steven";
    private AccountInMemoryRepository repository;

    @BeforeEach
    void init() {
        repository = new AccountInMemoryRepository();
    }

    @Test
    void findAllShouldReturnMapOfAllStages() {
        int expectedSize = 2;
        Map<String, Account> actual = repository.findAll();

        assertEquals(expectedSize, actual.size());
    }

    @Test
    void findByNameShouldReturnOptionalOfStageIfPresent() {
        String expectedName = EXISTING_NAME;
        Optional<Account> accountOptional = repository.findByName(expectedName);

        assertTrue(accountOptional.isPresent());
        assertEquals(expectedName, String.valueOf(accountOptional.get().getName()));
    }

    @Test
    void findByNameShouldReturnEmptyOptionalOfStageIfAbsent() {
        Optional<Account> accountOptional = repository.findByName(NOT_EXISTING_NAME);

        assertTrue(accountOptional.isEmpty());
    }

    @Test
    void addShouldAddNewItemToMap() {
        int sizeBefore = repository.findAll().size();
        String name = "name";
        String password = "password";
        Account newAccount = new Account(name, password);

        repository.add(newAccount);
        Map<String, Account> allAccounts = repository.findAll();

        assertEquals(sizeBefore + 1, allAccounts.size());
        assertTrue(allAccounts.containsKey(name));
        assertEquals(newAccount, allAccounts.get(name));
    }

    @Test
    void updateShouldRewriteItemIfPresent() {
        String newPassword = "new password";
        Account expected = new Account(EXISTING_NAME, newPassword);

        repository.update(expected);
        Account actual = repository.findByName(EXISTING_NAME).get();

        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    void updateShouldThrowExceptionIfAccountNotFound() {
        Account account = new Account(NOT_EXISTING_NAME, "password");

        assertThrows(UserNotFoundRuntimeException.class, () -> repository.update(account));
    }

    @Test
    void deleteShouldRemoveItemFromMap() {
        int sizeBefore = repository.findAll().size();

        repository.delete(EXISTING_NAME);
        Map<String, Account> allAccounts = repository.findAll();

        assertEquals(sizeBefore - 1, allAccounts.size());
        assertFalse(allAccounts.containsKey(EXISTING_NAME));
    }
}