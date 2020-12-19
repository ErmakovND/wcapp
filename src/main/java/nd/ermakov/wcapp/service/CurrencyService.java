package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.model.CurrencyRecord;
import org.apache.xmlbeans.XmlException;

import java.text.ParseException;
import java.util.List;

public interface CurrencyService {
    List<CurrencyRecord> getLast(Integer last) throws ParseException, XmlException;
}
