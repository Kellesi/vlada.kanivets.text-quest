package ua.javarush.textquest.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ua.javarush.textquest.entity.Stage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class StageDataReaderJson implements DataReader {
    private final InputStream stream;

    public StageDataReaderJson(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public Object read() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Stage>>() {}.getType();

        return gson.fromJson(new InputStreamReader(stream), listType);
    }
}
