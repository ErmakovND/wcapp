package nd.ermakov.wcapp.dataload;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import noNamespace.ValCursDocument;
import noNamespace.ValutaDocument;
import org.apache.xmlbeans.XmlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyWebXmlLoaderImplTest {

    @Mock
    private WebLoadClient webLoadClient;

    private DateTimeFormatter requestDateTimeFormatter;
    private DateTimeFormatter responseDateTimeFormatter;
    private String currencyName;

    private ValutaDocument valutaDocument;
    private ValCursDocument valCursDocument;

    private CurrencyWebXmlLoaderImpl currencyWebXmlLoader;

    @BeforeEach
    void init() {
        requestDateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        responseDateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        currencyName = "US Dollar";

        currencyWebXmlLoader = new CurrencyWebXmlLoaderImpl();
        currencyWebXmlLoader.setCurrencyName(currencyName);
        currencyWebXmlLoader.setRequestDateTimeFormatter(requestDateTimeFormatter);
        currencyWebXmlLoader.setResponseDateTimeFormatter(responseDateTimeFormatter);
        currencyWebXmlLoader.setWebLoadClient(webLoadClient);

        valutaDocument = ValutaDocument.Factory.newInstance();
        ValutaDocument.Valuta valuta = ValutaDocument.Valuta.Factory.newInstance();
        ValutaDocument.Valuta.Item item = ValutaDocument.Valuta.Item.Factory.newInstance();
        item.setEngName(currencyName);
        item.setID("id");
        valuta.addNewItem();
        valuta.setItemArray(0, item);
        valutaDocument.setValuta(valuta);

        valCursDocument = ValCursDocument.Factory.newInstance();
        ValCursDocument.ValCurs valCurs = ValCursDocument.ValCurs.Factory.newInstance();
        ValCursDocument.ValCurs.Record record = ValCursDocument.ValCurs.Record.Factory.newInstance();
        record.setId("id");
        record.setValue("36.6");
        record.setDate("01.01.2000");
        valCurs.addNewRecord();
        valCurs.setRecordArray(0, record);
        valCursDocument.setValCurs(valCurs);
    }

    @Test
    void testLoad() throws ParseException, XmlException {
        when(webLoadClient.get(eq("/scripts/XML_val.asp"), any(MultiValueMap.class)))
                .thenReturn(valutaDocument.xmlText());
        when(webLoadClient.get(eq("/scripts/XML_dynamic.asp"), any(MultiValueMap.class)))
                .thenReturn(valCursDocument.xmlText());
        List<CurrencyRecord> records = currencyWebXmlLoader
                .loadAllByDateRange(new DateRange(LocalDate.now(), LocalDate.now()));
        verify(webLoadClient, times(1))
                .get(eq("/scripts/XML_val.asp"), any(MultiValueMap.class));
        verify(webLoadClient, times(1))
                .get(eq("/scripts/XML_dynamic.asp"), any(MultiValueMap.class));
        assertEquals(1, records.size());
        assertEquals(36.6, records.get(0).getRate());
    }
}