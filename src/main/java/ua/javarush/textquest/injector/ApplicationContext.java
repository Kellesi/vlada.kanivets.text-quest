package ua.javarush.textquest.injector;

import ua.javarush.textquest.controller.AccountController;
import ua.javarush.textquest.controller.LoginController;
import ua.javarush.textquest.controller.StageController;
import ua.javarush.textquest.dispatcher.MethodMap;
import ua.javarush.textquest.dispatcher.RegisterForControllers;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.repositoty.InMemoryRepository;
import ua.javarush.textquest.repositoty.UserRepository;
import ua.javarush.textquest.service.io.DataReader;
import ua.javarush.textquest.service.io.StageDataReaderJson;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContext {
    private static Map<Integer, Stage> stages;
    private static final UserRepository REPOSITORY = new InMemoryRepository();

    public static void loadStages(ServletContext context) {
        String jsonFilePath = context.getInitParameter("jsonFilePath");
        InputStream inputStream = context.getResourceAsStream(jsonFilePath);
        DataReader reader = new StageDataReaderJson(inputStream);

        stages = ((List<Stage>) reader.read()).stream().collect(Collectors.toMap(Stage::getStageID, stage -> stage));
    }

    public static UserRepository getRepository(){
        return REPOSITORY;
    }

    public static Map<String, MethodMap> URL_TO_METHOD() {
        return new RegisterForControllers().register(new StageController(stages), new LoginController(), new AccountController());
    }
}
