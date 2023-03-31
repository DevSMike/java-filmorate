package ru.yandex.practicum.filmorate.validators;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void write(final JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        try {
            jsonWriter.value(localDate.format(formatter));
        } catch (NullPointerException e) {
            System.out.println("На вход Time не передавалось!");
            jsonWriter.value(LocalDate.MIN.format(formatter));
        }
    }

    @Override
    public LocalDate read(final JsonReader jsonReader) throws IOException {
        return LocalDate.parse(jsonReader.nextString(), formatter);

    }
}
