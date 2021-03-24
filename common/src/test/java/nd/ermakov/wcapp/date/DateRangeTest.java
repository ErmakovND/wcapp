package nd.ermakov.wcapp.date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeTest {
    private LocalDate now;

    @BeforeEach
    void setUp() {
        now = LocalDate.now();
    }

    @Test
    void isEmpty() {
        assertTrue(DateRange.empty().isEmpty());
        assertTrue(new DateRange(now, now.minusDays(1)).isEmpty());
    }

    @Test
    void in() {
        DateRange empty = DateRange.empty();
        DateRange range = new DateRange(now, now);
        assertFalse(empty.in(null));
        assertFalse(empty.in(empty));
        assertTrue(empty.in(range));
        assertTrue(range.in(range));
    }
    
    @Test
    void iterate() {
        int len = 5;
        for (LocalDate date :
                new DateRange(now.minusDays(len), now)) {
            len--;
        }
        assertEquals(-1, len);
    }
}