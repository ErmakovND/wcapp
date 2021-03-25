package nd.ermakov.wcapp.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class DoubleFormat {

    private static final DecimalFormat df;

    static {
        df = new DecimalFormat();
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(formatSymbols);
    }

    public static double parse(String s) throws ParseException {
        return (double)df.parse(s);
    }
}
