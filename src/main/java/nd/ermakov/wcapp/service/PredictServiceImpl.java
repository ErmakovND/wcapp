package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.regression.RegressionModel;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Service
public class PredictServiceImpl implements PredictService {

    private CurrencyService currencyService;
    private WeatherService weatherService;

    private RegressionModel regressionModel = new RegressionModel();

    public PredictServiceImpl(CurrencyService currencyService, WeatherService weatherService) {
        this.currencyService = currencyService;
        this.weatherService = weatherService;
    }

    @Override
    public double predictCurrencyRate() throws ParseException, XmlException {
        List<CurrencyRecord> currencyRecords = currencyService.getLast(8);
        List<WeatherRecord> weatherRecords = weatherService.getLastByLocation(8, "Moscow");
        Map<LocalDate, CurrencyRecord> currencyRecordMap  = new HashMap<>();
        currencyRecords.forEach(currencyRecord -> currencyRecordMap.put(currencyRecord.getDate(), currencyRecord));
        Map<LocalDate, WeatherRecord> weatherRecordMap  = new HashMap<>();
        weatherRecords.forEach(weatherRecord -> weatherRecordMap.put(weatherRecord.getDate(), weatherRecord));
        Double[][] features = new Double[8][3];
        int i = 0;
        for (LocalDate date : new DateRange(LocalDate.now().minusDays(7), LocalDate.now())) {
            if (currencyRecordMap.containsKey(date)) {
                features[i][0] = currencyRecordMap.get(date).getRate();
            }
            if (weatherRecordMap.containsKey(date)) {
                features[i][1] = weatherRecordMap.get(date).getTemperatureC();
                features[i][2] = weatherRecordMap.get(date).getPrecipitationMm();
            }
            i++;
        }
        interpolate(features);
        regressionModel.fit(extractTrainX(features), extractTrainY(features));
        return regressionModel.predict(extractTestX(features));
    }

    private void interpolate(Double[][] x) {
        Double[] means = new Double[x[0].length];
        for (int i = 0; i < means.length; i++) {
            means[i] = mean(extractFeature(i, x));
        }
        for (int j = 0; j < x[0].length; j++) {
            for (int i = 0; i < x.length; i++) {
                if (x[i][j] == null) {
                    x[i][j] = means[j];
                }
            }
        }
    }

    private Double[] extractFeature(int n, Double[][] x) {
        Double[] f = new Double[x.length];
        for (int i = 1; i < x.length; i++) {
            f[i] = x[i][n];
        }
        return f;
    }

    private Double[] extractTestX(Double[][] x) {
        return x[x.length - 1];
    }

    private Double[] extractTrainY(Double[][] x) {
        Double[] y = new Double[x.length - 1];
        for (int i = 1; i < x.length; i++) {
            y[i - 1] = x[i][0];
        }
        return y;
    }

    private Double[][] extractTrainX(Double[][] x) {
        Double[][] train = new Double[x.length - 1][x[0].length];
        System.arraycopy(x, 0, train, 0, x.length - 1);
        return train;
    }

    private double mean(Double[] x) {
        int n = 0;
        double sum = 0;
        for (Double d : x) {
            if (d != null) {
                n++;
                sum += d;
            }
        }
        return sum / n;
    }
}
