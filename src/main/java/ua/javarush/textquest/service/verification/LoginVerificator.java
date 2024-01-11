package ua.javarush.textquest.service.verification;

import ua.javarush.textquest.entity.Account;

public class LoginVerificator implements Verificator {
    private Account account;
    private String password;

    public LoginVerificator(Account account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public boolean verify() throws IllegalAccessException {
        if (account.getPassword().equals(password)) {
            return true;
        } else throw new IllegalAccessException("Неправильний пароль");
    }
}
