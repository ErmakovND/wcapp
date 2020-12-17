package nd.ermakov.wcapp.repository;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;

import java.util.List;

public interface CurrencyRepository {
    List<CurrencyRecord> findAllByDateRange(DateRange range);
    void saveAll(List<CurrencyRecord> records);
}