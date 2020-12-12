package nd.ermakov.wcapp.repository;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyRepositoryImpl implements CurrencyRepository {

    Map<LocalDate, CurrencyRecord> storage = new HashMap<>();

    @Override
    public List<CurrencyRecord> findAllByDateRange(DateRange range) {
        List<CurrencyRecord> result = new ArrayList<>();
        for (LocalDate date : range) {
            if (storage.containsKey(date)) {
                result.add(storage.get(date));
            }
        }
        return result;
    }

    @Override
    public void saveAll(List<CurrencyRecord> records) {
        for (CurrencyRecord record : records) {
            storage.put(record.getDate(), record);
        }
    }
}
