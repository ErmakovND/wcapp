package nd.ermakov.wcapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyRecordTest {

    @Test
    void testGetSet() {
        CurrencyRecord currencyRecord = new CurrencyRecord();

        long id = 1;
        double rate = 70;
        LocalDate date = LocalDate.now();

        currencyRecord.setId(id);
        currencyRecord.setDate(date);
        currencyRecord.setRate(rate);

        assertEquals(id, currencyRecord.getId());
        assertEquals(rate, currencyRecord.getRate());
        assertEquals(date, currencyRecord.getDate());
    }

}