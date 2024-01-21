package ua.javarush.textquest.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/quest/save", filterName = "authorization")
public class AuthorizationFiler implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filter) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        if (httpRequest.getSession().getAttribute("isLoggedIn") == null) {
            httpResponse.sendRedirect("./login");
        } else {
            filter.doFilter(req, resp);
        }
    }
}
