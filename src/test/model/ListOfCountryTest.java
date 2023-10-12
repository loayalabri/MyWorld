package model;

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
        testCountry1 = new Country("Canada", "North America", 7);
        testCountry2 = new Country("USA", "North America", 8);
        testCountry3 = new Country("Egypt", "Africa", 9);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testList.getLength());
    }

    @Test
    void testAddCountry() {
        testList.addCountry(testCountry1);
        assertEquals(1, testList.getLength());
        assertTrue(testList.countriesVisited.contains(testCountry1));
    }

    @Test
    void testAddMultipleCountries() {
        testList.addCountry(testCountry1);
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry3);

        assertEquals(3, testList.getLength());
        assertTrue(testList.countriesVisited.contains(testCountry1));
        assertTrue(testList.countriesVisited.contains(testCountry2));
        assertTrue(testList.countriesVisited.contains(testCountry3));
    }

    @Test
    void testRemoveCountry() {
        testList.addCountry(testCountry1);

        testList.removeCountry("Canada");
        assertEquals(0, testList.getLength());
    }

    @Test
    void testRemoveCountryFromManyCountries() {
        testList.addCountry(testCountry1);
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry3);

        testList.removeCountry("Canada");
        assertEquals(2, testList.getLength());
        assertFalse(testList.countriesVisited.contains(testCountry1));
        assertTrue(testList.countriesVisited.contains(testCountry2));
        assertTrue(testList.countriesVisited.contains(testCountry3));
    }

    @Test
    void testRemoveCountryDuplicates() {
        testList.addCountry(testCountry2);
        testList.addCountry(testCountry2);

        testList.removeCountry("USA");
        assertEquals(1, testList.getLength());
        assertTrue(testList.countriesVisited.contains(testCountry2));
    }
}
