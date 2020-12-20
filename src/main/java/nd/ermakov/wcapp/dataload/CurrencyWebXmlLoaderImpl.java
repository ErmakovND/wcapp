package nd.ermakov.wcapp.dataload;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.format.DoubleFormat;
import nd.ermakov.wcapp.model.CurrencyRecord;
import noNamespace.ValCursDocument;
import noNamespace.ValutaDocument;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CurrencyWebXmlLoaderImpl implements CurrencyWebXmlLoader {

    private WebLoadClient webLoadClient;
    private DateTimeFormatter requestDateTimeFormatter;
    private DateTimeFormatter responseDateTimeFormatter;
    private String currencyName;

    private String currencyCodesPath = "/scripts/XML_val.asp";
    private String currencyValuesPath = "/scripts/XML_dynamic.asp";
    private String currencyCodeParameterName = "VAL_NM_RQ";
    private String startDateParameterName = "date_req1";
    private String endDateParameterName = "date_req2";

    @Autowired
    public void setWebLoadClient(@Qualifier("currencyLoadClient") WebLoadClient webLoadClient) {
        this.webLoadClient = webLoadClient;
    }

    @Autowired
    public void setRequestDateTimeFormatter(
            @Qualifier("currencyRequestDateTimeFormatter") DateTimeFormatter requestDateTimeFormatter) {
        this.requestDateTimeFormatter = requestDateTimeFormatter;
    }

    @Autowired
    public void setResponseDateTimeFormatter(
            @Qualifier("currencyResponseDateTimeFormatter") DateTimeFormatter responseDateTimeFormatter) {
        this.responseDateTimeFormatter = responseDateTimeFormatter;
    }

    @Autowired
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    private String loadCode() throws XmlException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("d", "0");

        String valutaXml = webLoadClient.get(currencyCodesPath, params);
        ValutaDocument.Valuta valuta = ValutaDocument.Factory.parse(valutaXml).getValuta();

        for (ValutaDocument.Valuta.Item currencyItem:
                valuta.getItemArray()) {
            if (currencyItem.getEngName().equals(currencyName)) {
                return currencyItem.getID();
            }
        }
        return null;
    }

    @Override
    public List<CurrencyRecord> loadAllByDateRange(DateRange range) throws XmlException, ParseException {
        String currencyCode = loadCode();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(startDateParameterName, range.getStart().format(requestDateTimeFormatter));
        params.add(endDateParameterName, range.getEnd().format(requestDateTimeFormatter));
        params.add(currencyCodeParameterName, currencyCode);

        String valCursXml = webLoadClient.get(currencyValuesPath, params);
        ValCursDocument.ValCurs valCurs = ValCursDocument.Factory.parse(valCursXml).getValCurs();
        List<CurrencyRecord> result = new ArrayList<>();
        for (ValCursDocument.ValCurs.Record valCursRecord:
                valCurs.getRecordArray()) {
            result.add(new CurrencyRecord(
                    DoubleFormat.parse(valCursRecord.getValue()),
                    LocalDate.parse(valCursRecord.getDate(), responseDateTimeFormatter))
            );
        }
        return result;
    }
}