package ua.javarush.textquest.controller;


import ua.javarush.textquest.dispatcher.MethodType;
import ua.javarush.textquest.dispatcher.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountController {

    @RequestMapping(url = "/account", method = MethodType.GET)
    public void getAccountInfo(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/account.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
