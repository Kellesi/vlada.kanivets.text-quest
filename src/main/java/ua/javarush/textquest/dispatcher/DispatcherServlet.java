package ua.javarush.textquest.dispatcher;

import ua.javarush.textquest.exception.ServletExceptionHandler;
import ua.javarush.textquest.injector.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/quest/*")
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext.initJsonRepositoryFromServletContext(getServletContext());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String httpMethod = req.getMethod();
        String uri = req.getRequestURI();
        String url = uri.replace(req.getContextPath(), "").replace(req.getServletPath(), "");

        MethodMap methodMap = ApplicationContext.URL_TO_METHOD().get(url);
        Method method = methodMap.getMethod();
        Object controller = methodMap.getControllerInstance();
        MethodType methodType = methodMap.getMethodType();

        if (httpMethod.equalsIgnoreCase(methodType.name())) {
            try {
                method.invoke(controller, req, resp);
            } catch (InvocationTargetException | IllegalAccessException ex) {
                ServletExceptionHandler.handle(req, resp, ex);
            }
        }
    }
}
