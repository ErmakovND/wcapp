package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.dataload.WebLoadClient;
import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.regression.RegressionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.*;

@Service
public class PredictServiceImpl implements PredictService {

    private final int LAST = 8;

    private WebLoadClient currencyClient;
    private WebLoadClient weatherClient;

    private RegressionModel regressionModel;

    public PredictServiceImpl(RegressionModel regressionModel) {
        this.regressionModel = regressionModel;
    }

    @Autowired
    public void setCurrencyClient(@Qualifier("currencyServiceClient") WebLoadClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Autowired
    public void setWeatherClient(@Qualifier("weatherServiceClient") WebLoadClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @Override
    public double predictCurrencyRate() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("last", String.valueOf(LAST));
        CurrencyRecord[] currencyRecords = currencyClient.getAsClass("", params, CurrencyRecord[].class);
        WeatherRecord[] weatherRecords = weatherClient.getAsClass("", params, WeatherRecord[].class);
        Map<LocalDate, CurrencyRecord> currencyRecordMap  = new HashMap<>();
        for (CurrencyRecord currency :
                currencyRecords) {
            currencyRecordMap.put(currency.getDate(), currency);
        }
        Map<LocalDate, WeatherRecord> weatherRecordMap  = new HashMap<>();
        for (WeatherRecord weather :
                weatherRecords) {
            weatherRecordMap.put(weather.getDate(), weather);
        }
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
        return n == 0 ? sum : sum / n;
    }
}
