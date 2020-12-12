package nd.ermakov.wcapp.controller;

import nd.ermakov.wcapp.model.WeatherRecord;
import nd.ermakov.wcapp.service.WeatherService;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public void setWeatherService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam(defaultValue = "Moscow") String location,
                                        @RequestParam(defaultValue = "1") Integer last) {
        try {
            List<WeatherRecord> weatherRecords = weatherService.getLastByLocation(last, location);
            return new ResponseEntity<>(last == 1 ? weatherRecords.get(0) : weatherRecords, HttpStatus.OK);
        } catch (XmlException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
