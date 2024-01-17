package ua.javarush.textquest.controller;

import ua.javarush.textquest.dispatcher.MethodType;
import ua.javarush.textquest.dispatcher.RequestMapping;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.InvalidPasswordException;
import ua.javarush.textquest.exception.ServletExceptionHandler;
import ua.javarush.textquest.exception.UserNotFoundException;
import ua.javarush.textquest.injector.ApplicationContext;
import ua.javarush.textquest.repository.Repository;
import ua.javarush.textquest.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController {
    @RequestMapping(url = "/login", method = MethodType.GET)
    public void showLoginPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }

    @RequestMapping(url = "/logout", method = MethodType.GET)
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getSession().removeAttribute("isLoggedIn");
            resp.sendRedirect("/");
        } catch (IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }

    @RequestMapping(url = "/login2", method = MethodType.POST)
    public void tryLogin(HttpServletRequest req, HttpServletResponse resp) {
        AccountService accountService = ApplicationContext.getAccountService();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            Account account = accountService.login(login, password);
            req.getSession().setAttribute("isLoggedIn", true);
            req.getSession().setAttribute("account", account);
            req.getRequestDispatcher("/account.jsp").forward(req, resp);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            handleWrongInputException(req, resp, e);
        } catch (ServletException | IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }

    @RequestMapping(url = "/registration", method = MethodType.POST)
    public void addAccount(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            req.getSession().setAttribute("isLoggedIn", true);
            Repository<Account> repository = ApplicationContext.getACCOUNT_REPOSITORY();
            Account account = new Account(login, password);
            repository.add(account);
            req.getSession().setAttribute("account", account);
            resp.sendRedirect("/account.jsp");
        } catch (IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }

    @RequestMapping(url = "/registration2", method = MethodType.GET)
    public void registration(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("/registration.jsp");
        } catch (IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }

    private void handleWrongInputException(HttpServletRequest req, HttpServletResponse resp, Throwable e) {
        req.setAttribute("errorMessage", e.getMessage());
        try {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }
}
