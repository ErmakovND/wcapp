package nd.ermakov.wcapp.model;

import java.time.LocalDate;

public class WeatherRecordImpl implements WeatherRecord {

    private String location;
    private LocalDate date;
    private double temperatureC;
    private double windKph;
    private double precipitationMm;
    private double visibilityKm;
    private double humidity;
    private String weather;

    public WeatherRecordImpl(String location, LocalDate date, double temperatureC, double windKph, double precipitationMm, double visibilityKm, double humidity, String weather) {
        this.location = location;
        this.date = date;
        this.temperatureC = temperatureC;
        this.windKph = windKph;
        this.precipitationMm = precipitationMm;
        this.visibilityKm = visibilityKm;
        this.humidity = humidity;
        this.weather = weather;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public double getTemperatureC() {
        return temperatureC;
    }

    @Override
    public double getWindKph() {
        return windKph;
    }

    @Override
    public double getPrecipitationMm() {
        return precipitationMm;
    }

    @Override
    public double getVisibilityKm() {
        return visibilityKm;
    }

    @Override
    public double getHumidity() {
        return humidity;
    }

    @Override
    public String getWeather() {
        return weather;
    }

    @Override
    public String toString() {
        return date + ":\n" +
                "Location - " + location + "\n" +
                "Temperature - " + temperatureC + " C\n" +
                "Wind - " + windKph + " kph\n" +
                "Precipitation - " + precipitationMm + " mm\n" +
                "Visibility - " + visibilityKm + " km\n" +
                "Humidity - " + humidity + " %\n" +
                "Weather - " + weather + "\n";
    }
}
