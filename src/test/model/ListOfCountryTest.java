package model;

import model.exceptions.CountryNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ListOfCountryTest {
    ListOfCountry testList;
    Country testCountry1;
    Country testCountry2;
    Country testCountry3;

    @BeforeEach
    void runBefore() {
        testList = new ListOfCountry();
        testCountry1 = makeCountrySafely("Canada", "North America", 7, "");
        testCountry2 = makeCountrySafely("USA", "North America", 8, "");
        testCountry3 = makeCountrySafely("Egypt", "Africa", 9, "");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testList.getLength());
    }

    @Test
    void testAddCountry() {
        testList.addCountry(testCountry1);
        assertEquals(1, testList.getLength());
        assertTrue(testList.getCountriesVisited().contains(testCountry1));
    }

    @Test
    void testAddMultipleCountries() {
        testList.addCountry(testCountry1);
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry3);

        assertEquals(3, testList.getLength());
        assertTrue(testList.getCountriesVisited().contains(testCountry1));
        assertTrue(testList.getCountriesVisited().contains(testCountry2));
        assertTrue(testList.getCountriesVisited().contains(testCountry3));
    }

    @Test
    void testRemoveCountryEmpty() {
        try {
            testList.removeCountry("Oman");
            fail("CountryNotFoundException not caught");
        } catch (CountryNotFoundException e) {
            //pass
        }
    }

    @Test
    void testRemoveCountry() {
        testList.addCountry(testCountry1);

        try {
            testList.removeCountry("Canada");
            assertEquals(0, testList.getLength());
        } catch (CountryNotFoundException e) {
            fail("CountryNotFoundException caught");
        }
    }

    @Test
    void testRemoveCountryFromManyCountries() {
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry3);
        testList.addCountry(testCountry1);

        try {
            testList.removeCountry("Canada");
            assertEquals(2, testList.getLength());
            assertFalse(testList.getCountriesVisited().contains(testCountry1));
            assertTrue(testList.getCountriesVisited().contains(testCountry2));
            assertTrue(testList.getCountriesVisited().contains(testCountry3));
        } catch (CountryNotFoundException e) {
            fail("CountryNotFoundException caught");
        }

    }

    @Test
    void testRemoveCountryDuplicates() {
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry2);

        try {
            testList.removeCountry("USA");
            assertEquals(1, testList.getLength());
            assertTrue(testList.getCountriesVisited().contains(testCountry2));
        } catch (CountryNotFoundException e) {
            fail("CountryNotFoundException caught");
        }
    }



    @Test
    void testGetCountryFromNameNoCountry() {
        try {
            Country result = testList.getCountryFromName("Canada");
            fail("CountryNotFoundException caught");
        } catch (CountryNotFoundException e) {
           // pass
        }
    }

    @Test
    void testGetCountryFromName() {
        testList.addCountry(testCountry3);
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry1);

        try {
            Country result = testList.getCountryFromName("Canada");
            assertEquals(result, testCountry1);
        } catch (CountryNotFoundException e) {
            fail("CountryNotFoundException caught");
        }

    }

    //Helper method to create countries with no exceptions thrown
    private Country makeCountrySafely(String name, String continent, int rating, String desc) {
        Country country;
        try {
            country = new Country(name, continent, rating, desc);
            return country;
        } catch (Exception e) {
            //
        }
        return null;
    }
}

