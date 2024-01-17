package ua.javarush.textquest.exception;

import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ServletExceptionHandler {

    public static void handle(HttpServletRequest req, HttpServletResponse resp, Throwable ex) {
        try {
            req.setAttribute("errorMessage", ex.getMessage());
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
            log.error(ex.getMessage(),ex);
        } catch (ServletException | IOException e) {
            log.error(ex.getMessage(), ex);
        }
    }
}
