package nd.ermakov.wcapp.model;

import java.time.LocalDate;

public interface WeatherRecord extends Record {
    LocalDate getDate();
    String getLocation();
    double getTemperatureC();
    double getWindKph();
    double getPrecipitationMm();
    double getVisibilityKm();
    double getHumidity();
    String getWeather();
}
