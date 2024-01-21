package ua.javarush.textquest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.ServletExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {
    private static final String STAGE_PATH = "/quest/stage?choice=";
    private static final String ACCOUNT_PATH = "/account.jsp";
    private final AccountController accountController = new AccountController();
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpSession session = Mockito.mock(HttpSession.class);
    private final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);

    @Test
    void showAccountPageShouldRedirectToAccountJsp() throws ServletException, IOException {

        when(request.getRequestDispatcher(ACCOUNT_PATH)).thenReturn(dispatcher);

        accountController.showAccountPage(request, response);

        verify(request, times(1)).getRequestDispatcher(ACCOUNT_PATH);
        verify(dispatcher).forward(request, response);
    }

    @ParameterizedTest
    @ValueSource(classes = {IOException.class, ServletException.class})
    void showAccountPageShouldCallExceptionServletHandlerWhenException(Class<Throwable> throwable) throws ServletException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Throwable ex = throwable.getConstructor().newInstance();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            when(request.getRequestDispatcher(ACCOUNT_PATH)).thenReturn(dispatcher);

            doThrow(ex).when(dispatcher).forward(request, response);

            accountController.showAccountPage(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }

    @Test
    void saveStageShouldShouldReturnStageJsp() throws IOException {
        String savePoint = "1";
        Account account = new Account("name", "password");

        when(request.getParameter("savePoint")).thenReturn(savePoint);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("account")).thenReturn(account);

        accountController.saveStage(request, response);

        verify(response, times(1)).sendRedirect(STAGE_PATH + savePoint);
    }

    @Test
    void saveStageShouldSetZeroAsSavePointIfSavePointIsEmpty() throws IOException {
        String actualSavePoint = "";
        String expectedSavePoint = "0";
        Account account = new Account("name", "password");

        when(request.getParameter("savePoint")).thenReturn(actualSavePoint);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("account")).thenReturn(account);

        accountController.saveStage(request, response);

        verify(response, times(1)).sendRedirect(STAGE_PATH + expectedSavePoint);
    }

    @Test
    void saveStageShouldSetSavePointToCurrentAccount() {
        String expectedSavePoint = "1";
        Account actual = new Account("name", "password");
        Account expected = new Account("name2", "password2");
        expected.setSavePoint(expectedSavePoint);

        when(request.getParameter("savePoint")).thenReturn(expectedSavePoint);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("account")).thenReturn(actual);

        accountController.saveStage(request, response);

        assertEquals(expected.getSavePoint(), actual.getSavePoint());
    }

    @Test
    void saveStageShouldCallExceptionServletHandlerWhenException() throws IOException {
        String savePoint = "1";
        IOException ex = new IOException();
        Account account = new Account("name", "password");

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            when(request.getParameter("savePoint")).thenReturn(savePoint);
            when(request.getSession()).thenReturn(session);
            when(session.getAttribute("account")).thenReturn(account);

            doThrow(ex).when(response).sendRedirect(STAGE_PATH + savePoint);

            accountController.saveStage(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }
}