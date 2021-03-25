package nd.ermakov.wcapp.dataload;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import org.apache.xmlbeans.XmlException;

import java.text.ParseException;
import java.util.List;

public interface CurrencyWebXmlLoader {
    List<CurrencyRecord> loadAllByDateRange(DateRange range) throws XmlException, ParseException;
}
