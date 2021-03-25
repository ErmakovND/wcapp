package nd.ermakov.wcapp.dataload;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.WeatherRecord;
import noNamespace.RootDocument;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherWebXmlLoaderImpl implements WeatherWebXmlLoader {

    private WebLoadClient webLoadClient;
    private DateTimeFormatter requestDateTimeFormatter;
    private String keyApi;

    private String keyApiParameterName = "key";
    private String locationParameterName = "q";
    private String dateParameterName = "dt";
    private String weatherHistoryPath = "/history.xml";

    @Autowired
    public void setWebLoadClient(@Qualifier("weatherLoadClient") WebLoadClient webLoadClient) {
        this.webLoadClient = webLoadClient;
    }

    @Autowired
    public void setRequestDateTimeFormatter(
            @Qualifier("weatherRequestDateTimeFormatter") DateTimeFormatter requestDateTimeFormatter) {
        this.requestDateTimeFormatter = requestDateTimeFormatter;
    }

    @Autowired
    public void setKeyApi(@Qualifier("weatherApiKey") String keyApi) {
        this.keyApi = keyApi;
    }

    private WeatherRecord loadByLocationAndDate(String location, LocalDate date) throws XmlException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add(keyApiParameterName, keyApi);
        params.add(locationParameterName, location);
        params.add(dateParameterName, date.format(requestDateTimeFormatter));

        String forecastXml = webLoadClient.get(weatherHistoryPath, params);
        RootDocument.Root.Forecast.Forecastday.Day weatherDay =
                RootDocument.Factory.parse(forecastXml).getRoot().getForecast().getForecastday().getDay();

        return new WeatherRecord(
                location,
                date,
                weatherDay.getAvgtempC(),
                weatherDay.getMaxwindKph(),
                weatherDay.getTotalprecipMm(),
                weatherDay.getAvgvisKm(),
                weatherDay.getCondition().getText()
        );
    }

    @Override
    public List<WeatherRecord> loadAllByLocationAndDateRange(String location, DateRange range) throws XmlException {
        List<WeatherRecord> result = new ArrayList<>();
        for (LocalDate date : range) {
            result.add(loadByLocationAndDate(location, date));
        }
        return result;
    }
}
