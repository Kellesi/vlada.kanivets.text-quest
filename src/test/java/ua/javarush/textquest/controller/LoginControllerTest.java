package ua.javarush.textquest.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.exception.InvalidPasswordRuntimeException;
import ua.javarush.textquest.exception.LoginRuntimeException;
import ua.javarush.textquest.exception.ServletExceptionHandler;
import ua.javarush.textquest.exception.UserNotFoundRuntimeException;
import ua.javarush.textquest.injector.ApplicationContext;
import ua.javarush.textquest.repository.Repository;
import ua.javarush.textquest.repository.AccountInMemoryRepository;
import ua.javarush.textquest.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class LoginControllerTest {
    private static final String LOGIN_PAGE_PATH = "/login.jsp";
    private static final String ACCOUNT_PAGE_PATH = "/account.jsp";
    private static final String REGISTRATION_PAGE_PATH = "/registration.jsp";
    private static final String HOME_PAGE_PATH = "/";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private final LoginController loginController = new LoginController();
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpSession session = Mockito.mock(HttpSession.class);
    private final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
    private final AccountService accountService = Mockito.mock(AccountService.class);

    @BeforeEach
    void init() {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(LOGIN)).thenReturn(LOGIN);
        when(request.getParameter(PASSWORD)).thenReturn(PASSWORD);
    }

    @Test
    void showLoginPageShouldRedirectToLoginPage() throws ServletException, IOException {

        when(request.getRequestDispatcher(LOGIN_PAGE_PATH)).thenReturn(dispatcher);

        loginController.showLoginPage(request, response);

        verify(request, times(1)).getRequestDispatcher(LOGIN_PAGE_PATH);
        verify(dispatcher).forward(request, response);
    }

    @ParameterizedTest
    @ValueSource(classes = {IOException.class, ServletException.class})
    void showLoginPageShouldCallExceptionServletHandlerWhenException(Class<Throwable> throwable) throws ServletException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Throwable ex = throwable.getConstructor().newInstance();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            when(request.getRequestDispatcher(LOGIN_PAGE_PATH)).thenReturn(dispatcher);

            doThrow(ex).when(dispatcher).forward(request, response);

            loginController.showLoginPage(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }

    @Test
    void logoutShouldRedirectToHomePage() throws IOException {
        when(request.getSession()).thenReturn(session);

        loginController.logout(request, response);

        verify(response).sendRedirect(HOME_PAGE_PATH);
    }

    @Test
    void logoutShouldRemoveAttributeIsLoggedIn() {
        String attribute = "isLoggedIn";

        loginController.logout(request, response);

        verify(session).removeAttribute(attribute);
    }

    @Test
    void logoutShouldCallExceptionServletHandlerWhenException() throws IOException {
        IOException ex = new IOException();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            doThrow(ex).when(response).sendRedirect(HOME_PAGE_PATH);

            loginController.logout(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }

    @Test
    void tryLoginShouldRedirectToAccountPageWithAttributesWhenLoginSuccessful() throws ServletException, IOException {
        Account account = new Account(LOGIN, PASSWORD);

        try (MockedStatic<ApplicationContext> application = Mockito.mockStatic(ApplicationContext.class)) {
            when(ApplicationContext.getAccountService()).thenReturn(accountService);
            when(request.getRequestDispatcher(ACCOUNT_PAGE_PATH)).thenReturn(dispatcher);
            when(accountService.login(anyString(), anyString())).thenReturn(account);

            loginController.tryLogin(request, response);

            verify(session).setAttribute("isLoggedIn", true);
            verify(session).setAttribute("account", account);
            verify(request).getRequestDispatcher(ACCOUNT_PAGE_PATH);
            verify(dispatcher).forward(request, response);
        }

    }

    @ParameterizedTest
    @ValueSource(classes = {InvalidPasswordRuntimeException.class, UserNotFoundRuntimeException.class})
    void tryLoginShouldCallExceptionServletHandlerWhenLoginException(Class<LoginRuntimeException> throwable) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        LoginRuntimeException ex = throwable.getConstructor().newInstance();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class);
             MockedStatic<ApplicationContext> application = Mockito.mockStatic(ApplicationContext.class)) {
            when(ApplicationContext.getAccountService()).thenReturn(accountService);
            when(accountService.login(anyString(), anyString())).thenThrow(ex);

            loginController.tryLogin(request, response);

            handler.verify(() -> ServletExceptionHandler.handleLoginException(request, response, ex));
        }
    }

    @ParameterizedTest
    @ValueSource(classes = {IOException.class, ServletException.class})
    void tryLoginShouldCallExceptionServletHandlerWhenException(Class<Throwable> throwable) throws IOException, ServletException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Throwable ex = throwable.getConstructor().newInstance();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class);
             MockedStatic<ApplicationContext> application = Mockito.mockStatic(ApplicationContext.class)) {
            when(ApplicationContext.getAccountService()).thenReturn(accountService);
            when(accountService.login(LOGIN, PASSWORD)).thenReturn(new Account(LOGIN, PASSWORD));
            when(request.getRequestDispatcher(ACCOUNT_PAGE_PATH)).thenReturn(dispatcher);

            doThrow(ex).when(dispatcher).forward(request, response);

            loginController.tryLogin(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }

    @Test
    void addAccountShouldRedirectToAccountPageWithSessionParams() throws IOException {
        loginController.addAccount(request, response);

        verify(session).setAttribute("isLoggedIn", true);
        verify(session).setAttribute(eq("account"), any(Account.class));
        verify(response).sendRedirect("/account.jsp");
    }

    @Test
    void addAccountShouldAddNewAccountToRepository() throws IOException {
        Repository<Account> repository = Mockito.spy(new AccountInMemoryRepository());
        int sizeBefore = repository.findAll().size();

        try (MockedStatic<ApplicationContext> application = Mockito.mockStatic(ApplicationContext.class)) {
            when(ApplicationContext.getACCOUNT_REPOSITORY()).thenReturn(repository);

            loginController.addAccount(request, response);
            int sizeAfter = repository.findAll().size();

            assertEquals(sizeBefore + 1, sizeAfter);
        }
    }

    @Test
    void addAccountShouldCallExceptionServletHandlerWhenException() throws IOException {
        IOException ex = new IOException();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            doThrow(ex).when(response).sendRedirect(ACCOUNT_PAGE_PATH);

            loginController.addAccount(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }

    @Test
    void registrationShouldRedirectToRegistrationPage() throws IOException {
        loginController.registration(request, response);

        verify(response).sendRedirect(REGISTRATION_PAGE_PATH);
    }

    @Test
    void registrationShouldCallExceptionServletHandlerWhenException() throws IOException {
        IOException ex = new IOException();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            doThrow(ex).when(response).sendRedirect(REGISTRATION_PAGE_PATH);

            loginController.registration(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }
}