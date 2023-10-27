package presistance;

import model.Country;


import static org.junit.jupiter.api.Assertions.*;


public class JsonTest {
    protected void checkCountry(String name, String continent, int rating, String desc, Country country) {
        assertEquals(name, country.getCountryName());
        assertEquals(continent, country.getContinent());
        assertEquals(rating, country.getRating());
        assertEquals(desc, country.getDescription());
    }

}
