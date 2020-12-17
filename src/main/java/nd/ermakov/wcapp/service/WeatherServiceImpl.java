package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.dataload.WeatherWebXmlLoader;
import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.repository.WeatherRepository;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    private WeatherRepository weatherRepository;
    private WeatherWebXmlLoader weatherLoader;

    private DateRange savedDateRange = DateRange.empty();

    public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherWebXmlLoader weatherLoader) {
        this.weatherRepository = weatherRepository;
        this.weatherLoader = weatherLoader;
    }

    @Override
    public List<WeatherRecord> getLastByLocation(Integer last, String location) throws XmlException {
        DateRange range = new DateRange(LocalDate.now().minusDays(last - 1), LocalDate.now());
        if (!range.in(savedDateRange)) {
            for (WeatherRecord weather :
                    weatherLoader.loadAllByLocationAndDateRange(location, range)) {
                if (!weatherRepository.existsByLocationAndDate(weather.getLocation(), weather.getDate())) {
                    weatherRepository.save(weather);
                }
            }
            savedDateRange = range;
        }
        return weatherRepository
                .findAllByLocationAndDateBetweenOrderByDateDesc(location, range.getStart(), range.getEnd());
    }
}
