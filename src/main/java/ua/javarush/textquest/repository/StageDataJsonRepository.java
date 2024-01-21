package ua.javarush.textquest.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.exception.StageNotFoundRuntimeException;

import javax.servlet.ServletContext;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class StageDataJsonRepository implements Repository<Stage> {
    private final Map<String, Stage> idToStages;

    public StageDataJsonRepository(ServletContext servletContext) {
        this.idToStages = init(servletContext);
    }

    private Map<String, Stage> init(ServletContext servletContext) {
        List<Stage> stages = new ArrayList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Stage>>() {
        }.getType();
        String filePath = servletContext.getInitParameter("jsonFilePath");
        try (InputStreamReader reader =
                     new InputStreamReader(servletContext.getResourceAsStream(filePath))) {
            stages = gson.fromJson(reader, listType);
        } catch (IOException e) {
            log.error(e);
        }
        return stages.stream().collect(Collectors.toMap(stage -> String.valueOf(stage.getId()), stage -> stage));
    }

    @Override
    public Map<String, Stage> findAll() {
        return idToStages;
    }

    @Override
    public Optional<Stage> findByName(String stageId) {
        return Optional.ofNullable(idToStages.get(stageId));
    }

    @Override
    public void add(Stage stage) {
        idToStages.put(String.valueOf(stage.getId()), stage);
    }

    @Override
    public void update(Stage stage) {
        if (findByName(String.valueOf(stage.getId())).isPresent()) {
            idToStages.put(String.valueOf(stage.getId()), stage);
        } else {
            throw new StageNotFoundRuntimeException();
        }
    }

    @Override
    public void delete(String stageId) {
        idToStages.remove(stageId);
    }
}
