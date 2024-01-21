package ua.javarush.textquest.controller;

import ua.javarush.textquest.dispatcher.MethodType;
import ua.javarush.textquest.dispatcher.RequestMapping;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.exception.ServletExceptionHandler;
import ua.javarush.textquest.injector.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class StageController {
    private final Map<String, Stage> stages;

    public StageController(Map<String, Stage> stages) {
        this.stages = stages;
    }

    @RequestMapping(url = "/stage", method = MethodType.GET)
    public void showStage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String choice = req.getParameter("choice");
            String stageID = "0";
            if (choice != null) {
                stageID = choice;
            }

            Stage stage = stages.get(stageID);
            req.setAttribute("stage", stage);

            if (stage.isDeadPoint()) {
                if (req.getSession().getAttribute("isLoggedIn") != null) {
                    Account account = (Account) req.getSession().getAttribute("account");
                    account.setDeathCount(account.getDeathCount() + 1);
                    ApplicationContext.getACCOUNT_REPOSITORY().update(account);
                }
                req.getRequestDispatcher("/dead.jsp").forward(req, resp);
            } else if (stage.isEndPoint()) {
                if (req.getSession().getAttribute("isLoggedIn") != null) {
                    Account account = (Account) req.getSession().getAttribute("account");
                    account.setDeathCount(account.getCollectedEndings() + 1);
                    ApplicationContext.getACCOUNT_REPOSITORY().update(account);
                }
                req.getRequestDispatcher("/end.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/stage.jsp").forward(req, resp);
            }
        } catch (ServletException | IOException ex) {
            ServletExceptionHandler.handle(req, resp, ex);
        }
    }
}
