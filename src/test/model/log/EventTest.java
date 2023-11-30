package model.log;

import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event testEvent1;
    private Date testEventDate;

    @BeforeEach
    void runBefore() {
        testEvent1 = new Event("Country added");
        testEventDate = Calendar.getInstance().getTime();

    }

    @Test
    void testConstructor() {
        assertEquals("Country added", testEvent1.getDescription());
        assertEquals(testEventDate.toString(), testEvent1.getDate().toString());
        assertTrue(Math.abs(testEventDate.getTime() - testEvent1.getDate().getTime()) < 10);
    }

    @Test
    void testToString() {
        assertEquals(testEventDate.toString() + "\n" + "Country added", testEvent1.toString());
    }
}
