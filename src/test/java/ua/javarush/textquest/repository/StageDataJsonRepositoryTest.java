package ua.javarush.textquest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.javarush.textquest.entity.Stage;
import ua.javarush.textquest.exception.StageNotFoundRuntimeException;

import javax.servlet.ServletContext;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StageDataJsonRepositoryTest {
    private static final String TEST_SOURCE_PATH = "/test-stages.json";
    private static final String EXISTING_ID = "0";
    private static final String NOT_EXISTING_ID = "9";
    private static final ServletContext servletContext = Mockito.mock(ServletContext.class);
    private StageDataJsonRepository repository;

    @BeforeEach
    void init() {
        when(servletContext.getInitParameter("jsonFilePath")).thenReturn(TEST_SOURCE_PATH);

        InputStream resourceAsStream = getClass().getResourceAsStream(TEST_SOURCE_PATH);
        InputStreamReader reader = new InputStreamReader(resourceAsStream);
        when(servletContext.getResourceAsStream(TEST_SOURCE_PATH)).thenReturn(resourceAsStream);

        repository = new StageDataJsonRepository(servletContext);
    }

    @Test
    void findAllShouldReturnMapOfAllStages() {
        int expectedSize = 3;
        Map<String, Stage> actual = repository.findAll();

        assertEquals(expectedSize, actual.size());
    }

    @Test
    void findByNameShouldReturnOptionalOfStageIfPresent() {
        String expectedId = EXISTING_ID;
        Optional<Stage> stageOptional = repository.findByName(expectedId);

        assertTrue(stageOptional.isPresent());
        assertEquals(expectedId, String.valueOf(stageOptional.get().getId()));
    }

    @Test
    void findByNameShouldReturnEmptyOptionalOfStageIfAbsent() {
        Optional<Stage> stageOptional = repository.findByName(NOT_EXISTING_ID);

        assertTrue(stageOptional.isEmpty());
    }

    @Test
    void addShouldAddNewItemToMap() {
        int sizeBefore = repository.findAll().size();
        Stage newStage = new Stage();
        String id = "4";
        newStage.setId(Integer.parseInt(id));

        repository.add(newStage);
        Map<String, Stage> allStages = repository.findAll();

        assertEquals(sizeBefore + 1, allStages.size());
        assertTrue(allStages.containsKey(id));
        assertEquals(newStage, allStages.get(id));
    }

    @Test
    void updateShouldRewriteItemIfPresent() {
        Stage expected = new Stage();
        expected.setId(Integer.parseInt(EXISTING_ID));
        expected.setDescription("New description");

        repository.update(expected);
        Stage actual = repository.findByName(EXISTING_ID).get();

        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    void updateShouldThrowExceptionIfStageNotFound() {
        Stage stage = new Stage();
        stage.setId(Integer.parseInt(NOT_EXISTING_ID));

        assertThrows(StageNotFoundRuntimeException.class, () -> repository.update(stage));
    }

    @Test
    void deleteShouldRemoveItemFromMap() {
        int sizeBefore = repository.findAll().size();

        repository.delete(EXISTING_ID);
        Map<String, Stage> allStages = repository.findAll();

        assertEquals(sizeBefore - 1, allStages.size());
        assertFalse(allStages.containsKey(EXISTING_ID));
    }
}