package persistence;


import model.Country;
import model.ListOfCountry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;
import org.json.JSONArray;
import org.json.JSONObject;

/*
Represents reader that reads ListOfCountry from JSON data stored in file
This class is modeled on jsonReader class from JsonSerializationDemo from GitHub
that can be found https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public class JsonReader {
    private String source;

    // EFFECTS: construct a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: read ListOfCountry and returns it
    // Throws IOException if error occurs when trying to read data from file
    public ListOfCountry read() throws IOException, RatingOutOfBoundException, EmptyStringException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfCountry(jsonObject);
    }



    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parse ListOfCountry from jsonObject and returns it
    private ListOfCountry parseListOfCountry(JSONObject jsonObject) throws RatingOutOfBoundException,
            EmptyStringException {
        ListOfCountry loc = new ListOfCountry();
        addCountries(loc, jsonObject);
        return loc;
    }

    // MODIFIES: loc
    // EFFECTS: parses countries from JSON object and adds them to ListOfCountry
    private void addCountries(ListOfCountry loc, JSONObject jsonObject) throws RatingOutOfBoundException,
            EmptyStringException {
        JSONArray jsonArray = jsonObject.getJSONArray("countries");
        for (Object obj : jsonArray) {
            JSONObject nextCountry = (JSONObject) obj;
            addCountry(loc, nextCountry);
        }
    }

    // MODIFIES: loc
    // EFFECTS: parses country from JSON object and adds it to ListOfCountry
    private void addCountry(ListOfCountry loc, JSONObject jsonObject) throws RatingOutOfBoundException,
            EmptyStringException {
        String name = jsonObject.getString("name");
        int rating = jsonObject.getInt("rating");
        String description = jsonObject.getString("description");
        String continent = jsonObject.getString("continent");

        Country country = new Country(name, continent, rating, description);
        loc.addCountry(country);
    }


}
