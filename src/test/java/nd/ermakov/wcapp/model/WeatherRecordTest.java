package nd.ermakov.wcapp.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class WeatherRecordTest {

    @Test
    void testGetSet() {
        WeatherRecord weatherRecord = new WeatherRecord();

        long id = 1;
        String location = "London";
        LocalDate date = LocalDate.now();
        double temeratureC = 2.;
        double windKph = 3.;
        double precipitationMm = 4.;
        double humidity = 6.;
        String weather = "sunny";

        weatherRecord.setId(id);
        weatherRecord.setLocation(location);
        weatherRecord.setDate(date);
        weatherRecord.setTemperatureC(temeratureC);
        weatherRecord.setWindKph(windKph);
        weatherRecord.setPrecipitationMm(precipitationMm);
        weatherRecord.setHumidity(humidity);
        weatherRecord.setWeather(weather);

        assertEquals(id, weatherRecord.getId());
        assertEquals(location, weatherRecord.getLocation());
        assertEquals(date, weatherRecord.getDate());
        assertEquals(temeratureC, weatherRecord.getTemperatureC());
        assertEquals(windKph, weatherRecord.getWindKph());
        assertEquals(precipitationMm, weatherRecord.getPrecipitationMm());
        assertEquals(humidity, weatherRecord.getHumidity());
        assertEquals(weather, weatherRecord.getWeather());
    }
}