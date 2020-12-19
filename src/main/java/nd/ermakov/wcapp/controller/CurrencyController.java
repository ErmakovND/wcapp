package nd.ermakov.wcapp.controller;

import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.service.CurrencyService;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController()
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<?> getCurrency(@RequestParam(defaultValue = "1") Integer last) {
        if (last < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<CurrencyRecord> currencyRecords = currencyService.getLast(last);
            if (currencyRecords.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(last == 1 ? currencyRecords.get(0) : currencyRecords, HttpStatus.OK);
        } catch (ParseException | XmlException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
