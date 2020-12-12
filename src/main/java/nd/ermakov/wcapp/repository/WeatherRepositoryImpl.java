package nd.ermakov.wcapp.repository;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherRepositoryImpl implements WeatherRepository {

    Map<String, Map<LocalDate, WeatherRecord>> storage = new HashMap<>();

    @Override
    public List<WeatherRecord> findAllByLocationAndDateRange(String location, DateRange range) {
        List<WeatherRecord> result = new ArrayList<>();
        if (storage.containsKey(location)) {
            Map<LocalDate, WeatherRecord> map = storage.get(location);
            for (LocalDate date : range) {
                if (map.containsKey(date)) {
                    result.add(map.get(date));
                }
            }
        }
        return result;
    }

    @Override
    public void saveAll(List<WeatherRecord> records) {
        for (WeatherRecord record : records) {
            String location = record.getLocation();
            if (!storage.containsKey(location)) {
                storage.put(location, new HashMap<>());
            }
            storage.get(location).put(record.getDate(), record);
        }
    }
}
