package fields;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DateField extends ColumnField {

    private String defaultDate;

    public DateField(boolean ... args) throws ParseException {

        super(args);
        this.defaultDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public DateField(String defaultDate, boolean ... args) throws ParseException {

        super(args);
        this.defaultDate = new SimpleDateFormat("yyyy-MM-dd").format(defaultDate);
    }

    public String getDate() {

        return defaultDate;
    }

    public String getDefaultValue() {

        return defaultDate.toString();
    }
}
