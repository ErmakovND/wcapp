package nd.ermakov.wcapp.format;

import java.text.NumberFormat;
import java.text.ParseException;

public class DoubleFormat {

    private static NumberFormat nf = NumberFormat.getInstance();

    private DoubleFormat() {}

    public static double parse(String s) throws ParseException {
        return (double)nf.parse(s);
    }
}
