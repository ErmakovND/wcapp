package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.regression.RegressionModel;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredictServiceImplTest {

    @Mock
    private CurrencyService currencyService;
    @Mock
    private WeatherService weatherService;
    @Mock
    private RegressionModel regressionModel;

    private PredictService predictService;

    @BeforeEach
    void init() {
        predictService = new PredictServiceImpl(currencyService, weatherService, regressionModel);
    }

    @Test
    void testPredict() throws ParseException, XmlException {
        String location = "Moscow";
        CurrencyRecord currencyRecord = new CurrencyRecord(70, LocalDate.now());
        WeatherRecord weatherRecord = new WeatherRecord(location, LocalDate.now(), 0, 0, 0, 0, 0, "");

        when(currencyService.getLast(8)).thenReturn(Collections.singletonList(currencyRecord));
        when(weatherService.getLastByLocation(8, location)).thenReturn(Collections.singletonList(weatherRecord));
        when(regressionModel.predict(any(Double[].class))).thenReturn(70D);

        double rate = predictService.predictCurrencyRate();
        verify(currencyService, times(1)).getLast(8);
        verify(weatherService, times(1)).getLastByLocation(8, location);
        verify(regressionModel, times(1)).fit(any(Double[][].class), any(Double[].class));
        assertEquals(70D, rate);
    }
}