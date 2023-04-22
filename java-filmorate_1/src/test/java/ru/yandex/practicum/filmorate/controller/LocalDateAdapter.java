package ru.yandex.practicum.filmorate.controller;

import com.solidfire.gson.TypeAdapter;
import com.solidfire.gson.stream.JsonReader;
import com.solidfire.gson.stream.JsonWriter;
import org.springframework.format.datetime.DateFormatter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        try {
            jsonWriter.value(localDate.format(dateFormatter));
        } catch (IOException | IllegalStateException | NullPointerException e) {
            jsonWriter.value((String) null);
        }
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        try {
            return LocalDate.parse(jsonReader.nextString(), dateFormatter);
        }
        catch (IOException | IllegalStateException | NullPointerException e) {
            return null;
        }

    }
}
