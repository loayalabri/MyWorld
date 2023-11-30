package model.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private EventLog testLog;
    private Event event1;
    private Event event2;

    @BeforeEach
    void runBefore() {
        testLog = EventLog.getInstance();
        event1 = new Event("Event 1");
        event2 = new Event("Event 2");
    }

    @Test
    void testLogEvent() {
        testLog.logEvent(event1);
        testLog.logEvent(event2);

        List<Event> list = new ArrayList<>();
        for (Event next : testLog) {
            list.add(next);
        }
        assertTrue(list.contains(event1));
        assertTrue(list.contains(event2));
    }

    @Test
    void testClear() {
        testLog.clear();
        Iterator<Event> itr = testLog.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
