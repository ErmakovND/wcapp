package nd.ermakov.wcapp.date;

import java.time.LocalDate;
import java.util.Iterator;

public class DateRange implements Iterable<LocalDate> {

    private LocalDate start;
    private LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public boolean in(DateRange other) {
        if (other == null || other.isEmpty()) {
            return false;
        }
        if (this.isEmpty()) {
            return true;
        }
        return !start.isBefore(other.start) && !end.isAfter(other.end);
    }

    public boolean isEmpty() {
        if (start == null || end == null) {
            return true;
        }
        return start.isAfter(end);
    }

    public static DateRange empty() {
        return new DateRange(null, null);
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new DateRangeIterator(this);
    }
}
