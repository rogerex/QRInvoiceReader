package bo.bumbay.qrinvoicereader.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

    public static String format(Date date) {
        return date == null? "" : formatter.format(date);
    }

    public static Date parse(String part) {
        Date date;
        try {
            date = formatter.parse(part);
        } catch (ParseException e) {
            date = null;
        }
        return date;
    }

    public static String format(String part) {
        if (part == null) {
            return "";
        }
        String format;
        try {
            Date date = new Date(Long.parseLong(part));
            format = formatter.format(date);
        } catch (Exception e) {
            format = "";
        }
        return format;
    }
}
