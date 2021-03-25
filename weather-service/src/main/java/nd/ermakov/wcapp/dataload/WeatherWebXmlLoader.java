package nd.ermakov.wcapp.dataload;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;
import org.apache.xmlbeans.XmlException;

import java.util.List;

public interface WeatherWebXmlLoader {
    List<WeatherRecord> loadAllByLocationAndDateRange(String location, DateRange range) throws XmlException;
}
