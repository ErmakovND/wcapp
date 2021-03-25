package nd.ermakov.wcapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="weather")
public class WeatherRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    private String location;
    private LocalDate date;
    private double temperatureC;
    private double windKph;
    private double precipitationMm;
    private double humidity;
    private String weather;

    public WeatherRecord() { }

    public WeatherRecord(String location, LocalDate date, double temperatureC, double windKph, double precipitationMm, double humidity, String weather) {
        this.location = location;
        this.date = date;
        this.temperatureC = temperatureC;
        this.windKph = windKph;
        this.precipitationMm = precipitationMm;
        this.humidity = humidity;
        this.weather = weather;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public double getWindKph() {
        return windKph;
    }

    public void setWindKph(double windKph) {
        this.windKph = windKph;
    }

    public double getPrecipitationMm() {
        return precipitationMm;
    }

    public void setPrecipitationMm(double precipitationMm) {
        this.precipitationMm = precipitationMm;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
