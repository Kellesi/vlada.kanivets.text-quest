package ua.javarush.textquest.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class ServletExceptionHandlerTest {
    private static final String ERROR_PAGE_PATH = "/error_page.jsp";
    private static final String LOGIN_PAGE_PATH = "/login.jsp";
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);

    @Test
    void handleShouldRedirectToErrorPageJsp() throws ServletException, IOException {
        Throwable ex = new Throwable("Message");

        when(request.getRequestDispatcher(ERROR_PAGE_PATH)).thenReturn(dispatcher);

        ServletExceptionHandler.handle(request, response, ex);

        verify(request).getRequestDispatcher(ERROR_PAGE_PATH);
        verify(dispatcher).forward(request, response);
    }

    @ParameterizedTest
    @ValueSource(classes = {InvalidPasswordRuntimeException.class, UserNotFoundRuntimeException.class})
    void handleLoginExceptionShouldRedirectToLoginPageWithErrorMessage(Class<LoginRuntimeException> throwable) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ServletException, IOException {
        LoginRuntimeException ex = throwable.getConstructor(String.class).newInstance("Error Message");

        when(request.getRequestDispatcher(LOGIN_PAGE_PATH)).thenReturn(dispatcher);

        ServletExceptionHandler.handleLoginException(request, response, ex);

        verify(request).setAttribute("errorMessage", ex.getMessage());
        verify(request).getRequestDispatcher(LOGIN_PAGE_PATH);
        verify(dispatcher).forward(request, response);
    }

}