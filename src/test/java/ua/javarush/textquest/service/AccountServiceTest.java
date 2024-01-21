package ua.javarush.textquest.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.InvalidPasswordRuntimeException;
import ua.javarush.textquest.exception.UserNotFoundRuntimeException;
import ua.javarush.textquest.repository.AccountInMemoryRepository;
import ua.javarush.textquest.repository.Repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private static final String LOGIN = "login";
    private static String CORRECT_PASSWORD = "password";
    private static String INCORRECT_PASSWORD = "incorrectpassword";
    private final Repository<Account> repository = Mockito.mock(AccountInMemoryRepository.class);
    private final AccountService accountService = new AccountService(repository);

    @Test
    void loginShouldReturnAccountIfLoginAndPasswordCorrect() {
        Account expected = new Account(LOGIN, CORRECT_PASSWORD);

        when(repository.findByName(LOGIN)).thenReturn(Optional.of(expected));

        Account actual = accountService.login(LOGIN, CORRECT_PASSWORD);

        assertEquals(expected, actual);
    }

    @Test
    void loginShouldThrowExceptionWhenUserIsAbsent() {
        when(repository.findByName(LOGIN)).thenReturn(Optional.empty());

        UserNotFoundRuntimeException exception =
                assertThrows(UserNotFoundRuntimeException.class, () -> accountService.login(LOGIN, CORRECT_PASSWORD));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void loginShouldThrowExceptionWhenPasswordsNotEquals() {
        when(repository.findByName(LOGIN)).thenReturn(Optional.of(new Account(LOGIN, INCORRECT_PASSWORD)));

        InvalidPasswordRuntimeException exception =
                assertThrows(InvalidPasswordRuntimeException.class, () -> accountService.login(LOGIN, CORRECT_PASSWORD));
        assertEquals("Invalid password", exception.getMessage());
    }

}