package nd.ermakov.wcapp.controller;

import nd.ermakov.wcapp.service.PredictService;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredictControllerTest {

    private PredictController predictController;

    @Mock
    private PredictService predictService;

    @BeforeEach
    void init() {
        predictController = new PredictController(predictService);
    }

    @Test
    void testPredictRequest() throws ParseException, XmlException {
        when(predictService.predictCurrencyRate()).thenReturn(100D);
        ResponseEntity<?> responseEntity = predictController.getPrediction();
        verify(predictService, times(1)).predictCurrencyRate();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(100D, responseEntity.getBody());
    }
}