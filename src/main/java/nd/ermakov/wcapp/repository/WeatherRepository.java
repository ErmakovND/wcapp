package nd.ermakov.wcapp.repository;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;

import java.util.List;

public interface WeatherRepository {
    List<WeatherRecord> findAllByLocationAndDateRange(String location, DateRange range);
    void saveAll(List<WeatherRecord> records);
}
