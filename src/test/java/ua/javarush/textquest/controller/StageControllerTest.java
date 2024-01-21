package ua.javarush.textquest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.entity.StepChoice;
import ua.javarush.textquest.exception.ServletExceptionHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class StageControllerTest {

    private static final String STAGE_PAGE_PATH = "/stage.jsp";
    private static final String END_PAGE_PATH = "/end.jsp";
    private static final String DEAD_PAGE_PATH = "/dead.jsp";
    private final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
    private final HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
    private final HttpSession session = Mockito.mock(HttpSession.class);
    private final RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
    private final Stage stage = Mockito.mock(Stage.class);
    private final HashMap<String, Stage> stages = new HashMap<>(
            Map.of("0", stage,
                    "1", stage));
    private final StageController stageController = Mockito.spy(new StageController(stages));

    @BeforeEach
    void init() {
        when(request.getSession()).thenReturn(session);
        when(stage.getDescription()).thenReturn("description");
        when(stage.getStepChoices()).thenReturn(new StepChoice[]{});
        when(stage.getImg()).thenReturn("img");
    }

    @Test
    void showStageShouldRedirectToStagePage() throws ServletException, IOException {
        when(request.getRequestDispatcher(STAGE_PAGE_PATH)).thenReturn(dispatcher);

        stageController.showStage(request, response);

        verify(request, times(1)).getRequestDispatcher(STAGE_PAGE_PATH);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void showStageShouldSetRequestAttributeStage() {
        String choice = "1";
        when(request.getRequestDispatcher(STAGE_PAGE_PATH)).thenReturn(dispatcher);
        when(request.getParameter("choice")).thenReturn(choice);
        when(stage.isDeadPoint()).thenReturn(false);
        when(stage.isEndPoint()).thenReturn(false);

        stageController.showStage(request, response);

        verify(request, times(1)).setAttribute("stage", stages.get(choice));
    }

    @Test
    void showStageShouldSetStageZeroIfChoiceIsNull() {
        String choice = null;
        when(request.getRequestDispatcher(STAGE_PAGE_PATH)).thenReturn(dispatcher);
        when(request.getParameter("choice")).thenReturn(choice);

        stageController.showStage(request, response);

        verify(request, times(1)).setAttribute("stage", stages.get("0"));
    }

    @Test
    void showStageShouldRedirectToEndPageIfIsEndPoint() throws ServletException, IOException {
        when(request.getRequestDispatcher(END_PAGE_PATH)).thenReturn(dispatcher);
        when(stage.isEndPoint()).thenReturn(true);

        stageController.showStage(request, response);

        verify(request, times(1)).getRequestDispatcher(END_PAGE_PATH);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void showStageShouldRedirectToDeadPageIfIsDeadPoint() throws ServletException, IOException {
        when(request.getRequestDispatcher(DEAD_PAGE_PATH)).thenReturn(dispatcher);
        when(stage.isDeadPoint()).thenReturn(true);

        stageController.showStage(request, response);

        verify(request, times(1)).getRequestDispatcher(DEAD_PAGE_PATH);
        verify(dispatcher).forward(request, response);
    }

    @ParameterizedTest
    @ValueSource(classes = {IOException.class, ServletException.class})
    void showStageShouldCallExceptionServletHandlerWhenException(Class<Throwable> throwable) throws ServletException, IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Throwable ex = throwable.getConstructor().newInstance();

        try (MockedStatic<ServletExceptionHandler> handler = Mockito.mockStatic(ServletExceptionHandler.class)) {
            when(request.getRequestDispatcher(STAGE_PAGE_PATH)).thenReturn(dispatcher);

            doThrow(ex).when(dispatcher).forward(request, response);

            stageController.showStage(request, response);

            handler.verify(() -> ServletExceptionHandler.handle(request, response, ex));
        }
    }
}