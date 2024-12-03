package org.example.nosql.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import org.example.nosql.dto.Movie;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Component
public class Mapper {
    private final String path = "C:\\Users\\vgoli\\OneDrive\\Рабочий стол\\nosql\\src\\main\\resources\\data\\";

    public void map() {
        List<String[]> l1 = saveToListFromCSV();
        List<Movie> l2 = saveMovies(l1);
        try {
            createJSON(l2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * преобразует CSV список массивов строк
     * @return  список массивов строк
     */
    public List<String[]> saveToListFromCSV() {
        List<String[]> list = new ArrayList<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(
                path + "movies.csv"))
                .build()) {
            List<String[]> c = csvReader.readAll(); // Для хранения текущей строки из CSV
            for (String[] row : c) {
                String[] strings = row[0].split(";")[0].split(",");
                list.add(strings);
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * Создает список с {@link Movie} для дальнейшего преобразования в JSON
     * @return список {@link Movie}
     */
    private List<Movie> saveMovies(List<String[]> list) {
        List<Movie> movies = new ArrayList<>();
        for(int i = 1; i < list.size(); i++) {
            if(list.get(i).length == 16) {
                Movie movie = new Movie(list.get(i)[0],
                        list.get(i)[1],
                        list.get(i)[2],
                        list.get(i)[3], (list.get(i)[4] + "," + list.get(i)[5]).replaceAll("\"", ""),
                        parseDouble(list.get(i)[6].replaceAll("\"", "")),
                        parseDouble(list.get(i)[7].replaceAll("\"", "")),
                        list.get(i)[8],
                        list.get(i)[9],
                        list.get(i)[10],
                        list.get(i)[11],
                        parseDouble(list.get(i)[12].replaceAll("\"", "")),
                        parseDouble(list.get(i)[13].replaceAll("\"", "")),
                        list.get(i)[14],
                        parseDouble(list.get(i)[15].replaceAll("\"", "")));
                movies.add(movie);
            }
        }
        return movies;
    }

    /**
     * Преобразование списка в JSON. Сохраняет данные в файл.
     * @param movies       список с {@link Movie}
     */
    private void createJSON(List<Movie> movies) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(path + "movies.json"), movies);

    }

    public List<Movie> readJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Movie[] o = mapper.readValue(new File(path + "movies.json"), Movie[].class);
            return Arrays.stream(o).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Безопасный парсинг чисел из String в Double.
     * @param str   строка, которую необходимо преобразовать
     * @return значение Double или 0.0
     */

    private static Double parseDouble(String str) {
        try {
            return str.isEmpty() ? 0.0 : Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
