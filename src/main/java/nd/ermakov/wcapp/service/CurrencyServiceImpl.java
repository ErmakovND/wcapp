package nd.ermakov.wcapp.service;

import nd.ermakov.wcapp.dataload.CurrencyWebXmlLoader;
import nd.ermakov.wcapp.date.DateRange;
import nd.ermakov.wcapp.model.CurrencyRecord;
import nd.ermakov.wcapp.repository.CurrencyRepository;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;
    private CurrencyWebXmlLoader currencyLoader;

    private DateRange savedDateRange = DateRange.empty();

    @Autowired
    public void setCurrencyRepository(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Autowired
    public void setCurrencyLoader(CurrencyWebXmlLoader currencyLoader) {
        this.currencyLoader = currencyLoader;
    }

    @Override
    public List<CurrencyRecord> getLast(Integer last) throws ParseException, XmlException {
        DateRange range = new DateRange(LocalDate.now().minusDays(last - 1), LocalDate.now());
        if (!range.in(savedDateRange)) {
            currencyRepository.saveAll(currencyLoader.loadAllByDateRange(range));
            savedDateRange = range;
        }
        return currencyRepository.findAllByDateRange(range);
    }
}
