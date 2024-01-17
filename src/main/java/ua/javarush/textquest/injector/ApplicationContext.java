package ua.javarush.textquest.injector;

import lombok.Getter;
import ua.javarush.textquest.controller.AccountController;
import ua.javarush.textquest.controller.LoginController;
import ua.javarush.textquest.controller.StageController;
import ua.javarush.textquest.dispatcher.MethodMap;
import ua.javarush.textquest.dispatcher.RegisterForControllers;
import ua.javarush.textquest.entity.Account;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.repository.UserInMemoryRepository;
import ua.javarush.textquest.repository.Repository;
import ua.javarush.textquest.repository.StageDataRepositoryJson;
import ua.javarush.textquest.service.AccountService;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContext {
    @Getter
    private static final Repository<Account> ACCOUNT_REPOSITORY = new UserInMemoryRepository();
    @Getter
    private static final AccountService accountService = new AccountService(ACCOUNT_REPOSITORY);
    private static Repository<Stage> stageRepository;
    private static Map<Integer, Stage> stages;

    public static void initJsonRepositoryFromServletContext(ServletContext servletContext) {
        stageRepository = new StageDataRepositoryJson(servletContext);
        stages = idToStages();
    }

    public static Map<String, MethodMap> URL_TO_METHOD() {
        return new RegisterForControllers().register(new StageController(stages), new LoginController(), new AccountController());
    }

    private static Map<Integer, Stage> idToStages() {
        return stageRepository.findAll().stream().collect(Collectors.toMap(Stage::getId, stage -> stage));
    }
}
