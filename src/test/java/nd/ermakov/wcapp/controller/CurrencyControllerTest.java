package nd.ermakov.wcapp.controller;

import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.service.CurrencyService;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyControllerTest {

    private CurrencyController currencyController;

    @Mock
    private CurrencyService currencyService;

    private CurrencyRecord currencyRecord;

    @BeforeEach
    void init() {
        currencyController = new CurrencyController(currencyService);
        currencyRecord = new CurrencyRecord(70, LocalDate.now());
    }

    @Test
    void testCurrencyBadRequest() throws ParseException, XmlException {
        int last = -1;
        ResponseEntity<?> responseEntity = currencyController.getCurrency(last);
        verify(currencyService, times(0)).getLast(last);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testCurrencyCurrentRequest() throws ParseException, XmlException {
        int last = 1;
        when(currencyService.getLast(last)).thenReturn(Collections.singletonList(currencyRecord));
        ResponseEntity<?> responseEntity = currencyController.getCurrency(last);
        verify(currencyService, times(1)).getLast(last);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(currencyRecord, responseEntity.getBody());
    }

    @Test
    void testCurrencyHistoryRequest() throws ParseException, XmlException {
        int last = 2;
        List<CurrencyRecord> currencyRecords = Arrays.asList(currencyRecord, currencyRecord);
        when(currencyService.getLast(last)).thenReturn(currencyRecords);
        ResponseEntity<?> responseEntity = currencyController.getCurrency(last);
        verify(currencyService, times(1)).getLast(last);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertSame(currencyRecords, responseEntity.getBody());
    }

    @Test
    void testCurrencyRequestException() throws ParseException, XmlException {
        int last = 1;
        when(currencyService.getLast(last)).thenThrow(XmlException.class);
        ResponseEntity<?> responseEntity = currencyController.getCurrency(last);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCurrencyRequestNotFound() throws ParseException, XmlException {
        int last = 1;
        when(currencyService.getLast(last)).thenReturn(new ArrayList<>());
        ResponseEntity<?> responseEntity = currencyController.getCurrency(last);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}