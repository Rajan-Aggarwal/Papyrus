package fields;

import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateField extends ColumnField {

    private String defaultDate;

    public DateField(boolean ... args) throws ParseException {

        super(args);
        this.defaultDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }

    public DateField(String defaultDate, boolean ... args) throws ParseException {

        super(args);

        //to check the validity of date string --> parse exception is thrown otherwise
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(defaultDate);
        this.defaultDate = defaultDate;

    }

    public String getDate() {

        return defaultDate;
    }

    public String getDefaultValue() {

        return defaultDate.toString();
    }
}
