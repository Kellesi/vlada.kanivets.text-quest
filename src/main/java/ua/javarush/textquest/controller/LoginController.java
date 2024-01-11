package ua.javarush.textquest.controller;

import ua.javarush.textquest.dispatcher.MethodType;
import ua.javarush.textquest.dispatcher.RequestMapping;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.injector.ApplicationContext;
import ua.javarush.textquest.service.verification.LoginVerificator;
import ua.javarush.textquest.service.verification.Verificator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginController {
    @RequestMapping(url = "/login", method = MethodType.GET)
    public void login(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(url = "/logout", method = MethodType.GET)
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getSession().removeAttribute("isLoggedIn");
            resp.sendRedirect("/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(url = "/login2", method = MethodType.POST)
    public void tryLogin(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Optional<Account> account = ApplicationContext.getRepository().findByName(login);
        if (account.isPresent()) {
            try {
                Verificator verificator = new LoginVerificator(account.get(), password);
                verificator.verify();
                req.getSession().setAttribute("isLoggedIn", true);
                req.getSession().setAttribute("account", account.get());
                req.getRequestDispatcher("/account.jsp").forward(req, resp);
            } catch (IllegalAccessException | ServletException | IOException ex) {
                try {
                    req.setAttribute("errorMessage",ex.getMessage());
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                req.setAttribute("errorMessage","Користувач не знайдений");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
