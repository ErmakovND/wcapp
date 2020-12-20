package nd.ermakov.wcapp.dataload;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;
import noNamespace.RootDocument;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherWebXmlLoaderImplTest {

    @Mock
    private WebLoadClient webLoadClient;

    private DateTimeFormatter requestDateTimeFormatter;
    private String keyApi;

    private RootDocument rootDocument;

    private WeatherWebXmlLoaderImpl weatherWebXmlLoader;

    @BeforeEach
    void init() {
        requestDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        keyApi = "";

        weatherWebXmlLoader = new WeatherWebXmlLoaderImpl();
        weatherWebXmlLoader.setKeyApi(keyApi);
        weatherWebXmlLoader.setRequestDateTimeFormatter(requestDateTimeFormatter);
        weatherWebXmlLoader.setWebLoadClient(webLoadClient);

        rootDocument = RootDocument.Factory.newInstance();
        RootDocument.Root root = RootDocument.Root.Factory.newInstance();
        RootDocument.Root.Forecast forecast = RootDocument.Root.Forecast.Factory.newInstance();
        RootDocument.Root.Forecast.Forecastday forecastday =
                RootDocument.Root.Forecast.Forecastday.Factory.newInstance();
        RootDocument.Root.Forecast.Forecastday.Day day =
                RootDocument.Root.Forecast.Forecastday.Day.Factory.newInstance();
        RootDocument.Root.Forecast.Forecastday.Day.Condition condition =
                RootDocument.Root.Forecast.Forecastday.Day.Condition.Factory.newInstance();
        condition.setText("sunny");
        day.setAvgtempC(36.6F);
        day.setCondition(condition);
        forecastday.setDay(day);
        forecast.setForecastday(forecastday);
        root.setForecast(forecast);
        rootDocument.setRoot(root);
    }

    @Test
    void testLoad() throws XmlException {
        String location = "London";
        when(webLoadClient.get(eq("/history.xml"), any(MultiValueMap.class)))
                .thenReturn(rootDocument.xmlText());
        List<WeatherRecord> weatherRecords = weatherWebXmlLoader
                .loadAllByLocationAndDateRange(location, new DateRange(LocalDate.now(), LocalDate.now()));
        verify(webLoadClient, times(1))
                .get(eq("/history.xml"), any(MultiValueMap.class));
        assertEquals(1, weatherRecords.size());
        WeatherRecord weatherRecord = weatherRecords.get(0);
        String weather = weatherRecord.getWeather();
        double temp = weatherRecord.getTemperatureC();
        assertEquals("sunny", weather);
        assertTrue(temp > 36.59);
        assertTrue(temp < 36.61);
    }
}