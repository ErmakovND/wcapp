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

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;
    private CurrencyWebXmlLoader currencyLoader;

    private DateRange savedDateRange = DateRange.empty();

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyWebXmlLoader currencyLoader) {
        this.currencyRepository = currencyRepository;
        this.currencyLoader = currencyLoader;
    }

    @Override
    public List<CurrencyRecord> getLast(Integer last) throws ParseException, XmlException {
        DateRange range = new DateRange(LocalDate.now().minusDays(last - 1L), LocalDate.now());
        if (!range.in(savedDateRange)) {
            for (CurrencyRecord currency :
                    currencyLoader.loadAllByDateRange(range)) {
                if (!currencyRepository.existsByDate(currency.getDate())) {
                    currencyRepository.save(currency);
                }
            }
            savedDateRange = range;
        }
        return currencyRepository
                .findAllByDateBetweenOrderByDateDesc(range.getStart(), range.getEnd());
    }
}
