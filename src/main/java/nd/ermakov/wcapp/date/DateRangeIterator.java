package nd.ermakov.wcapp.date;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.NoSuchElementException;

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
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        currentDate = currentDate.plusDays(1);
        return currentDate.minusDays(1);
    }
}
