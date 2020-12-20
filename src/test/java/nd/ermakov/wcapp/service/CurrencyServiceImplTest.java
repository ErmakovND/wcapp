package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.dataload.CurrencyWebXmlLoader;
import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.repository.CurrencyRepository;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CurrencyWebXmlLoader currencyWebXmlLoader;

    private CurrencyService currencyService;

    @BeforeEach
    void init() {
        currencyService = new CurrencyServiceImpl(currencyRepository, currencyWebXmlLoader);
    }

    @Test
    void testGetLast() throws ParseException, XmlException {
        int last = 2;
        DateRange range = new DateRange(LocalDate.now().minusDays(last - 1L), LocalDate.now());
        CurrencyRecord currencyRecord = new CurrencyRecord(70, LocalDate.now());
        List<CurrencyRecord> currencyRecords = Arrays.asList(currencyRecord, currencyRecord);

        when(currencyWebXmlLoader.loadAllByDateRange(any(DateRange.class))).thenReturn(currencyRecords);
        when(currencyRepository.existsByDate(LocalDate.now())).thenReturn(true);
        when(currencyRepository.findAllByDateBetweenOrderByDateDesc(range.getStart(), range.getEnd()))
                .thenReturn(currencyRecords);

        List<CurrencyRecord> records = currencyService.getLast(last);
        verify(currencyWebXmlLoader, times(1)).loadAllByDateRange(any(DateRange.class));
        verify(currencyRepository, times(2)).existsByDate(LocalDate.now());
        assertEquals(currencyRecords, records);
    }

}