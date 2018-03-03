package fields;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateField extends ColumnField {

    private String format;
    private String defaultDate;

    public DateField(boolean ... args) throws ParseException {

        super(args);
        this.format = "";
        this.defaultDate = "";

    }

    public DateField(String format, String defaultDate, boolean ... args) throws ParseException {

        super(args);
        this.format = format;
        Date date = new SimpleDateFormat(this.format).parse(defaultDate);
        this.defaultDate = defaultDate;
    }

    public String getDate() {

        return defaultDate;
    }

    public String getDefaultDate() {

        return defaultDate;
    }

    public String getFormat() {

        return this.format;
    }
}
