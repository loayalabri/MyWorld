package persistence;

import model.ListOfCountry;
import org.json.JSONObject;
import java.io.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
Represents a writer that write JSON representation to ListOfCountry
This class is modeled on jsonWriter class from JsonSerializationDemo from GitHub
that can be found https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: Constructs a writer to write to given destination.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of ListOfCountry to file
    public void write(ListOfCountry myWorld) {
        JSONObject json = myWorld.toJason();
        saveToFile(json.toString(TAB));
    }

    // EFFECTS: Write given string to file
    private void saveToFile(String jsonString) {
        writer.print(jsonString);
    }

    // EFFECTS: close the writer
    public void close() {
        writer.close();
    }



}
