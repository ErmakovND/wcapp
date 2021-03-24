package nd.ermakov.wcapp.service;

import java.text.ParseException;

public interface PredictService {
    double predictCurrencyRate() throws ParseException;
}
