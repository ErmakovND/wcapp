package nd.ermakov.wcapp.date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {
    @Test
    void isEmpty() {
        assertTrue(DateRange.empty().isEmpty());
    }
}