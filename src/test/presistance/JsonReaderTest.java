package presistance;


import model.Country;
import model.ListOfCountry;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/*
The tests in this class are modeled on jsonReaderTest class from JsonSerializationDemo from GitHub
that can be found https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */

public class JsonReaderTest extends JsonTest{


    @Test
    void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/notRealFile.json");
        try {
            ListOfCountry loc = reader.read();
            fail("IOException not caught on test testReaderNoFile");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyListOfCountry() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfCountry.json");
        try {
            ListOfCountry loc = reader.read();
            List<Country> countries = loc.getCountriesVisited();
            assertEquals(0, countries.size());
        } catch (IOException e) {
            fail("IOException caught on testReaderEmptyListOfCountry");
        }
    }

    @Test
    void testReaderGeneralListOfCountry() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfCountry.json");
        try {
            ListOfCountry loc = reader.read();
            List<Country> countries = loc.getCountriesVisited();
            assertEquals(3, countries.size());
            checkCountry("USA", "North America", 6, "Beautiful and diverse",
                    countries.get(0));
            checkCountry("Oman", "Asia", 10, "Kind people",
                    countries.get(1));
            checkCountry("Egypt", "Africa", 7, "Great history",
                    countries.get(2));
        } catch (IOException e) {
            fail("IOException caught on testReaderGeneralListOfCountry");
        }
    }
}
