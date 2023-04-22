package ru.yandex.practicum.filmorate.controller;

import com.solidfire.gson.TypeAdapter;
import com.solidfire.gson.stream.JsonReader;
import com.solidfire.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        try {
            int minutes = (int) duration.toMinutes();
            jsonWriter.value(minutes);
        } catch (IOException | IllegalStateException | NullPointerException exception) {
            jsonWriter.value((String) null);
        }
    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        try {
            Long durationInMinutes = Long.parseLong(jsonReader.nextString());
            Duration duration = Duration.ofMinutes(durationInMinutes);
            return duration;
        } catch (IOException | IllegalStateException | NullPointerException exception) {
            return null;
        }
    }
}