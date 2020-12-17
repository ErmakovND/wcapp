package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.model.CurrencyRecord;
import org.apache.xmlbeans.XmlException;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CurrencyService {
    List<CurrencyRecord> getLast(Integer last) throws ParseException, XmlException;
}
