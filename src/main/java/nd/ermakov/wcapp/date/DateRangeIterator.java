package nd.ermakov.wcapp.date;

import java.time.LocalDate;
import java.util.Iterator;

public class DateRangeIterator implements Iterator<LocalDate> {

    private DateRange range;
    private LocalDate currentDate;

    public DateRangeIterator(DateRange range) {
        this.range = range;
        currentDate = range.getStart();
    }

    @Override
    public boolean hasNext() {
        return !currentDate.isAfter(range.getEnd());
    }

    @Override
    public LocalDate next() {
        currentDate = currentDate.plusDays(1);
        return currentDate.minusDays(1);
    }
}
