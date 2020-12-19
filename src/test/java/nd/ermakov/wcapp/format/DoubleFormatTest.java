package nd.ermakov.wcapp.format;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class DoubleFormatTest {

    @Test
    void parse() throws ParseException {
        assertEquals(1.2D, DoubleFormat.parse("1,2"));
    }
}