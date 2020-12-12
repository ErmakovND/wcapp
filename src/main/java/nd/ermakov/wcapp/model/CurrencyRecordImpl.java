package nd.ermakov.wcapp.model;

import java.time.LocalDate;

public class CurrencyRecordImpl implements CurrencyRecord {

    private double rate;
    private LocalDate date;

    public CurrencyRecordImpl(double rate, LocalDate date) {
        this.rate = rate;
        this.date = date;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date + ": " + rate;
    }
}
