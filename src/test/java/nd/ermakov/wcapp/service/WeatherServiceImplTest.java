package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.dataload.WeatherWebXmlLoader;
import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.repository.WeatherRepository;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private WeatherWebXmlLoader weatherWebXmlLoader;

    private WeatherService weatherService;

    @BeforeEach
    void init() {
        weatherService = new WeatherServiceImpl(weatherRepository, weatherWebXmlLoader);
    }

    @Test
    void testGetLast() throws XmlException {
        int last = 2;
        String location = "London";
        DateRange range = new DateRange(LocalDate.now().minusDays(last - 1L), LocalDate.now());
        WeatherRecord weatherRecord = new WeatherRecord(location, LocalDate.now(), 0, 0, 0, 0, 0, "");
        List<WeatherRecord> weatherRecords = Arrays.asList(weatherRecord, weatherRecord);

        when(weatherWebXmlLoader.loadAllByLocationAndDateRange(anyString(), any(DateRange.class)))
                .thenReturn(weatherRecords);
        when(weatherRepository.existsByLocationAndDate(location, LocalDate.now())).thenReturn(true);
        when(weatherRepository.findAllByLocationAndDateBetweenOrderByDateDesc(location, range.getStart(), range.getEnd()))
                .thenReturn(weatherRecords);

        List<WeatherRecord> records = weatherService.getLastByLocation(last, location);
        verify(weatherWebXmlLoader, times(1))
                .loadAllByLocationAndDateRange(anyString(), any(DateRange.class));
        verify(weatherRepository, times(2)).existsByLocationAndDate(location, LocalDate.now());
        assertEquals(weatherRecords, records);
    }
}