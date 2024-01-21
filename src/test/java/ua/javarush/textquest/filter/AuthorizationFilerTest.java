package ua.javarush.textquest.filter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorizationFilerTest {
    private static final String LOGIN_PAGE_PATH = "./login";
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpSession session = Mockito.mock(HttpSession.class);
    private final AuthorizationFiler filer = Mockito.spy(new AuthorizationFiler());
    private final FilterChain filterChain = Mockito.mock(FilterChain.class);

    @Test
    void doFilterShouldRedirectToLoginPageIfIsLoggedInNull() throws IOException, ServletException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("isLoggedIn")).thenReturn(null);

        filer.doFilter(request, response, filterChain);

        verify(response).sendRedirect(LOGIN_PAGE_PATH);
    }

    @Test
    void doFilterShouldDoFilterIfIsLoggedNotNull() throws IOException, ServletException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("isLoggedIn")).thenReturn(true);

        filer.doFilter(request, response, filterChain);

        verify(filer).doFilter(request, response, filterChain);
    }

}