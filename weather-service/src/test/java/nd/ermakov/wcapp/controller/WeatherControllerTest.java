package nd.ermakov.wcapp.controller;

import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.service.WeatherService;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    private WeatherRecord weatherRecord;

    @BeforeEach
    void init() {
        weatherController = new WeatherController(weatherService);
        weatherRecord = new WeatherRecord(
                "",
                LocalDate.now(),
                0,
                0,
                0,
                0,
                "");
    }

    @Test
    void testWeatherBadRequest() throws XmlException {
        String location = "foo";
        int last = 1;
        Mockito.when(weatherService.getLastByLocation(last, location)).thenReturn(new ArrayList<>());
        ResponseEntity<?> responseEntity = weatherController.getWeather(location, last);
        Mockito.verify(weatherService, Mockito.times(1)).getLastByLocation(last, location);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testWeatherCurrentRequest() throws XmlException {
        String location = "London";
        int last = 1;
        Mockito.when(weatherService.getLastByLocation(last, location)).thenReturn(Collections.singletonList(weatherRecord));
        ResponseEntity<?> responseEntity = weatherController.getWeather(location, last);
        Mockito.verify(weatherService, Mockito.times(1)).getLastByLocation(last, location);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(weatherRecord, responseEntity.getBody());
    }

    @Test
    void testWeatherHistoryRequest() throws XmlException {
        String location = "London";
        int last = 2;
        List<WeatherRecord> weatherRecords = Arrays.asList(weatherRecord, weatherRecord);
        Mockito.when(weatherService.getLastByLocation(last, location)).thenReturn(weatherRecords);
        ResponseEntity<?> responseEntity = weatherController.getWeather(location, last);
        Mockito.verify(weatherService, Mockito.times(1)).getLastByLocation(last, location);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(weatherRecords, responseEntity.getBody());
    }

    @Test
    void testWeatherRequestException() throws XmlException {
        String location = "";
        int last = 1;
        Mockito.when(weatherService.getLastByLocation(last, location)).thenThrow(XmlException.class);
        ResponseEntity<?> responseEntity = weatherController.getWeather(location, last);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}