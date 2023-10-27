package presistance;

import model.Country;
import model.ListOfCountry;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterIllegalFile() {
        try {
            ListOfCountry loc = new ListOfCountry();
            JsonWriter writer = new JsonWriter("./data/illegal\0file:Nmae.json");
            writer.open();
            fail("IOException not caught on testWriterIllegalFile");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyListOfCountry() {
        try {
            ListOfCountry loc = new ListOfCountry();
            JsonWriter writer = new JsonWriter("./data/testWriteEmptyListOfCountry.json");
            writer.open();
            writer.write(loc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptyListOfCountry.json");
            loc = reader.read();
            assertEquals(0, loc.getLength());
        } catch (IOException e) {
            fail("IOException caught on testWriteEmptyListOfCountry");
        }
    }

    @Test
    void testWriteGeneralListOfCountry() {
        try {
            ListOfCountry loc = new ListOfCountry();
            Country canada = new Country("Canada", "North America", 7, "Very cold");
            Country oman = new Country("Oman", "Asia", 10, "Very hot");
            loc.addCountry(canada);
            loc.addCountry(oman);
            JsonWriter writer = new JsonWriter("./data/testWriteGeneralListOfCountry.json");
            writer.open();
            writer.write(loc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteGeneralListOfCountry.json");
            loc = reader.read();
            List<Country> countries = loc.getCountriesVisited();
            assertEquals(2, countries.size());
            checkCountry("Canada", "North America", 7, "Very cold",
                    countries.get(0));
            checkCountry("Oman", "Asia", 10, "Very hot",
                    countries.get(1));

        } catch (IOException e) {
            fail("IOException caught on testWriteGeneralListOfCountry");
        }
    }
}
