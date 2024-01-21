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
            log.error(ex.getMessage(),ex);
            req.getRequestDispatcher("/error_page.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error(ex.getMessage(), ex);
        }
    }

    public static void handleLoginException(HttpServletRequest req, HttpServletResponse resp, LoginRuntimeException ex){
        try {
            req.setAttribute("errorMessage", ex.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.error(ex.getMessage(), ex);
        }
    }
}
