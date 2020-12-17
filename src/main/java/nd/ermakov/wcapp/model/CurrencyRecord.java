package nd.ermakov.wcapp.model;

import java.time.LocalDate;

public interface CurrencyRecord extends Record {
    double getRate();
    LocalDate getDate();
}
