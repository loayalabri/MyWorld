package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class countryTest {
    private Country testCountry;

    @BeforeEach
    void runBefore() {
        testCountry = new Country("Canada", "North America", 8);
    }

    @Test
    void testConstructor() {
        assertEquals("Canada", testCountry.getCountryName());
        assertEquals("North America", testCountry.getContinent());
        assertEquals(8, testCountry.getRating());
    }

    @Test
    void testCreateDescribtion() {
        testCountry.createDescription("Canada is one of the best countries I visited");
        assertEquals("Canada is one of the best countries I visited", testCountry.getDescription());
    }

    @Test
    void testSetRating() {
        testCountry.setRating(6);
        assertEquals(6, testCountry.getRating());
    }
}