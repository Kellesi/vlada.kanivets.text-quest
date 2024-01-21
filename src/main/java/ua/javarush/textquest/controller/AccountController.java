package ua.javarush.textquest.controller;


import ua.javarush.textquest.dispatcher.MethodType;
import ua.javarush.textquest.dispatcher.RequestMapping;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.ServletExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccountController {

    @RequestMapping(url = "/account", method = MethodType.GET)
    public void showAccountPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/account.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }

    @RequestMapping(url = "/save", method = MethodType.POST)
    public void saveStage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String savePoint = req.getParameter("savePoint");
            if (savePoint.isEmpty()) {
                savePoint = "0";
            }
            Account account = (Account) req.getSession().getAttribute("account");
            account.setSavePoint(savePoint);
            resp.sendRedirect("/quest/stage?choice=" + savePoint);
        } catch (IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }
}
