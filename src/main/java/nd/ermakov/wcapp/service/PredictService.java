package nd.ermakov.wcapp.service;

import org.apache.xmlbeans.XmlException;

import java.text.ParseException;

public interface PredictService {
    double predictCurrencyRate() throws ParseException, XmlException;
}
