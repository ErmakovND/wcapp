package nd.ermakov.wcapp.controller;

import nd.ermakov.wcapp.service.PredictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/predict")
public class PredictController {

    private PredictService predictService;

    public PredictController(PredictService predictService) {
        this.predictService = predictService;
    }

    @GetMapping
    public ResponseEntity<Object> getPrediction() {
        System.out.println("trying to predict");
        try {
            return new ResponseEntity<>(predictService.predictCurrencyRate(), HttpStatus.OK);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
