package ua.javarush.textquest.injector;

import ua.javarush.textquest.controller.LoginController;
import ua.javarush.textquest.controller.StageController;
import ua.javarush.textquest.dispatcher.MethodMap;
import ua.javarush.textquest.dispatcher.RegisterForControllers;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.service.ContextReader;
import ua.javarush.textquest.service.StageContextReaderJson;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContext {
    private static Map<Integer, Stage> stages;

    public static void loadStages(ServletContext context) {
        String jsonFilePath = context.getInitParameter("jsonFilePath");
        InputStream inputStream = context.getResourceAsStream(jsonFilePath);
        ContextReader reader = new StageContextReaderJson(inputStream);

        stages = ((List<Stage>) reader.read()).stream().collect(Collectors.toMap(Stage::getStageID, stage -> stage));
    }

    public static Map<String, MethodMap> URL_TO_METHOD() {
        return new RegisterForControllers().register(new StageController(stages), new LoginController());
    }
}
