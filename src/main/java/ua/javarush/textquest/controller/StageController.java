package ua.javarush.textquest.controller;

import ua.javarush.textquest.dispatcher.MethodType;
import ua.javarush.textquest.dispatcher.RequestMapping;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.entity.Stage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class StageController {
    private final Map<Integer, Stage> stages;

    public StageController(Map<Integer, Stage> stages) {
        this.stages = stages;
    }

    @RequestMapping(url = "/stage", method = MethodType.GET)
    public void getStage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String choice = req.getParameter("choice");
            int stageID = 0;
            if (choice != null) {
                stageID = Integer.parseInt(choice);
            }

            Stage stage = stages.get(stageID);
            String description = stage.getStageDescription();
            req.setAttribute("description", description);

            Object[] choises = stage.getStepChoices();
            req.setAttribute("choises", choises);

            String stageImg = stage.getStageImg();
            req.setAttribute("stageImg", stageImg);

            if (stage.isDeadPoint()) {
                if (req.getSession().getAttribute("isLoggedIn") != null) {
                    Account account = (Account) req.getSession().getAttribute("account");
                    account.setDeathCount(account.getDeathCount() + 1);
                }
                req.getRequestDispatcher("/dead.jsp").forward(req, resp);
            }
            if (stage.isEndPoint()) {
                if (req.getSession().getAttribute("isLoggedIn") != null) {
                    Account account = (Account) req.getSession().getAttribute("account");
                    account.setDeathCount(account.getCollectedEndings() + 1);
                }
                req.getRequestDispatcher("/end.jsp").forward(req, resp);
            }
            req.getRequestDispatcher("/stage.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
