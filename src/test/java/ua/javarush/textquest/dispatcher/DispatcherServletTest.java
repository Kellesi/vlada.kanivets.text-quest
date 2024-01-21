package ua.javarush.textquest.dispatcher;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ua.javarush.textquest.exception.ServletExceptionHandler;
import ua.javarush.textquest.injector.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DispatcherServletTest {
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final Method method = Mockito.mock(Method.class);
    private final Object controllerInstance = Mockito.mock(Object.class);
    private final MethodType methodType = Mockito.mock(MethodType.class);
    private final DispatcherServlet dispatcher = new DispatcherServlet();
    private final MethodMap methodMap = new MethodMap(controllerInstance, methodType, method);
    private final Map<String, MethodMap> map = new HashMap<>(
            Map.of("/url1", methodMap));

    @Test
    void serviceShouldInvokeMethodFromMethodMap() throws InvocationTargetException, IllegalAccessException {
        String methodTypeName = "GET";

        when(request.getMethod()).thenReturn(methodTypeName);
        when(request.getRequestURI()).thenReturn("/url1");
        when(request.getContextPath()).thenReturn("");
        when(request.getServletPath()).thenReturn("");
        when(methodType.name()).thenReturn(methodTypeName);

        try (MockedStatic<ApplicationContext> applicationContextMockedStatic = Mockito.mockStatic(ApplicationContext.class)) {
            when(ApplicationContext.URL_TO_METHOD()).thenReturn(map);

            dispatcher.service(request, response);

            verify(method).invoke(controllerInstance, request, response);
        }
    }

    @Test
    void serviceShouldCallServletExceptionHandlerWhenException() throws InvocationTargetException, IllegalAccessException {
        Throwable ex = new InvocationTargetException(null);
        String methodTypeName = "GET";

        when(request.getMethod()).thenReturn(methodTypeName);
        when(request.getRequestURI()).thenReturn("/url1");
        when(request.getContextPath()).thenReturn("");
        when(request.getServletPath()).thenReturn("");
        when(methodType.name()).thenReturn(methodTypeName);
        when(method.invoke(controllerInstance, request, response)).thenThrow(ex);

        try (MockedStatic<ApplicationContext> applicationContextMockedStatic = Mockito.mockStatic(ApplicationContext.class);
             MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            when(ApplicationContext.URL_TO_METHOD()).thenReturn(map);

            dispatcher.service(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }
}