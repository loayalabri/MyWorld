package model;

import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TestCountry {
    private Country testCountry;
    private Country testCountry2;

    @BeforeEach
    void runBefore() {
        try {
            testCountry = new Country("Canada", "North America", 8, "");
        } catch (Exception e) {
           System.out.println("Exception caught in runBefore");
        }
    }

    @Test
    void testConstructor() {
        assertEquals("Canada", testCountry.getCountryName());
        assertEquals("North America", testCountry.getContinent());
        assertEquals(8, testCountry.getRating());
        assertEquals("", testCountry.getDescription());
    }

    @Test
    void testConstructorEmptyName() {
        try {
            testCountry2 = new Country("", "Asia", 4, "");
        } catch (EmptyStringException e) {
            // pass
        } catch (RatingOutOfBoundException e) {
            fail("EmptyStringException not caught for name");
        }
    }

    @Test
    void testConstructorEmptyContinent() {
        try {
            testCountry2 = new Country("Oman", "", 4, "");
            fail("EmptyStringException not caught for continent");
        } catch (EmptyStringException e) {
            // pass
        } catch (RatingOutOfBoundException e) {
            fail("RatingOutOfBoundException caught ");
        }
    }

    @Test
    void testConstructorEmptyNameAndContinent() {
        try {
            testCountry2 = new Country("", "", 5, "");
            fail("EmptyStringException not caught for continent");
        } catch (EmptyStringException e) {
            //pass
        } catch (RatingOutOfBoundException e) {
            fail("RatingOutOfBoundException caught ");
        }
    }

    @Test
    void testConstructorRatingLessThanZero() {
        try {
            testCountry2 = new Country("Oman", "Asia", -1, "");
            fail("RatingOutOfBoundException not caught");
        } catch (EmptyStringException e) {
            fail("EmptyStringException caught");
        } catch (RatingOutOfBoundException e) {
           //pass
        }
    }

    @Test
    void testConstructorRatingGreaterThanTen() {
        try {
            testCountry2 = new Country("Oman", "Asia", 11, "");
            fail("RatingOutOfBoundException not caught");
        } catch (EmptyStringException e) {
            fail("EmptyStringException caught");
        } catch (RatingOutOfBoundException e) {
            //pass
        }
    }

    @Test
    void testCreateDescription() {
        testCountry.setDescription("Canada is one of the best countries I visited");
        assertEquals("Canada is one of the best countries I visited", testCountry.getDescription());
    }

    @Test
    void testSetRating() {
        try {
            testCountry.setRating(6);
            assertEquals(6, testCountry.getRating());
        } catch (RatingOutOfBoundException e) {
            fail("RatingOutOfBoundException caught");
        }
    }

    @Test
    void testSetRatingLessThanZero() {
        try {
            testCountry.setRating(-1);
            fail("RatingOutOfBoundException not caught");
        } catch (RatingOutOfBoundException e) {
            assertEquals(8, testCountry.getRating());
        }
    }

    @Test
    void testSetRatingMoreThanTen() {
        try {
            testCountry.setRating(11);
            fail("RatingOutOfBoundException not caught");
        } catch (RatingOutOfBoundException e) {
            assertEquals(8, testCountry.getRating());
        }
    }

    @Test
    void testSetRatingToZero() {
        try {
            testCountry.setRating(0);
            assertEquals(0, testCountry.getRating());
        } catch (RatingOutOfBoundException e) {
            fail("RatingOutOfBoundException caught");
        }
    }

    @Test
    void testSetRatingToTen() {
        try {
            testCountry.setRating(10);
            assertEquals(10, testCountry.getRating());
        } catch (RatingOutOfBoundException e) {
            fail("RatingOutOfBoundException caught");
        }
    }

    @Test
    void testSetRatingMultipleTimes() {
        try {
            testCountry.setRating(4);
            testCountry.setRating(3);
            testCountry.setRating(23);
        } catch (RatingOutOfBoundException e) {
            assertEquals(3, testCountry.getRating());
        }
    }

    @Test
    void testToString() {
        assertEquals("Canada", testCountry.toString());
    }
}