package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.model.WeatherRecord;
import org.apache.xmlbeans.XmlException;

import java.util.List;

public interface WeatherService {
    List<WeatherRecord> getLastByLocation(Integer last, String location) throws XmlException;
}
